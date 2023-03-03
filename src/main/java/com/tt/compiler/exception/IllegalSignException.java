package com.tt.compiler.exception;

/**
 * @author Origami
 * @date 2/23/2023 11:15 AM
 */
public class IllegalSignException extends RuntimeException {
    public IllegalSignException(String sign) {
        super("illegal sign : " + sign);
    }
}
