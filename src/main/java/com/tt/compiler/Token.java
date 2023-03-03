package com.tt.compiler;

import com.tt.compiler.constants.SpecifyCode;
import com.tt.compiler.exception.IllegalSignException;

import java.util.Map;
import java.util.Optional;

/**
 * @author Origami
 * @date 2/22/2023 8:59 AM
 */
public record Token(
        SpecifyCode specifyCode,
        String value
) {
    // 单个字符的符号
    public static final Token LeftBracket = new Token(SpecifyCode.Sign, "(");
    public static final Token RightBracket = new Token(SpecifyCode.Sign, ")");
    public static final Token MediumLeftBracket = new Token(SpecifyCode.Sign, "[");
    public static final Token MediumRightBracket = new Token(SpecifyCode.Sign, "]");
    public static final Token BigLeftBracket = new Token(SpecifyCode.Sign, "{");
    public static final Token BigRightBracket = new Token(SpecifyCode.Sign, "}");
    public static final Token Semicolon = new Token(SpecifyCode.Sign, ";");
    public static final Token Comma = new Token(SpecifyCode.Sign, ",");
    public static final Token Dot = new Token(SpecifyCode.Sign, ".");

    // 可能包含多个字符的符号
    public static final Token Plus = new Token(SpecifyCode.Sign, "+");
    public static final Token PlusAndEqual = new Token(SpecifyCode.Sign, "+=");
    public static final Token PlusAndPlus = new Token(SpecifyCode.Sign, "++");
    public static final Token Minus = new Token(SpecifyCode.Sign, "-");
    public static final Token MinusAndEqual = new Token(SpecifyCode.Sign, "-=");
    public static final Token MinusAndMinus = new Token(SpecifyCode.Sign, "--");
    public static final Token Multiply = new Token(SpecifyCode.Sign, "*");
    public static final Token MultiplyAndEqual = new Token(SpecifyCode.Sign, "*=");
    public static final Token Divide = new Token(SpecifyCode.Sign, "/");
    public static final Token DivideAndEqual = new Token(SpecifyCode.Sign, "/=");
    public static final Token Mod = new Token(SpecifyCode.Sign, "%");
    public static final Token ModAndEqual = new Token(SpecifyCode.Sign, "%=");
    public static final Token Equal = new Token(SpecifyCode.Sign, "=");
    public static final Token EqualAndEqual = new Token(SpecifyCode.Sign, "==");
    public static final Token Less = new Token(SpecifyCode.Sign, "<");
    public static final Token LessAndEqual = new Token(SpecifyCode.Sign, "<=");
    public static final Token Greater = new Token(SpecifyCode.Sign, ">");
    public static final Token GreaterAndEqual = new Token(SpecifyCode.Sign, ">=");

    // 关键字
    public static final Token If = new Token(SpecifyCode.If, "if");
    public static final Token Else = new Token(SpecifyCode.Else, "else");
    public static final Token While = new Token(SpecifyCode.While, "while");
    public static final Token For = new Token(SpecifyCode.For, "for");
    public static final Token Do = new Token(SpecifyCode.Do, "do");
    public static final Token Break = new Token(SpecifyCode.Break, "break");
    public static final Token Continue = new Token(SpecifyCode.Continue, "continue");
    public static final Token Return = new Token(SpecifyCode.Return, "return");
    public static final Token Switch = new Token(SpecifyCode.Switch, "switch");
    public static final Token Case = new Token(SpecifyCode.Case, "case");
    public static final Token Default = new Token(SpecifyCode.Default, "default");
    public static final Token Int = new Token(SpecifyCode.Int, "int");
    public static final Token Long = new Token(SpecifyCode.Long, "long");
    public static final Token Float = new Token(SpecifyCode.Float, "float");
    public static final Token Double = new Token(SpecifyCode.Double, "double");
    public static final Token Char = new Token(SpecifyCode.Char, "char");
    public static final Token Boolean = new Token(SpecifyCode.Boolean, "boolean");
    public static final Token Void = new Token(SpecifyCode.Void, "void");
    public static final Token True = new Token(SpecifyCode.True, "true");
    public static final Token False = new Token(SpecifyCode.False, "false");
    public static final Token Null = new Token(SpecifyCode.Null, "null");
    public static final Token New = new Token(SpecifyCode.New, "new");
    public static final Token Class = new Token(SpecifyCode.Class, "class");
    public static final Token Public = new Token(SpecifyCode.Public, "public");
    public static final Token Private = new Token(SpecifyCode.Private, "private");
    public static final Token Protected = new Token(SpecifyCode.Protected, "protected");
    public static final Token Static = new Token(SpecifyCode.Static, "static");
    public static final Token Final = new Token(SpecifyCode.Final, "final");
    public static final Token Abstract = new Token(SpecifyCode.Abstract, "abstract");
    public static final Token This = new Token(SpecifyCode.This, "this");
    public static final Token Super = new Token(SpecifyCode.Super, "super");
    public static final Token Import = new Token(SpecifyCode.Import, "import");
    public static final Token Package = new Token(SpecifyCode.Package, "package");
    public static final Token Try = new Token(SpecifyCode.Try, "try");
    public static final Token Catch = new Token(SpecifyCode.Catch, "catch");
    public static final Token Finally = new Token(SpecifyCode.Finally, "finally");
    public static final Token Throw = new Token(SpecifyCode.Throw, "throw");
    public static final Token Throws = new Token(SpecifyCode.Throws, "throws");
    public static final Token Enum = new Token(SpecifyCode.Enum, "enum");
    public static final Token Interface = new Token(SpecifyCode.Interface, "interface");
    public static final Token Extends = new Token(SpecifyCode.Extends, "extends");
    public static final Token Implements = new Token(SpecifyCode.Implements, "implements");
    public static final Token InstanceOf = new Token(SpecifyCode.InstanceOf, "instanceof");
    public static final Token Native = new Token(SpecifyCode.Native, "native");
    public static final Token Synchronized = new Token(SpecifyCode.Synchronized, "synchronized");


    private static final Map<String, Token> SingleTerminatedSign = Map.ofEntries(
            entry(LeftBracket),
            entry(RightBracket),
            entry(MediumLeftBracket),
            entry(MediumRightBracket),
            entry(BigLeftBracket),
            entry(BigRightBracket),
            entry(Semicolon),
            entry(Comma),
            entry(Dot)
    );

    private static final Map<String, Token> MultiTerminatedSign = Map.ofEntries(
            entry(Plus),
            entry(PlusAndEqual),
            entry(PlusAndPlus),
            entry(Minus),
            entry(MinusAndEqual),
            entry(MinusAndMinus),
            entry(Multiply),
            entry(MultiplyAndEqual),
            entry(Divide),
            entry(DivideAndEqual),
            entry(Mod),
            entry(ModAndEqual),
            entry(Equal),
            entry(EqualAndEqual),
            entry(Less),
            entry(LessAndEqual),
            entry(Greater),
            entry(GreaterAndEqual)
    );

    private static final Map<String, Token> Keyword = Map.ofEntries(
            entry(If),
            entry(Else),
            entry(While),
            entry(For),
            entry(Do),
            entry(Break),
            entry(Continue),
            entry(Return),
            entry(Switch),
            entry(Case),
            entry(Default),
            entry(Int),
            entry(Long),
            entry(Float),
            entry(Double),
            entry(Char),
            entry(Boolean),
            entry(Void),
            entry(True),
            entry(False),
            entry(Null),
            entry(New),
            entry(Class),
            entry(Public),
            entry(Private),
            entry(Protected),
            entry(Static),
            entry(Final),
            entry(Abstract),
            entry(This),
            entry(Super),
            entry(Import),
            entry(Package),
            entry(Try),
            entry(Catch),
            entry(Finally),
            entry(Throw),
            entry(Throws),
            entry(Enum),
            entry(Interface),
            entry(Extends),
            entry(Implements),
            entry(InstanceOf),
            entry(Native),
            entry(Synchronized)
    );

    public static Optional<Token> getSingleTerminatedSign(char ch) {
        return Optional.ofNullable(SingleTerminatedSign.get(String.valueOf(ch)));
    }

    public static Optional<Token> getMultiTerminatedSignFrom(char[] chars, int index) {
        String sign = "";
        for (int i = index; i < chars.length; i++) {
            if ("+-*/%=<>".contains(String.valueOf(chars[i]))) {
                sign += chars[i];
            } else {
                break;
            }
        }
        if (sign.isEmpty()) {
            return Optional.empty();
        }
        Token token = MultiTerminatedSign.get(sign);
        if (token == null) {
            throw new IllegalSignException(sign);
        }
        return Optional.of(token);
    }

    public static Optional<Token> getDigit(char[] chars, int index) {
        if (!Character.isDigit(chars[index])) {
            return Optional.empty();
        }
        String literal = "";
        for (int i = index; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                literal += chars[i];
            } else {
                break;
            }
        }
        return Optional.of(new Token(SpecifyCode.Literal, literal));
    }

    public static Optional<Token> getString(char[] chars, int index) {
        if (chars[index] != '"') {
            return Optional.empty();
        }
        StringBuilder literal = new StringBuilder();
        literal.append('"');
        for (int i = index + 1; i < chars.length; i++) {
            if (chars[i] == '"') {
                literal.append('"');
                return Optional.of(new Token(SpecifyCode.Literal, literal.toString()));
            } else {
                literal.append(chars[i]);
            }
        }
        throw new IllegalSignException("can't find closed \" for : " + literal);
    }

    public static Optional<Token> getIdentifierOrKey(char[] chars, int index) {
        // 标识符不能以数字开头
        if (Character.isDigit(chars[index])) {
            return Optional.empty();
        }
        String literal = "";
        for (int i = index; i < chars.length; i++) {
            if (Character.isLetterOrDigit(chars[i]) || chars[i] == '_') {
                literal += chars[i];
            } else {
                break;
            }
        }
        if (literal.isBlank()) {
            return Optional.empty();
        }

        // 判断是标识符还是关键字
        return Optional.of(Keyword.getOrDefault(literal, new Token(SpecifyCode.Identifier, literal)));
    }

    private static Map.Entry<String, Token> entry(Token token) {
        return Map.entry(token.value, token);
    }

    @Override
    public String toString() {
        return String.format("<%2d, '%s'>", specifyCode.ordinal(), value);
    }
}
