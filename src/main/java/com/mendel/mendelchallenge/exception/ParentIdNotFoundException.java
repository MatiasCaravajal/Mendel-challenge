package com.mendel.mendelchallenge.exception;

/** Exception to be thrown when an parent id into a transaction not found in database. */
public class ParentIdNotFoundException extends RuntimeException{
    public ParentIdNotFoundException(String message) {
        super(message);
    }
}
