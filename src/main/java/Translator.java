import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Translator implements ExprBuilder {

    private static final Set<Character> ALPHABET = Set.of(
            '0','1','2','3','4','5','6','7','8','9',
            '^','*','/','—','-','+','(',')',' '
        );
    private static final Set<Character> NUMBERS = Set.of(
            '0','1','2','3','4','5','6','7','8','9'
    );

//    private static final Set<Character> OPERATORS = Set.of(
//            '^','*','/','—','-','+'
//    );

//    private static final Character DELIMETR = ' ';
//    private static final Character OPEN = '(';
//    private static final Character CLOSE = ')';

    private static final Map<Character,Integer> operatorPriority = Map.of(

            '^',3,
            '*',2,
            '/',2,
            '—',2,
            '+',1,
            '-',1
    );

//    private static int recurseCount = 0;

//    public boolean checkInput(@NotNull String input){
//        Deque<Character> pair = new ArrayDeque<>(2);
//        Deque<Character> brackets = new ArrayDeque<>();
//
//        // Проверка на неверные символы
//        if (!input.chars().mapToObj(c->(char)c).allMatch(c->ALPHABET.contains(c))){
//            System.out.println(input+"Wrong symbol");
//            throw new IllegalArgumentException("Wrong Symbol");
//        }
//        // Удаление разделителей @DELIMETR на неверные символы
//        input = input.chars().mapToObj(c->(char)c).filter(c->c!=DELIMETR).map(c-> c.toString()).collect(Collectors.joining());
//        System.out.println("InputStream = " + input);
//
//        // Проверка начальных символов
//        if (!NUMBERS.contains(input.charAt(0))&input.charAt(0)!=OPEN&input.charAt(0)!='—'){
//            System.out.println("Wrong first symbol = " + input.charAt(0) );
//            throw new IllegalArgumentException("Wrong first symbol");
//        }
//
//        // Проверка правильной последовательности скобок
//        input.chars().mapToObj(c->(char)c).forEach(c ->  {
//            if (c==OPEN){brackets.push(c);}
//            if (c==CLOSE&brackets.size()==0){
//                System.out.println("Wrong brackets");
//                throw new IllegalArgumentException("Wrong brackets");
//            }
//            if (c==CLOSE&brackets.size()!=0){brackets.pollLast();}
//
//
//            pair.push(c);
//            if (pair.size()>1){
//
//                if(NUMBERS.contains(pair.peekLast())&(pair.peekFirst()==OPEN|pair.peekFirst()=='—')){
//                    System.out.println("["+pair.peekLast()+"],["+pair.peekFirst()+"]");
//                    throw new IllegalArgumentException("Wrong Symbol");
//                }
//                if(OPERATORS.contains(pair.peekLast())&!(NUMBERS.contains(pair.peekFirst())|pair.peekFirst()==OPEN)){
//                    System.out.println("["+pair.peekLast()+"],["+pair.peekFirst()+"]");
//                    throw new IllegalArgumentException("Wrong Symbol");
//                }
//                if(pair.peekLast()==OPEN&!(NUMBERS.contains(pair.peekFirst())|pair.peekFirst()==OPEN|pair.peekFirst()=='—')){
//                    System.out.println("["+pair.peekLast()+"],["+pair.peekFirst()+"]");
//                    throw new IllegalArgumentException("Wrong Symbol");
//                }
//                if(pair.peekLast()==CLOSE&(NUMBERS.contains(pair.peekFirst())|pair.peekFirst()==OPEN|pair.peekFirst()=='—')){
//                    System.out.println("["+pair.peekLast()+"],["+pair.peekFirst()+"]");
//                    throw new IllegalArgumentException("Wrong Symbol");
//                }
//                System.out.println("["+pair.peekLast()+"],["+pair.peekFirst()+"]");
//
//                pair.pollLast();
//            }
//        });
//        if (brackets.size()!=0){
//            System.out.println("Wrong brackets");
//            throw new IllegalArgumentException("Wrong brackets");
//        }
//        return true;
//    }


    public List<Token> stringToToken(String input){
        List<Token> tokens = new ArrayList<Token>();
        int index = 0;
        StringBuilder tempNumber = new StringBuilder();

        for (int i = 0; i<input.length(); i++) {
//          Прверка на неверные символы. Если её убрать, они будут игнопироваться.
            if (!ALPHABET.contains(input.charAt(i))){
                throw new IllegalArgumentException("Wrong symbol. Position = "+i+"; Value = "+input.charAt(i));
            }
            if (input.charAt(i)== '(') {
                tokens.add(index, new Token(TokenType.OPEN, "(", -1));
                index++;
            }
            if (input.charAt(i)== ')') {
                tokens.add(index, new Token(TokenType.CLOSE, ")", -1));
                index++;
            }
            if (operatorPriority.containsKey(input.charAt(i))) {
                tokens.add(index, new Token(TokenType.OPERATOR,String.valueOf(input.charAt(i)),operatorPriority.get(input.charAt(i))));
                index++;
            }
            if (NUMBERS.contains(input.charAt(i))) {
                do {
                    tempNumber.append(input.charAt(i));
                    i++;
                }while (i<input.length()&& NUMBERS.contains(input.charAt(i)));
                i--;
                tokens.add(index, new Token(TokenType.CONST,tempNumber.toString(),-1));
                tempNumber.delete(0,tempNumber.length());
                index++;
            }
        }
        return tokens;
    }

    public Expr buildFromTokens(List<Token> tokens) throws IllegalArgumentException{
        Integer minPriorityOperator = null;
        int openCount=0;

        if (tokens.size()==1&&tokens.get(0).type==TokenType.CONST){
            return new Const(Integer.parseInt(tokens.get(0).value));
        }

        for (int i=0; i<tokens.size();i++) {
            if (tokens.get(i).value == "(") {
                do {
                    if (tokens.get(i).value == "(") {
                        openCount++;
                    }
                    if (tokens.get(i).value == ")") {
                        openCount--;
                    }
                    i++;
                } while (i < tokens.size() && openCount != 0);

                if (i == tokens.size() & minPriorityOperator==null) {
                    return buildFromTokens(tokens.subList(1, tokens.size() - 1));
                }
                i--;
            }
            if (operatorPriority.containsKey(tokens.get(i).value.charAt(0)) & minPriorityOperator == null) {
                minPriorityOperator = i;
            }
            if (operatorPriority.containsKey(tokens.get(i).value.charAt(0)) && minPriorityOperator != null && tokens.get(i).priority < tokens.get(minPriorityOperator).priority) {
                minPriorityOperator = i;
                break;
            }
        }
        if (minPriorityOperator==null){
            throw new IllegalArgumentException("Wrong expression");
        }
        if (minPriorityOperator==0&!tokens.get(minPriorityOperator).value.equals("—")){
            throw new IllegalArgumentException("Wrong expression");
        }
        switch (tokens.get(minPriorityOperator).value){
            case "+": return new Add(buildFromTokens(tokens.subList(0, minPriorityOperator)), buildFromTokens(tokens.subList(minPriorityOperator+1, tokens.size())));
            case "-": return new Subt(buildFromTokens(tokens.subList(0,minPriorityOperator)),buildFromTokens(tokens.subList(minPriorityOperator+1,tokens.size())));
            case "*": return new Mult(buildFromTokens(tokens.subList(0,minPriorityOperator)),buildFromTokens(tokens.subList(minPriorityOperator+1,tokens.size())));
            case "/": return new Div(buildFromTokens(tokens.subList(0, minPriorityOperator)), buildFromTokens(tokens.subList(minPriorityOperator+1, tokens.size())));
            case "^": return new Pow(buildFromTokens(tokens.subList(0,minPriorityOperator)),buildFromTokens(tokens.subList(minPriorityOperator+1,tokens.size())));
            case "—": return new Mult(new Const(-1),buildFromTokens(tokens.subList(minPriorityOperator+1,tokens.size())));
        }
        return null;
    }


    @Override
    public Expr build(String input) {
        Expr resultExpression = null;




        return resultExpression;
    }

}
