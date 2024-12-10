package model.enums;

public enum TokenType {
      //single character tokens
      LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, // (, ), {, }
      COMMA,DOT,MINUS,PLUS,SEMICOLON,SLASH,STAR,

       BANG,BANG_EQUAL,EQUAL,EQUAL_EQUAL,GREATER,GREATER_EQUAL,LESS,LESS_EQUAL,

      //literals
       IDENTIFIER,STRING,NUMBER,DEVANAGIRI,

       //keywords
       AND,ELSE,FALSE,FUN,FOR,IF,ETHER,OR,REVEAL,RETURN,TRUE,WHILE,SUMMON,

       EOF
}
