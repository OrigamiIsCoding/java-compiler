package com.tt.compiler.exception;

/**
 * @author Origami
 * @date 3/3/2023 11:05 AM
 */
public class IllegalSymbolException extends RuntimeException {
    public IllegalSymbolException() {
        super("illegal symbol");
    }

    public IllegalSymbolException(String message) {
        super(message);
    }
}
