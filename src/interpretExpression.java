import java.util.ArrayList;
import java.util.Stack;

public class interpretExpression {
    //stack to hold numbers
    Stack<String> output = new Stack<>();
    //stack to hold operators
    Stack<String> operator = new Stack<>();

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
    //method to validate numbers
    boolean validateNums(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //method to ensure symbols are limited
    boolean validateChar(char a){
        return (a == '{' || a == '}' || a == '(' || a == ')' || a == '^' || a == '*' || a == '/' || a == '+' || a == '-');

    }

    //method to ensure only certain functions are accepted
    boolean validateTrig(String func){
        return func.equals("sin") || func.equals("cos") || func.equals("tan") || func.equals("cot") || func.equals("ln") || func.equals("log10");
    }

    //method to get precedence
    private static int getPrecedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case'^': return 3;
            default:return -1;
        }
    }

    //method to get operator associativity (true = left, false = right)
    private static boolean isLeftAssociative(char op) {
        return op != '^';
    }


    //method to covert to postfix
    Stack<String> shuntingYardAlg(ArrayList<String> expression){

        for (int i = 0; i < expression.size(); i++) {
            String curr = expression.get(i);
            //if a token is a number add it to output stack
            if (validateNums(curr)) {
                output.push(curr);
            }
            //if token is a function
            else if (validateTrig(curr)) {
                operator.push(curr);
            }
            //if a token is an operator
            else if (validateChar(curr.charAt(0))) {
                while (!operator.isEmpty() && validateChar(operator.peek().charAt(0))) {
                    String top = operator.peek();
                    //while higher precedence operator is on stack pop it
                    if ((isLeftAssociative(curr.charAt(0)) && getPrecedence(curr.charAt(0)) <= getPrecedence(top.charAt(0))) ||
                            (!isLeftAssociative(curr.charAt(0)) && getPrecedence(curr.charAt(0)) < getPrecedence(top.charAt(0)))){
                        output.add(operator.pop());
                    } else {
                        break;
                    }
                }
                operator.push(curr);
            }
        //if token is left parenthesis push to stack
            else if (curr.equals("(")) {
                operator.push(curr);
            }
            else if (curr.equals(")")) {
                while (!operator.isEmpty() &&!operator.peek().equals("(")) {
                    output.add(operator.pop());
                }
                operator.pop(); //remove( from the stack
            }
        }
        //pop the remaining operators in the stack
        while (!operator.isEmpty()) {
            output.add(operator.pop());
        }
        return output;
    }
}
