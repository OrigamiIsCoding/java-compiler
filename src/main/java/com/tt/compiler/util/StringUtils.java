package com.tt.compiler.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Origami
 * @date 2/22/2023 1:41 PM
 */
public class StringUtils {
    public static List<String> splitToLines(String str) {
        return Arrays.stream(str.split("\n")).toList();
    }
}
