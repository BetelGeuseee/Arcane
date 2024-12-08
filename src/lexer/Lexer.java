package lexer;

import model.Token;
import model.enums.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String source;
    private List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Lexer(String source){
        this.source = source;
    }

    public List<Token> retrieveTokenFrom(String source){
          char c = consumeAndAdvance();
          switch(c){
              case '{' : addToken(TokenType.LEFT_BRACE); break;
              case '}' : addToken(TokenType.RIGHT_BRACE); break;
              case '(' : addToken(TokenType.LEFT_PAREN); break;
              case ')' : addToken(TokenType.RIGHT_PAREN); break;
              case '.' : addToken(TokenType.DOT); break;
              case '*' : addToken(TokenType.STAR); break;
              case '+' : addToken(TokenType.PLUS); break;
              case '-' : addToken(TokenType.MINUS); break;
              case '/' : addToken(TokenType.SLASH); break;
              case ';' : addToken(TokenType.SEMICOLON); break;
              case ',' : addToken(TokenType.COMMA); break;
              case '\n': line++; break;
              case ' ' :
              case '\r':
              case '\t': break;

          }
          return null;
    }

    private void addToken(TokenType tokenType){
        addToken(tokenType,null);
    }
    private void addToken(TokenType tokenType,Object literalValue) {
        String lexeme = source.substring(start,current);
        tokens.add(new Token(tokenType,literalValue,lexeme));
    }

    private char consumeAndAdvance() {
        return source.charAt(current++); //first get and move to next character
    }
}
