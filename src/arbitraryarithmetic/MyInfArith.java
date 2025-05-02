package arbitraryarithmetic;

import arbitraryarithmetic.AFloat;
import arbitraryarithmetic.AInteger;

public class MyInfArith {
    public static void main(String[] args){
        //checking if there are correct command line arguments
        if(args.length < 4){
            System.out.println("java MyInfArith 'type' 'operation' 'num1' 'num2'");
            return;//if the correct commands are not given then return
        }
        //taking the arguments
        String type = args[0];
        String operation = args[1];
        String num1 = args[2];
        String num2 = args[3];

        //for integer numbers
        if("int".equals(type)){
            AInteger integer1 = new AInteger(num1);
            AInteger integer2 = new AInteger(num2);
            
            switch (operation) {
                case "add":
                    System.out.println(integer1.add(integer2).getvalue());
                    break;
                case "sub":
                    System.out.println(integer1.subtract(integer2).getvalue());
                    break;
                case "mul":
                    System.out.println(integer1.multiply(integer2).getvalue());
                    break;
                case "div":
                    System.out.println(integer1.division(integer2).getvalue());
                    break;
                default:
                    System.out.println("unknown operation");
                    break;
            }
        }
        //for float numbers
        else if ("float".equals(type)){
            AFloat float1 = new AFloat(num1);
            AFloat float2 = new AFloat(num2);
            switch (operation) {
                case "add":
                    System.out.println(float1.add(float2).getvalue());
                    break;
                case "sub":
                    System.out.println(float1.subtract(float2).getvalue());
                    break;
                case "mul":
                    System.out.println(float1.multiply(float2).getvalue());
                    break;
                case "div":
                    System.out.println(float1.division(float2).getvalue());
                    break;
                default:
                    System.out.println("unknown operation");
                    break;
            }
        }
        else {
            System.out.println("Enter int or float only");
        }
    }
}