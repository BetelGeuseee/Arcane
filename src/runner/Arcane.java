package runner;

import lexer.Lexer;
import model.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Arcane {

    public void runPrompt() throws IOException {
       InputStreamReader inputStreamReader = new InputStreamReader(System.in);
       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
       for(;;){ //infinite loop
           System.out.println("-> ");
           String line = bufferedReader.readLine();
           if(line == null) break;
           run(line);
       }
    }
    public void runScript(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //raw bytes
        run(new String(bytes, Charset.defaultCharset())); //decode into human-readable characters, uses default charset of your system to decode.
    }
    public void run(String source){
       Lexer lexer =new Lexer(source);
       List<Token> tokens = lexer.getTokens();

    }
    public static void main(String[] args) throws IOException {
        Arcane arcane = new Arcane();
         if(args.length > 1){
             System.out.println("Usage: arcane [script]");
             System.exit(1);
         }else if( args.length == 1){
             arcane.runScript(args[0]); //run from script
         }else{
             arcane.runPrompt();
         }
    }
    public static void error(int line,String message){
        report(line,message);
    }
    private static void report(int line,String message){
        System.err.println("[In line "+line+"]"+"Error: "+message);
    }
}
