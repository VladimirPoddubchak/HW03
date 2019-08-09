import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Div implements Expr {

    private final @NotNull Expr left;
    private final @NotNull Expr right;

    public Div(final @NotNull Expr left, final @NotNull Expr right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public int evaluate() {

//        if (right.evaluate() == 0){
//            throw new ArithmeticException("Division by zero");
//         }else{
            return (left.evaluate() / right.evaluate());
//        }
    }
}
