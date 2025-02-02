import java.util.ArrayList;
import java.util.Stack;

public class Operations {
    //add method
    double add(double a, double b) {
        return a + b;
    }

    //subtract method
    double subtract(double a, double b) {
        return a - b;
    }

    //multiply method
    double multiply(double a, double b) {
        return a * b;
    }

    //divide method
    double divide(double a, double b) {
        if (b == 0) {
            System.out.println("Cannot divide by 0");
            return 0;
        } else
            return a / b;
    }

    //method to exponents
    double power(double a, double b) {
        return Math.pow(a, b);
    }

    //method to evaluate postfix expression
    public double evaluatePostfix(ArrayList<String> postfix) {
        //stack to store numbers for evaluation
        Stack<Double> stack = new Stack<>();

        //iterate theough each token in the postfix expression
        for (String token : postfix) {
            //if the token is a number push it to the stack
            try {
                double num = Double.parseDouble(token);
                stack.push(num);
                continue;
            } catch (NumberFormatException ignored) {}

            //if it's an operator, pop two values and apply the operation
            if (token.equals("+")) {
                stack.push(add(stack.pop(), stack.pop()));//addition
            } else if (token.equals("-")) {
                double b = stack.pop();//get the second operand
                double a = stack.pop(); //get the first operand
                stack.push(subtract(a, b)); //perform subtraction
            } else if (token.equals("*")) {
                stack.push(multiply(stack.pop(), stack.pop()));
            } else if (token.equals("/")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(divide(a, b));
            } else if (token.equals("^")) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(power(a,b));
            }

            //if it's a function pop one value and apply the function
            else if (token.equals("sin")) {
                stack.push(Math.sin(Math.toRadians(stack.pop())));//compute sine in degrees
            } else if (token.equals("cos")) {
                stack.push(Math.cos(Math.toRadians(stack.pop())));//compute cosine in degrees
            } else if (token.equals("tan")) {
                stack.push(Math.tan(Math.toRadians(stack.pop()))); //compute tan in degrees
            } else if (token.equals("log10")) {
                stack.push(Math.log10(stack.pop()));
            } else if (token.equals("ln")) {
                stack.push(Math.log(stack.pop()));
            } else {
                System.out.println("Invalid token in postfix: " + token);
                return Double.NaN;
            }
        }

        //the final result should be the only number left in the stack
        if (stack.size() == 1) {
            return stack.pop();//return the final computed result
        } else {
            System.out.println("Error: Postfix expression did not resolve to a single value");

        }
        return Double.NaN;
    }
}
