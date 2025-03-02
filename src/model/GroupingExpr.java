package model;

import model.visitor.Visitor;

public class GroupingExpr extends Expr{
    public final Expr expr;
    public GroupingExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitGroupingExpr(this);
    }
}
