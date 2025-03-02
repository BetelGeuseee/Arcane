package model;

import model.visitor.Visitor;

public abstract class Expr {
    public abstract <R> R accept(Visitor<R> visitor);
}
