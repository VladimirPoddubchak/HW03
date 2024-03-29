import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Mult implements Expr {

    private final @NotNull Expr left;
    private final @NotNull Expr right;

    public Mult(final @NotNull Expr left, final @NotNull Expr right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public int evaluate() {
        return left.evaluate() * right.evaluate();
    }
}
