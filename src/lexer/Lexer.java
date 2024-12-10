package lexer;

import model.Token;
import model.enums.TokenType;
import runner.Arcane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lexer {
    private String source;
    private List<Token> tokens = new ArrayList<>();
    private static Map<String,TokenType> keywords;
    private int start = 0;
    private int current = 0; //represents character to be consumed next not already consumed.
    private int line = 1;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    TokenType.AND);
        keywords.put("else",   TokenType.ELSE);
        keywords.put("false",  TokenType.FALSE);
        keywords.put("for",    TokenType.FOR);
        keywords.put("if",     TokenType.IF);
        keywords.put("ether",  TokenType.ETHER);
        keywords.put("or",     TokenType.OR);
        keywords.put("reveal",  TokenType.REVEAL);
        keywords.put("true",   TokenType.TRUE);
        keywords.put("summon",    TokenType.SUMMON);
        keywords.put("while",  TokenType.WHILE);
    }
    public Lexer(String source){
        this.source = source;
    }
    public List<Token> getTokens(){
        while(!isAtEnd()){
            start = current;
            retrieveToken();
        }
        tokens.add(new Token(TokenType.EOF,"",null,line));
        return tokens;
    }
    private void retrieveToken(){
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
              case '!' : addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG); break;
              case '=' : addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL); break;
              case '>' : addToken(match('=')? TokenType.GREATER_EQUAL: TokenType.GREATER); break;
              case '<' : addToken(match('=')? TokenType.LESS_EQUAL : TokenType.LESS); break;
              case '"':  handleString(); break;
              case '\n': line++; break;
              case ' ' :
              case '\r':
              case '\t': break;
              default :
                  if(isDigit(c))
                      number();
                  else if(isAlpha(c))
                      identifier();
                  else{
                      Arcane.error(line,"Unexpected character.");
                  }
                  break;
          }
    }

    private void identifier() {
        while(isAlphaNumeric(peek())){
            consumeAndAdvance();
        }
       String text = source.substring(start,current);
        TokenType type = keywords.get(text);
        if(type != null)
            addToken(type);
        addToken(TokenType.IDENTIFIER);
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isAlpha(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';//identifier can only begin with alphabets or _.
    }

    private boolean isDigit(char c){
         return c>='0' && c<='9'; //here the conversion is handle by java compiler, lastly c,0 and 9 are represented as their corresponding unicode value.
    }
    private void number(){
        while(isDigit(peek())){
            consumeAndAdvance();
        }
        //this one is for fractional part
        if(peek() == '.' && isDigit(peekNext())){
            consumeAndAdvance();
            while(isDigit(peek()))
                consumeAndAdvance();
        }
        addToken(TokenType.NUMBER,Double.parseDouble(source.substring(start,current)));
    }

    private char peekNext(){
        if(current+1 >= source.length())
            return '\0';
        return source.charAt(current+1);
    }
    private void handleString() {
       while(peek()!= '"' && !isAtEnd()){
           if(peek() == '\n'){
               line++;
               consumeAndAdvance();
           }
       }
       if(isAtEnd()){
           Arcane.error(line,"Unterminated string.");
           return;
       }
       String value = source.substring(start+1,current-1); //removing "" double quotes.
       addToken(TokenType.STRING,value);
    }

    private char peek(){
        if(isAtEnd())
            return '\0'; // this is null character that represents end of the string.
        return source.charAt(current);
    }

    private boolean match(char expected){
        if(isAtEnd())
            return false;
        if(source.charAt(current) == expected) {
            current++;
            return true;
        }
        current++;
        return false;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void addToken(TokenType tokenType){
        addToken(tokenType,null);
    }
    private void addToken(TokenType tokenType,Object literalValue) {
        String lexeme = source.substring(start,current);
        tokens.add(new Token(tokenType,literalValue,lexeme,line));
    }

    private char consumeAndAdvance() {
        return source.charAt(current++); //first get and move to next character
    }
}
