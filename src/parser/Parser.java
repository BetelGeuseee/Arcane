package parser;

import model.Token;

import java.util.List;

/***
 * final grammar
 * -----------------------
 * expression → equality ;
 * equality→ comparison ( ( "!=" | "==" ) comparison )* ;
 * comparison→ term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
 * term→ factor ( ( "-" | "+" ) factor )* ;
 * factor→ unary ( ( "/" | "*" ) unary )* ;
 * unary→ ( "!" | "-" ) unary
 * primary→ NUMBER | STRING | "true" | "false" | "nil"| primary | "(" expression ")" ;
 */

public class Parser {
    private final List<Token> tokens;
    private int current = 0;
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }


}
