package model.visitor;

import model.BinaryExpr;
import model.GroupingExpr;
import model.LiteralExpr;
import model.UnaryExpr;

public interface Visitor<R> {
    R visitBinaryExpr(BinaryExpr binaryExpr);
    R visitUnaryExpr(UnaryExpr unaryExpr);
    R visitGroupingExpr(GroupingExpr groupingExpr);
    R visitLiteralExpr(LiteralExpr literal);
}
