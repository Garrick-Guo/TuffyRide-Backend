package com.cs362.tuffyride.model;
/*
we don't need to mark the class as @Entity since we don't store token information in database
 */
public class Token {
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}