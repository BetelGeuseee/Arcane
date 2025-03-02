package model;

import model.enums.TokenType;

public class Token {
    public TokenType type;
    public Object literal;
    public String lexeme;
    public int line;  //used for giving meaningful error for user

    public Token(TokenType type,Object literal,String lexeme,int line){
        this.type = type;
        this.literal = literal;
        this.lexeme = lexeme;
        this.line = line;
    }
}
