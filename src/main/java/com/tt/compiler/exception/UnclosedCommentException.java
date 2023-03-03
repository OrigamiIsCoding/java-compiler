package com.tt.compiler.exception;

/**
 * @author Origami
 * @date 2/22/2023 9:32 AM
 */
public class UnclosedCommentException extends RuntimeException {
    public UnclosedCommentException() {
        super("unclosed comment");
    }

    public UnclosedCommentException(String message) {
        super(message);
    }
}
