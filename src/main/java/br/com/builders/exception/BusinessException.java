package br.com.builders.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String erro) {
        super(erro);
    }
}
