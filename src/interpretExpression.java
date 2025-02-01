import java.util.ArrayList;
import java.util.Stack;

public class interpretExpression {
    //stack to hold numbers
    Stack<Integer> output = new Stack<>();
    //stack to hold operators
    Stack<Character> op = new Stack<>();

    //method to tokenize string
    public ArrayList<String> tokenize (String expression) {
        //array list to hold tokens
        ArrayList<String> tokens = new ArrayList<>();

        //String builder to temp hold
        StringBuilder nums = new StringBuilder();
        StringBuilder func = new StringBuilder();

        //iterate through expression
        for (int i = 0; i < expression.length(); i++) {
            //set current char
            char curr = expression.charAt(i);

            //ignore spaces
            if (Character.isWhitespace(curr)) {
                continue;
            }

            //handle numbers
            if (Character.isDigit(curr) || curr == '.') {
                nums.append(curr);
                continue;
            }

            //if a non-number is encountered
            if (!nums.isEmpty()) {
                //add previous token to array list
                tokens.add(nums.toString());
                //clear num string builder
                nums.setLength(0);
            }

            //handle functions
            if (Character.isLetter(curr)) {
                func.append(curr);
                continue;
            }
            //if a non-letter is encountered
            if (!func.isEmpty()) {
                //validate the function
                if (validateTrig(func.toString())) {
                    //add token to array list
                    tokens.add(func.toString());
                } else {
                    System.out.println("Invalid function: " + func);
                }
                //clear temp string builder
                func.setLength(0);
            }

            //handle operators
            if (validateChar(curr)) {
                tokens.add(String.valueOf(curr));
            } else {
                System.out.println("Invalid Character: " + curr);
            }
        }
            if (!nums.isEmpty()) {
                tokens.add(nums.toString());
            }
            if (!func.isEmpty()) {
                if (validateTrig(func.toString())) {
                    tokens.add(func.toString());
                } else {
                    System.out.println("Invalid function at the end: " + func);
                }
            }
        return tokens;
    }

    //method to ensure symbols are limited
    boolean validateChar(char a){
        return (a == '{' || a == '}' || a == '(' || a == ')' || a == '^' || a == '*' || a == '/' || a == '+' || a == '-');

    }

    //method to ensure only certain functions are accepted
    boolean validateTrig(String func){
        return func.equals("sin") || func.equals("cos") || func.equals("tan") || func.equals("cot") || func.equals("ln") || func.equals("log10");
    }

    //method to determine precedence

}
