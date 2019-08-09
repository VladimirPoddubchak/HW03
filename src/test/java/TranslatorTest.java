import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {
    Translator translator = new Translator();
    @Test
    void checkSymbols() {



//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("11111r             +        1111");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("e123+-56");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput(")123t+-56;e");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("-123+456");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("123()+456");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("123+)456");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("()123+456");});
//        assertThrows(IllegalArgumentException.class,()->{translator.checkInput("(123+456");});
//        assertEquals(true,translator.checkInput("123+56"));
//        assertEquals(true,translator.checkInput("123+(56*2)"));
//        assertEquals(true,translator.checkInput("(123-1)+56"));
//        assertEquals(true,translator.checkInput("(123+56)"));
//        assertEquals(false,translator.checkInput("e123+-56"));
//
//        assertEquals(false,translator.checkInput(")123t+-56;e"));
//        assertEquals(false,translator.checkInput("  ( )4"));
//        assertEquals(false,translator.checkInput("*123+-56"));
//        assertEquals(false,translator.checkInput("12(3t)+-56;e"));
//        assertEquals(true,translator.checkInput(""));
//
//        assertEquals(false,translator.checkInput("123t+-56;e"));
//        assertEquals(true,translator.checkInput(""));
//        assertThrows(IllegalArgumentException.class,()->translator.checkInput(null));
    }

    @Test
    void build() {
        String input = "78  +-*   21";
        assertEquals(99,translator.build(input).evaluate());
    }
    @Test
    void test1(){
        Expr expr = new Subt(
                new Mult(
                        new Const(3),
                        new Const(5)),
                new Div(
                        new Const(16),
                        new Const(4)
                )
        );
        System.out.println("!!!!!!! Expr = "+ expr.evaluate());


    }

//    @Test
//    void stringToToken() {
//        List<Token> tokens = new ArrayList<>();
//        String input ="(345rrr + 667*)";
//        tokens = translator.stringToToken(input);
//
//        assertEquals(TokenType.OPEN,tokens.get(0).type);
//        assertEquals("(", tokens.get(0).value);
//        assertEquals(-1,tokens.get(0).priority);
//
//        assertEquals(TokenType.CONST,tokens.get(1).type);
//        assertEquals("345", tokens.get(1).value);
//        assertEquals(-1,tokens.get(1).priority);
//
//        assertEquals(TokenType.OPERATOR,tokens.get(2).type);
//        assertEquals("+", tokens.get(2).value);
//        assertEquals(1,tokens.get(2).priority);
//
//        assertEquals(TokenType.CONST,tokens.get(3).type);
//        assertEquals("667", tokens.get(3).value);
//        assertEquals(-1,tokens.get(3).priority);
//
//        assertEquals(TokenType.OPERATOR,tokens.get(4).type);
//        assertEquals("*", tokens.get(4).value);
//        assertEquals(2,tokens.get(4).priority);
//
//        assertEquals(TokenType.CLOSE,tokens.get(5).type);
//        assertEquals(")", tokens.get(5).value);
//        assertEquals(-1,tokens.get(5).priority);
//
//
//        input = "(3+2)*  6-(5^456+9   )";
//        System.out.println(input);
//        tokens = translator.stringToToken(input);
//    }

    @Test
    void buildFromTokens() {
        System.out.println("================================================");
        List<Token> tokens = new ArrayList<>();
        String input =" —        ((—(3+8*2))-(8-2^0))";
        tokens = translator.stringToToken(input);
        Expr resultExpr = translator.buildFromTokens(tokens);
        int result = resultExpr.evaluate();
        System.out.println("Result: "+ input+" = "+result);
    }
}