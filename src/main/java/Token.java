
public class Token {
    TokenType type;
    String value;
    int priority;

    public Token(TokenType type, String value, int priority) {
        this.type = type;
        this.value = value;
        this.priority = priority;
    }


}
