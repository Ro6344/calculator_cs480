//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //ask user for input
        System.out.println("Enter your expression: ");

        //scanner declaration
        Scanner keyboard = new Scanner(System.in);

        //variable for expression
        String expression = keyboard.nextLine();

        //tokenize expression
        interpretExpression ie = new interpretExpression();

        ArrayList<String> tokens = ie.tokenize(expression);

        //pass tokenized expression to shunting yard algorithm
        System.out.println(ie.shuntingYardAlg(tokens));

        System.out.println(tokens);

    }
}