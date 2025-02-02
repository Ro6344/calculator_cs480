import java.util.*;

public class InterpretExpression {
    //method to tokenize string
    public ArrayList<String> tokenize (String expression) {
        //array list to hold tokens
        ArrayList<String> tokens = new ArrayList<>();

        //temporary holder for numbers and function names
        StringBuilder nums = new StringBuilder();
        StringBuilder func = new StringBuilder();

        //iterate through each character in the expression
        for (int i = 0; i < expression.length(); i++) {
            //set current char
            char curr = expression.charAt(i);

            //ignore spaces
            if (Character.isWhitespace(curr)) {
                continue;
            }

            //handle numbers and decimal points
            if (Character.isDigit(curr) || curr == '.') {
                nums.append(curr);
                continue;
            }

            //if a non-number is encountered store the collected number token
            if (!nums.isEmpty()) {
                //add previous token to array list
                tokens.add(nums.toString());
                //clear num string builder
                nums.setLength(0);
            }

            //handle function names like sin, cos, and tan
            if (Character.isLetter(curr)) {
                func.append(curr);
                continue;
            }
            //validate ans store function names
            if (!func.isEmpty()) {
                //validate the function
                if (validateTrig(func.toString())) {
                    if (i < expression.length() && expression.charAt(i) == '(') {
                        tokens.add(func.toString());
                    } else{
                        System.out.println("Error: Invalid function usage");
                        return new ArrayList<>();
                    }
                } else {
                    System.out.println("Invalid function: " + func);
                    return new ArrayList<>();
                }
                //clear temp string builder
                func.setLength(0);
            }

            //handle operators and special characters
            if (validateChar(curr)) {
                tokens.add(String.valueOf(curr));
            } else {
                System.out.println("Invalid Character: " + curr);
            }
        }
            //add remaining numbers or function to the token list
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
    //method to validate if a string is a number
    boolean validateNums(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //method to validate math operators
    boolean validateChar(char a){
        return "{}()^*/+-".indexOf(a) != -1;

    }


    boolean validateChar(String a) {
        return "+-*/^".contains(a);
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
    ArrayList<String> shuntingYardAlg(ArrayList<String> tokens) {
        //output list
        ArrayList<String> output = new ArrayList<>();
        //operator stack
        Stack<String> operator = new Stack<>();
        Stack<Character> bracketCheck = new Stack<>();  //track open brackets

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            //handle Numbers
            if (validateNums(token)) {
                output.add(token);
            }
            //handle Functions (sin, cos, etc.)
            else if (validateTrig(token)) {
                operator.push(token);
            }
            //handle Unary Minus (-x -> -1 * x)
            else if (token.equals("-") && (i == 0 || validateChar(tokens.get(i - 1)) || tokens.get(i - 1).equals("("))) {
                output.add("-1");
                operator.push("*");
            }
            //handle Operators (+, -, *, /, ^)
            else if (validateChar(token)) {
                while (!operator.isEmpty() && validateChar(operator.peek())) {
                    String top = operator.peek();
                    if ((isLeftAssociative(token.charAt(0)) && getPrecedence(token.charAt(0)) <= getPrecedence(top.charAt(0))) ||
                            (!isLeftAssociative(token.charAt(0)) && getPrecedence(token.charAt(0)) < getPrecedence(token.charAt(0)))) {
                        output.add(operator.pop());
                    } else {
                        break;
                    }
                }
                operator.push(token);
            }
            //handle left parentheses & Curly Braces
            else if (token.equals("(") || token.equals("{")) {
                operator.push(token);
                bracketCheck.push(token.charAt(0));  // Track brackets
            }
            //handle Right Parentheses & curly Braces
            else if (token.equals(")") || token.equals("}")) {
                boolean foundLeft = false;

                while (!operator.isEmpty()) {
                    String top = operator.peek();
                    if ((token.equals(")") && top.equals("(")) || (token.equals("}") && top.equals("{"))) {
                        operator.pop();
                        foundLeft = true;
                        bracketCheck.pop();  // Remove matching bracket
                        break;
                    } else {
                        output.add(operator.pop());
                    }
                }

                if (!foundLeft) {
                    System.out.println("Error: Mismatched parentheses or braces.");
                    return new ArrayList<>();
                }

                // Pop function if it's on top
                if (!operator.isEmpty() && validateTrig(operator.peek())) {
                    output.add(operator.pop());
                }
            }
        }

        //final Cleanup: Pop remaining operators
        while (!operator.isEmpty()) {
            String op = operator.pop();
            if (op.equals("(") || op.equals("{")) {
                System.out.println("Error: Mismatched parentheses or braces.");
                return new ArrayList<>();
            }
            output.add(op);
        }

        //nsure all brackets `{` or `(` were closed
        if (!bracketCheck.isEmpty()) {
            System.out.println("Error: Unmatched parentheses or curly braces.");
            return new ArrayList<>();
        }

        return output;
    }
}
