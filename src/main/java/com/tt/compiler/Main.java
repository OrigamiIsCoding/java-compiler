package com.tt.compiler;

import com.google.common.io.Resources;
import com.tt.compiler.component.LexicalAnalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Origami
 * @date 2/22/2023 8:46 AM
 */
public class Main {
    public static void main(String[] args) throws IOException {
        URL resource = Resources.getResource("examples/Main.java");
        new LexicalAnalyzer().parse(new File(resource.getFile())).forEach(System.out::println);
    }
}
