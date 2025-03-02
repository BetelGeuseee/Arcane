package model.visitor.impl;

import model.*;
import model.enums.TokenType;
import model.visitor.Visitor;

public class PrettyPrinter implements Visitor<String> {

    @Override
    public String visitBinaryExpr(BinaryExpr binaryExpr) {
        return parenthesize(binaryExpr.operator.lexeme,binaryExpr.left,binaryExpr.right);
    }

    @Override
    public String visitUnaryExpr(UnaryExpr unaryExpr) {
        return parenthesize(unaryExpr.operator.lexeme,unaryExpr.right);
    }

    @Override
    public String visitGroupingExpr(GroupingExpr groupingExpr) {
        return parenthesize("group",groupingExpr.expr);
    }

    @Override
    public String visitLiteralExpr(LiteralExpr literal) {
        if(literal.value == null) return "nil";
        return literal.value.toString();
    }
    public String print(Expr expr){
        return expr.accept(this);
    }

    private String parenthesize(String name, Expr... exprs){
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for(Expr expr: exprs){
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    public static void main(String[] args) {
        Expr expression = new BinaryExpr(
                new UnaryExpr(
                        new Token(TokenType.MINUS, null, "-", 1),
                        new LiteralExpr(123)),
                new Token(TokenType.STAR, null, "*", 1),
                new GroupingExpr(
                        new LiteralExpr(45.67)));
        System.out.println(new PrettyPrinter().print(expression));
    }
}
