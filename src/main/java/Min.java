import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Min implements Expr {

    private final @NotNull Const left;
    private final @NotNull Expr right;

    public Min(final @NotNull Const left, final @NotNull Expr right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public int evaluate() {
        return left.evaluate() - right.evaluate();
    }
}
