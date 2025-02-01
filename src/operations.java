public class operations
{
    //plus
    double add(double a, double b){
     return a+b;
    }
    //minus
    double subtract(double a, double b){
        return a-b;
    }

    //multiply
    double multiply(double a, double b){
        return a*b;
    }

    //divide
    double divide(double a, double b){
        if (b == 0){
            System.out.println("Cannot divide by 0");
            return 0;
        } else
            return a/b;
    }
}
