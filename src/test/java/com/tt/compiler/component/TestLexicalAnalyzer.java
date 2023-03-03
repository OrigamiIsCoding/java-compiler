package com.tt.compiler.component;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tt.compiler.util.StringUtils.splitToLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

/**
 * @author Origami
 * @date 2/22/2023 9:38 AM
 */
public class TestLexicalAnalyzer {
    LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

    private static void assertEqualsIgnoringWhiteSpace(String expected, List<String> actual) {
        assertThat(expected, equalToIgnoringWhiteSpace(String.join("\n", actual)));
    }

    @Test
    void testSimplifySource1() {
        var input = """
                int a = 1; int b = 2; // this is one line comment
                a += 1;
                """;
        var expected = """
                int a = 1
                int b = 2
                // this is one line comment
                a += 1
                """;
        var actual = lexicalAnalyzer.simplifySource(input);
        assertEqualsIgnoringWhiteSpace(expected, actual);
    }

    @Test
    void testSimplifySource2() {
        var input = """
                int a = 1; // this is one line comment int b = 2;
                a += 1;
                """;
        var excepted = """
                int a = 1
                // this is one line comment int b = 2;
                a += 1
                """;

        var actual = lexicalAnalyzer.simplifySource(input);
        assertEqualsIgnoringWhiteSpace(excepted, actual);
    }

    @Test
    void testSimplifySource3() {
        var input = """
                String a = "Hello // this is not comment"; // this is comment
                """;
        var excepted = """
                String a = "Hello // this is not comment"
                // this is comment
                """;
        var actual = lexicalAnalyzer.simplifySource(input);
        assertEqualsIgnoringWhiteSpace(excepted, actual);
    }

    @Test
    void testProcessOneLineComment() {
        var input = """
                int a = 1;
                // this is one line comment
                a += 1;
                """;
        var expected = """
                int a = 1;
                a += 1;
                """;

        var actual = lexicalAnalyzer.filterComments(splitToLines(input));

        assertEqualsIgnoringWhiteSpace(expected, actual);
    }

    @Test
    void testProcessMultiLineComment() {
        var input = """
                int a = 1;
                /**
                    this is multi
                    line comment
                */
                a += 1;
                """;
        var expected = """
                int a = 1;
                a += 1;
                """;
        var actual = lexicalAnalyzer.filterComments(splitToLines(input));

        assertEqualsIgnoringWhiteSpace(expected, actual);
    }

    @Test
    void testParseOneExpression() {
        var input = """
                int a = 1;
                """;
        var actual = lexicalAnalyzer.parseOneExpression(input);

        actual.forEach(System.out::println);
    }

    @Test
    void testParseCode() {
        var input = """
                import com.tt.Test;
                
                public Test {
                    public static void main(String[] args) {
                        System.out.println("Hello World");
                    }
                }
                """;

        var tokens = lexicalAnalyzer.parse(input);

        tokens.forEach(System.out::println);
    }
}
