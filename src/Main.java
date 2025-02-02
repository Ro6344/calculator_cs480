
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //ask user for input
        System.out.println("Enter your expression: ");

        //scanner declaration
        Scanner keyboard = new Scanner(System.in);

        //variable for expression
        String expression = keyboard.nextLine();

        //tokenize expression
        InterpretExpression ie = new InterpretExpression();
        Operations op = new Operations();
        ArrayList<String> tokens = ie.tokenize(expression);

        //pass tokenized expression to Shunting Yard algorithm to convert to postfix
        ArrayList<String> postfixTokens = ie.shuntingYardAlg(tokens);

        //print the postfix expression for debugging
        System.out.println("Postfix Expression: " + postfixTokens);

        //calculate the result using postfix evaluation
        double result = op.evaluatePostfix(postfixTokens);

        //print the final result
        if (!Double.isNaN(result)) {
            System.out.println("Result: " + result);
        }
    }
}
