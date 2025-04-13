package parser;

import model.*;
import model.enums.TokenType;
import runner.Arcane;

import java.util.List;

import static model.enums.TokenType.*;

/***
 * final grammar
 * -----------------------
 * expression → equality ;
 * equality→ comparison ( ( "!=" | "==" ) comparison )* ;
 * comparison→ term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
 * term→ factor ( ( "-" | "+" ) factor )* ;
 * factor→ unary ( ( "/" | "*" ) unary )* ;
 * unary→ ( "!" | "-" ) unary
 * primary→ NUMBER | STRING | "true" | "false" | "ether"| primary | "(" expression ")" ;
 */

public class Parser {
    private final List<Token> tokens;
    private int current = 0;
    private static class ParseError extends RuntimeException {}
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public Expr parse(){
        try{
            return expression();
        }catch(ParseError e){
            return null;
        }
    }

    private Expr expression(){
        return equality();
    }

    private Expr equality(){
        Expr expr = comparison();
        while(match(BANG_EQUAL,EQUAL_EQUAL)){
            Token operator = previous();
            Expr right = comparison();
            expr = new BinaryExpr(expr,operator,right);
        }
        return expr;
    }

    private Expr comparison(){
        Expr expr = term();
        while(match(GREATER,GREATER_EQUAL,LESS,LESS_EQUAL)){
            Token operator = previous();
            Expr right = term();
            expr = new BinaryExpr(expr,operator,right);
        }
        return expr;
    }
    private Expr term() {
        Expr expr = factor();
        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new BinaryExpr(expr, operator, right);
        }
        return expr;
    }
    private Expr factor() {
        Expr expr = unary();
        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new BinaryExpr(expr, operator, right);
        }
        return expr;
    }
    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new UnaryExpr(operator, right);
        }
        return primary();
    }
    private Expr primary() {
        if (match(FALSE)) return new LiteralExpr(false);
        if (match(TRUE)) return new LiteralExpr(true);
        if (match(ETHER)) return new LiteralExpr(null);
        if (match(NUMBER, STRING)) {
            return new LiteralExpr(previous().literal);
        }
        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new GroupingExpr(expr);
        }
        throw error(peek(), "Expect expression.");
    }
    private Token consume(TokenType type, String message){
        if(check(type))
            return advance();
        throw error(peek(),message);
    }

    private ParseError error(Token token, String message){
        Arcane.error(token,message);
        return new ParseError();
    }

    private boolean match(TokenType... types){
        for(TokenType type : types){
            if(check(type)){
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type){
        if(isAtEnd())
            return false;
        return peek().type == type;
    }
    private Token advance(){
        if(!isAtEnd())
            current++;
        return previous();
    }
    private boolean isAtEnd() {
        return peek().type == EOF;
    }
    private Token peek() {
        return tokens.get(current);
    }
    private Token previous() {
        return tokens.get(current - 1);
    }

    private void synchronize() {
        advance();
        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;
            switch (peek().type) {
                case FUN:
                case SUMMON:
                case FOR:
                case IF:
                case WHILE:
                case REVEAL:
                case RETURN:
                    return;
            }
            advance();
        }
    }

}
