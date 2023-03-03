package com.tt.compiler.component;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.tt.compiler.Token;
import com.tt.compiler.exception.IllegalSymbolException;
import com.tt.compiler.exception.UnclosedCommentException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 词法分析器
 *
 * @author Origami
 * @date 2/22/2023 9:05 AM
 */
public class LexicalAnalyzer {
    public static final String OneLineCommentSign = "//";
    public static final String MultiLineCommentRight = "*/";
    public static final String MultiLineCommentLeft = "/*";
    // 分号
    public static final String Semicolon = ";";

    /**
     * 词法分析
     *
     * @param sourceFile 源代码文件
     * @return Token 列表
     * @throws IOException IOException
     * @author Origami
     * @date 2/22/2023
     */
    public List<Token> parse(File sourceFile) throws IOException {
        return parse(Files.asCharSource(sourceFile, Charsets.UTF_8).read());
    }

    /**
     * 词法分析
     *
     * @param source 源代码字符串
     * @return Token 列表
     * @author Origami
     * @date 2/22/2023
     */
    public List<Token> parse(String source) {
        var lines = simplifySource(source);
        lines = filterComments(lines);
        return lines.stream().flatMap(this::parseOneExpression).collect(Collectors.toList());
    }

    public Stream<Token> parseOneExpression(String expression) {
        var chars = expression.toCharArray();
        var out = Stream.<Token>builder();
        for (int i = 0; i < chars.length; i++) {
            var first = chars[i];
            if (first == ' ' || first == '\t' || first == '\n') {
                continue;
            }

            int j = i;
            Token token = Optional.<Token>empty()
                    // 先匹配单个符号
                    .or(() -> Token.getSingleTerminatedSign(first))
                    // 再匹配多个符号
                    .or(() -> Token.getMultiTerminatedSignFrom(chars, j))
                    // 处理数字
                    .or(() -> Token.getDigit(chars, j))
                    // 处理字面量字符串
                    .or(() -> Token.getString(chars, j))
                    // 再匹配标识符或者关键字
                    .or(() -> Token.getIdentifierOrKey(chars, j))
                    // 如果都不匹配，就抛出异常
                    .orElseThrow(() -> new IllegalSymbolException(String.format(
                            "illegal symbol: %s",
                            new String(chars, j, Math.min(chars.length - j, 10))
                    )));

            i += token.value().length() - 1;
            out.add(token);
        }
        return out.build();
    }

    public List<String> simplifySource(String source) {
        return Arrays.stream(source.split("\n"))
                .flatMap(this::flatMapNewLine)
                .map(String::strip)
                .filter(s -> !Strings.isNullOrEmpty(s))
                .collect(Collectors.toList());
    }

    private Stream<String> flatMapNewLine(String line) {
        var out = Stream.<String>builder();
        var index = -1;
        var minIndex = Integer.MAX_VALUE;
        String expression = null;

        // 过滤单行注释
        while ((index = line.indexOf(OneLineCommentSign, index + 1)) != -1) {
            // 如果不在字面量中，就直接从当前位置截断，然后退出
            if (checkNotInLiteral(line, index)) {
                expression = line.substring(0, index);
                minIndex = index;
                break;
            }
        }

        // 过滤多行注释
        while ((index = line.lastIndexOf(MultiLineCommentLeft, index - 1)) != -1) {
            if (checkNotInLiteral(line, index)) {
                expression = line.substring(0, index);
                minIndex = Math.min(index, minIndex);
                break;
            }
        }

        // 过滤多行注释的右边
        if (expression == null) {
            expression = line;
        }

        boolean hasSemicolon = expression.contains(Semicolon);
        // 将有分号的进行换行
        // TODO 对于字符串中的分号需要左判断
        for (String item : expression.split(Semicolon)) {
            if (item.isBlank()) {
                continue;
            }
            out.accept(item);
            if (hasSemicolon) {
                out.accept(Semicolon);
            }
        }

        if (minIndex != Integer.MAX_VALUE) {
            out.accept(line.substring(minIndex));
        }

        return out.build();
    }

    /**
     * 过滤注释
     *
     * @param lines 一行行代码
     * @return 过滤后的源码
     * @author Origami
     * @date 2/22/2023
     */
    public List<String> filterComments(List<String> lines) {
        var stack = new Stack<String>();

        for (var line : lines) {
            // 单行注释
            if (line.startsWith(OneLineCommentSign)) {
                // 以 // 开头，直接过滤
                continue;
            }

            // 当前是多行注释的结尾
            if (line.endsWith(MultiLineCommentRight)) {
                // 当栈不为空，并且栈顶元素不以 /** 开头，那么就弹出栈顶元素
                while (!stack.isEmpty() && !stack.peek().startsWith(MultiLineCommentLeft)) {
                    stack.pop();
                }
                if (stack.isEmpty()) {
                    // 找不到匹配的 /**，抛出异常
                    throw new UnclosedCommentException();
                }
                // 否则弹出这个匹配的注释
                stack.pop();

                continue;
            }
            // 否则加入栈中
            stack.push(line);
        }

        return stack.stream().toList();
    }

    /**
     * 判断 target 字符串是否在 source 代码的字符串字面量中
     *
     * @return boolean
     * @author Origami
     * @date 2/22/2023
     */
    private boolean checkNotInLiteral(String source, int index) {
        int leftIndex = source.lastIndexOf('"', index);
        int rightIndex = source.indexOf('"', index);

        return leftIndex == -1 || // 存在左引号
                rightIndex == -1 || // 存在右引号
                leftIndex >= index || // 在左边
                rightIndex <= index; // 在右边
    }
}
