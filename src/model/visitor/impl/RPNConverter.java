package model.visitor.impl;

import model.*;
import model.enums.TokenType;
import model.visitor.Visitor;

public class RPNConverter implements Visitor<String> {

    public static void main(String[] args) {
        RPNConverter converter = new RPNConverter();
        Expr expr = new BinaryExpr(
                new BinaryExpr(new LiteralExpr(1),new Token(TokenType.PLUS,null,"+",1),new LiteralExpr(2)),
                new Token(TokenType.STAR,null,"*",1),
                new BinaryExpr(new LiteralExpr(4),new Token(TokenType.MINUS,null,"-",1),new LiteralExpr(3))
        );
        System.out.println(converter.print(expr));
    }
    public String print(Expr expr){
        return expr.accept(this);
    }
    @Override
    public String visitBinaryExpr(BinaryExpr binaryExpr) {
         String leftExpr = binaryExpr.left.accept(this);
         String rightExpr = binaryExpr.right.accept(this);

         return leftExpr+" "+rightExpr+" "+binaryExpr.operator.lexeme;
    }

    @Override
    public String visitUnaryExpr(UnaryExpr unaryExpr) {
        String rightExpr = unaryExpr.right.accept(this);
        return rightExpr+ " "+ unaryExpr.operator.lexeme;
    }

    @Override
    public String visitGroupingExpr(GroupingExpr groupingExpr) {
        return groupingExpr.expr.accept(this);
    }

    @Override
    public String visitLiteralExpr(LiteralExpr literal) {
        if(literal.value == null)
            return "nil";
        return literal.value.toString();
    }
}
