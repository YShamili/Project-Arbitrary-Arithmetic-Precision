package arbitraryarithmetic;

public class AInteger {

    private String value;//store the number as an integer
    private boolean is_negative;//to check whether the number is negative or positive

    public AInteger() {
        this.value = "0";
        this.is_negative = false;//default is positive
    }

    //constructor from the string
    public AInteger(String s) {
        if (s.charAt(0) == '-') {
            this.is_negative = true;//if it is negative
            this.value = s.substring(1);//remove the negative sign for the further
        } 
        else {
            this.is_negative = false;
            this.value = s;
        }
    }

    //copy constructor
    public AInteger (AInteger other_num) {
        this.is_negative = other_num.is_negative;
        this.value = other_num.value;
    }

    //parsing that creates a new instance of AInteger and return it
    public static AInteger parse(String s) {
        return new AInteger(s);
    }

    //helper to remove the leading zeros
    public static String removeLeadingzeros(String s) {
        return s.replaceFirst("^0+(?!$)", "");//replacind leading zeros with an empty string
    }

    //helping methods for comaparing
    public int comparing(AInteger other_num) {
        if (this.is_negative && !other_num.is_negative) {
            return -1;//this is negativa and other is positive
        }
        if (!this.is_negative && other_num.is_negative) {
            return 1;//this is positive and other is negative
        }
        String thisval = removeLeadingzeros(this.value);
        String otherval = removeLeadingzeros(other_num.value);

        //if both are positive or negaive , compare the absolute values
        if (thisval.length() > otherval.length()) {
            return this.is_negative ? -1 : 1;
        }
        else if (thisval.length() < otherval.length()) {
            return this.is_negative ? 1 : -1;
        }
        else {
            return thisval.compareTo(otherval);
        }
    }

    //get value
    public String getvalue() {
        return is_negative && !value.equals("0") ? "-" + value : value;
    }

    //ADDITION
    public AInteger add(AInteger other_num) {
        if(this.is_negative == other_num.is_negative) {//same sign
            String first = this.value;
            String second = other_num.value ;

            first = removeLeadingzeros(first);
            second = removeLeadingzeros(second);
            //removing the leading zeroes before reversing for safe side

            StringBuilder result = new StringBuilder();
            String reversed_num1 = new StringBuilder(first).reverse().toString();
            String reversed_num2 = new StringBuilder(second).reverse().toString();
            //reversing the first and second numbers 

            int carry = 0;
            int max_length = Math.max(first.length(), second.length());
            //finding the maximum length required for the for loop

            for(int i =0; i<max_length; i++) {
                int digit1 = i < reversed_num1.length() ? reversed_num1.charAt(i) - '0' : 0;
                int digit2 = i < reversed_num2.length() ? reversed_num2.charAt(i) - '0' : 0;
                //converting the char into integer
                //if the number is shorter, then placing 0 as the missing digits
                int sum = digit1 + digit2 + carry;
                result.append(sum % 10);
                carry = sum / 10;
            }
        
            //appending the other carry in the result
            if(carry != 0){
                result.append(carry);
            }
        
            String final_result = removeLeadingzeros(result.reverse().toString());
            //this is the answer after addition

            //for the sign to be kept in front of the answer 
            AInteger result_number = new AInteger(final_result);
            result_number.is_negative = this.is_negative;
            return result_number;
        }
        else{
            //if signs are different ,subtract the smaller from the larger
            if(this.comparing(other_num) >= 0) {//this is greater ot equal to other_num
                AInteger result_number = this.subtract(other_num);
                result_number.is_negative = this.is_negative;
                return result_number;
            }
            else{
                //other is greater
                AInteger result_number = other_num.subtract(this);
                result_number.is_negative = other_num.is_negative;
                return result_number;
            }
        }
    }

    //SUBTRACTION
    public AInteger subtract(AInteger other_num) {
        if(this.is_negative == other_num.is_negative) {
            String first = this.value;
            String second = other_num.value;

            //removing the leading zeroes in case in input we entered as 0100 then we may get wrong answer
            first = removeLeadingzeros(first);
            second = removeLeadingzeros(second);

            int compared = this.comparing(other_num);
            if (compared == 0) {//numbers are equal
                return new AInteger("0");
            }

            boolean res_negative = false;
            if(compared < 0) {
                res_negative = true;//means that first is smaller than the second              
                //swapping 
                String temp = first;
                first = second;
                second = temp;
            }

            StringBuilder result = new StringBuilder();

            String rev_num1 = new StringBuilder(first).reverse().toString();
            String rev_num2 = new StringBuilder(second).reverse().toString();
            //reversed the numbers

            int borrow = 0;//for borrowing
            for(int i =0; i < rev_num1.length(); i++){
                int digit1 = rev_num1.charAt(i) - '0';
                int digit2 = (i < rev_num2.length()) ? rev_num2.charAt(i) - '0' : 0;

                int sub = digit1 - digit2 - borrow;
                if(sub < 0){
                    sub += 10;
                    borrow = 1;
                }
                else{
                    borrow = 0;
                }
                result.append(sub);
            }
            String finalresult = removeLeadingzeros(result.reverse().toString());
            AInteger result_number = new AInteger(finalresult);
            result_number.is_negative = res_negative ;
            return result_number;
        }
        else{
            //if the signs are different , do addtion
            AInteger result_number = this.add(other_num);
            result_number.is_negative = this.is_negative;//according to the sign of the big number we are placing he sign
            return result_number;
        }
    }
    
    //MULTIPLICATION
    public AInteger multiply(AInteger other_num) {
        String first = this.value;
        String second = other_num.value;

        //if either of the number is 0 then returning the answer as 0
        if (first.equals("0") || second.equals("0")) {
            return new AInteger("0");
        }
        int len1 = first.length();
        int len2 = second.length();

        int[] result = new int[len1+len2];
        for (int i = len1-1; i>=0; i--){
            //starting from the last digit of the string
            int dig1 = first.charAt(i) - '0';
            for(int j = len2-1; j>=0; j--){
                int dig2 = second.charAt(j) - '0';
                int product = dig1 * dig2;

                int sum = product + result[i+j+1];
                result[i+j+1] = sum % 10;
                result[i+j] += sum/10;
            }
        }

        StringBuilder result_multiply = new StringBuilder();
        //converting the array into a string
        for(int i=0; i<result.length; i++){
            result_multiply.append(result[i]);
        }

        //remove leading zeros
        String final_res = removeLeadingzeros(result_multiply.toString());
        
        //to check whehter the inputs are negative or positive
        boolean isit_negative;

        if((this.is_negative && !other_num.is_negative) || (!this.is_negative && other_num.is_negative)){
            isit_negative = true;
        }
        else {
            isit_negative = false;
        }
        final_res = isit_negative ? "-"+final_res : final_res;
        return new AInteger(final_res);
    }

    //DIVISION
    public AInteger division(AInteger other_num) {
        String dividend = this.value;
        String divisor = other_num.value;
 
        if (divisor.equals("0") ) {
            throw new ArithmeticException("Division by zero");
        }
        if (dividend.equals("0")) {
            return new AInteger("0");
        }

        AInteger divi_int = new AInteger(dividend);
        AInteger divisor_int = new AInteger(divisor);

        //if the dividend is smaller than the divisor
        if (divi_int.comparing(divisor_int) < 0){
            return new AInteger("0");
        }

        StringBuilder quotient = new StringBuilder();
        String current = "";
        AInteger divise_int = new AInteger(divisor);

        for (int i = 0; i<dividend.length(); i++) {
            current += dividend.charAt(i);
            current = removeLeadingzeros(current);
            //removing the leading zeros

            int r = 0;//count for the quotient
            AInteger present_int = new AInteger(current);

            while (present_int.comparing(divise_int) >=0){
                present_int = present_int.subtract(divise_int);
                r++;
            }
            quotient.append(r);
            current = present_int.getvalue();//the remainder goes here
        }
        
        String final_result = removeLeadingzeros(quotient.toString());

        boolean isit_negative;

        if((this.is_negative && !other_num.is_negative) || (!this.is_negative && other_num.is_negative)){
            isit_negative = true;
        }
        else {
            isit_negative = false;
        }
        final_result = isit_negative ? "-" + final_result : final_result;
        return new AInteger(final_result);
    }

}