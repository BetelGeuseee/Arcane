package runner;

import lexer.Lexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Arcane {
    public void runPrompt(){

    }
    public void runScript(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //raw bytes
        run(new String(bytes, Charset.defaultCharset())); //decode into human-readable characters, uses default charset of your system to decode.
    }
    public void run(String source){
       Lexer lexer =new Lexer(source);

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
