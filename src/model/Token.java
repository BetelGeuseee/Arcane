package model;

import model.enums.TokenType;

public class Token {
    private TokenType type;
    private Object literal;
    private String lexeme;
    private int line;  //used for giving meaningful error for user

    public Token(TokenType type,Object literal,String lexeme){
        this.type = type;
        this.literal = literal;
        this.lexeme = lexeme;
    }
}
