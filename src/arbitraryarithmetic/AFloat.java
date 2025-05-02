package arbitraryarithmetic;

public class AFloat {
    private String value;
    public boolean is_negative;//for checking the sign of the string

    public AFloat() {
        this.value = "0.0";
        //default value
        this.is_negative = false;//the default is positive
    }

    public static String make_decimal_point(String s) {
        if(!s.contains(".")) { //in case the point is not present then adding the point at last of the string
            return s + ".0";
        }
        return s;
    }


    //constructor from a string
    public AFloat(String s){
        if (s.charAt(0) == '-'){
            this.is_negative = true;//if it is negative
            s = s.substring(1);
            this.value = make_decimal_point(s);
        }
        else{
            this.is_negative = false;
            this.value = make_decimal_point(s);
        }
    }

    //copy constructor
    public AFloat (AFloat other_num) {
        this.value = other_num.value;
        this.is_negative = other_num.is_negative;
    }

    //parse method
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    //get value
    public String getvalue() {
        return (is_negative ? "-" : "") + this.value;
    }

    //removing leading zeroes
    public static String removeleadingzeros(String s) {
        if (!s.contains(".")) {
            return s.replaceFirst("^0+(?!$)", "");
        }
        String[] parts = s.split("\\.");
        String int_part = parts[0].replaceFirst("^0+(?!$)", "");
        String decimal_part = parts.length > 1? parts[1] : "0";
        if (int_part.isEmpty()) {
            int_part = "0";
        }
        return int_part + "." + decimal_part;
    }

    //comparision between two numbers
    public int comparing (AFloat other_num) {
        String[] this_parts = this.value.split("\\.");
        String[] other_parts = other_num.value.split("\\.");

        String this_int = removeleadingzeros(this_parts[0]);
        String other_int = removeleadingzeros(other_parts[0]);

        if (this_int.length() > other_int.length()){
            return 1;
        }
        if (this_int.length() < other_int.length()){
            return -1;
        }
        int compar = this_int.compareTo(other_int);     
        if (compar != 0){
            return compar;
        }

        //check if the length of the parts is >1 or... which tell us whehter do we have the decimal digits or not
        String this_decimal = this_parts.length > 1 ? this_parts[1] : "0";
        String other_decimal = other_parts.length > 1 ? other_parts[1] : "0";

        int max_length = Math.max(this_decimal.length(), other_decimal.length());

        //for adding extra zeroes according to the maximum length       //
        while (this_decimal.length() < max_length) {
            this_decimal += "0";
        }
        while (other_decimal.length() < max_length) {
            other_decimal += "0";
        }

        int decimal_comparision = this_decimal.compareTo(other_decimal);
        return decimal_comparision;
    }

    //ADDITION
    public AFloat add(AFloat other_num) {
        if(this.is_negative == other_num.is_negative){
            //both same sign
            String[] first_parts = this.value.split("\\.");
            String[] second_parts = other_num.value.split("\\.");

            String first_int = first_parts[0];
            String second_int = second_parts[0];

            String first_decimal = first_parts.length > 1 ? first_parts[1] : "0";
            String second_decimal = second_parts.length > 1 ? second_parts[1] : "0";

            //adding the extra digits in the decimal to have equal lengths to do any functions
            int max_decimal_length = Math.max(first_decimal.length(), second_decimal.length());
            while (first_decimal.length() < max_decimal_length){
                first_decimal += "0";
            }
            while (second_decimal.length() < max_decimal_length){
                second_decimal += "0";
            }

            //decimal addition
            StringBuilder decimal_result = new StringBuilder();
            int carry = 0;
            for (int i=max_decimal_length - 1; i>= 0; i--) {
                int dig1 = first_decimal.charAt(i) - '0';
                int dig2 = second_decimal.charAt(i) - '0';
                int sum_decimal = dig1+dig2+carry;
                decimal_result.append(sum_decimal % 10);
                carry = sum_decimal/10;
            }
            decimal_result.reverse();

            StringBuilder int_result = new StringBuilder();
            String rev_first_int = new StringBuilder(first_int).reverse().toString();
            String rev_second_int = new StringBuilder(second_int).reverse().toString();
            
            int max_int_length = Math.max(rev_first_int.length(), rev_second_int.length());
            for (int i=0; i<max_int_length;i++){
                int dig1 = i < rev_first_int.length() ? rev_first_int.charAt(i)-'0' : 0;
                int dig2 = i < rev_second_int.length() ? rev_second_int.charAt(i)-'0' : 0;
                int sum_int = dig1+dig2+carry;
                int_result.append(sum_int%10);
                carry = sum_int/10;
            }
            if (carry > 0) {
                int_result.append(carry);
            }

            int_result.reverse();
            String final_value = removeleadingzeros(int_result.toString()) + "." + decimal_result.toString().replaceAll("0+$", "");
            AFloat result = new AFloat(final_value);
            result.is_negative = this.is_negative;
            return result;
        }
        else{
           if (this.comparing(other_num) >= 0){
            AFloat result = this.subtract(other_num);
            result.is_negative = this.is_negative;
            return result;
           }
           else{//subtracting the this from other
            AFloat result = other_num.subtract(this);
            result.is_negative = other_num.is_negative;
            return result;
           }
        }
    }

    //SUBTRACTION
    public AFloat subtract(AFloat other_num) {
        if (this.is_negative == other_num.is_negative) {
            String[] first_parts = this.value.split("\\.");
            String[] second_parts = other_num.value.split("\\.");
            //the numbers being splited

            String first_int = removeleadingzeros(first_parts[0]);
            String second_int = removeleadingzeros(second_parts[0]);

            String first_dec = first_parts.length > 1? first_parts[1] : "0";
            String second_dec = second_parts.length > 1 ? second_parts[1] : "0";
            //if the length is > 1 then it means that it does have some decimal digits otherwise adding the 0

            int max_deci_len = Math.max(first_dec.length(), second_dec.length());
            //adding zeros to both the decimals in order to have the same length
            while (first_dec.length() < max_deci_len) {
                first_dec += "0";
            }
            while (second_dec.length() < max_deci_len) {
                second_dec += "0";
            }

            String first_num = first_int + "." + first_dec;
            String second_num = second_int + "." + second_dec;
            
            int compared = this.comparing(other_num);
            if (compared == 0){
                return new AFloat("0.0");
            }//if the numbers are equal then return 0.0

            boolean res_negative = false;
            if (compared < 0){
                res_negative = true;//to tell that they are negative
                //swapping the int
                String temp = first_int;
                first_int = second_int;
                second_int = temp;
                //swapping the decimals
                String temp1 = first_dec;
                first_dec = second_dec;
                second_dec = temp1;
            }
            //subtracion of the decimal part
            StringBuilder dec_res = new StringBuilder();
            int borrow = 0;

            for(int i = max_deci_len-1; i >= 0; i--) {
                int dig1 = first_dec.charAt(i)-'0';
                int dig2 = second_dec.charAt(i) - '0';

                int sub = dig1-dig2-borrow;
                if(sub < 0){
                    sub += 10;
                    borrow = 1;
                }
                else{
                    borrow = 0;
                }
                dec_res.append(sub);
            }
            dec_res.reverse();

            //subtraction fo the int parts by reversing them
            StringBuilder int_res = new StringBuilder();
            String rev_first_int = new StringBuilder(first_int).reverse().toString();
            String rev_second_int = new StringBuilder(second_int).reverse().toString();

            for(int i=0; i<rev_first_int.length(); i++){
                int dig1 = rev_first_int.charAt(i) - '0';
                int dig2 = (i < rev_second_int.length()) ? rev_second_int.charAt(i)-'0' : 0;

                int sub = dig1-dig2-borrow;
                if(sub < 0){
                    sub += 10;
                    borrow = 1;
                }
                else{
                    borrow = 0;
                }
                int_res.append(sub);
            }

            String final_int = removeleadingzeros(int_res.reverse().toString());
            String final_dec = dec_res.toString().replaceAll("0+$", "");
            if (final_dec.isEmpty()){
                final_dec = "0";
            }
            String final_value = final_int+"."+final_dec;
            AFloat result = new AFloat(final_value);
            result.is_negative = res_negative ? !this.is_negative : this.is_negative;
            return result;
        }
        else{
            AFloat result = this.add(other_num);
            result.is_negative = this.is_negative;
            return result;
        }
    }

    //MULTIPLICATION
    public AFloat multiply(AFloat other_num){
        String[] first_parts = this.value.split("\\.");
        String[] second_parts = other_num.value.split("\\.");

        String first_int = removeleadingzeros(first_parts[0]);
        String second_int = removeleadingzeros(second_parts[0]);
        String first_dec = first_parts.length > 1 ? first_parts[1] : "0";
        String second_dec = second_parts.length > 1 ? second_parts[1] : "0";

        String first = first_int+first_dec;
        String second = second_int+second_dec;
        //making them as the whole number in order to have easy multiplication

        int first_dec_len = first_dec.length();
        int second_dec_len = second_dec.length();
        int l1 = first.length();
        int l2 = second.length();

        int[] result = new int[l1+l2];

        for(int i = l1 - 1; i>=0; i--){
            int dig1 = first.charAt(i)-'0';
            for(int j = l2 - 1; j>=0; j--){
                int dig2 = second.charAt(j)-'0';
                int product = dig1*dig2;

                int sum = product + result[i+j+1];
                result[i+j+1] = sum%10;
                result[i+j] += sum/10;
            }
        }
        //convert the array into a string
        StringBuilder result_multiply = new StringBuilder();
        for(int i = 0; i < result.length; i++){
            result_multiply.append(result[i]);
        }

        //remove the leading zero
        String final_res = removeleadingzeros(result_multiply.toString());

        int total_dec_places = first_dec_len+second_dec_len;
        while (final_res.length() <= total_dec_places){
            final_res = "0" + final_res;//to make sure the string has enough length for keeping the decimal point
        }
        String intpart = final_res.substring(0, final_res.length()-total_dec_places);
        String decpart = final_res.substring(final_res.length() - total_dec_places);

        final_res = intpart + "." + decpart;
        //this is the final addition result in the form of decimal points also

        boolean isit_negative;
        if((this.is_negative && !other_num.is_negative) || (!this.is_negative && other_num.is_negative)){
            isit_negative = true;
        }
        else {
            isit_negative = false;
        }
        final_res = isit_negative ? "-" + final_res : final_res;
        return new AFloat(final_res);
    }

    //DIVISION
    public AFloat division(AFloat other_num){
       if(other_num.value.equals("0.0")){
            throw new ArithmeticException("Division by zero");
        }
        if (this.value.equals("0.0")){
            return new AFloat("0.0");//if the dividend is zero then the wuotient will be 0
        }

        String[] firstparts = this.value.split("\\.");
        String[] secondparts = other_num.value.split("\\.");

        String first_int = firstparts[0];
        String second_int = secondparts[0];
        String first_dec = firstparts.length > 1 ? firstparts[1] : "";
        String second_dec = secondparts.length > 1 ? secondparts[1] : "";

        //removing the point and then considering the number as an integer
        String numerator = first_int+first_dec;
        String denominator = second_int+second_dec;

        //number of digits for the decimal count
        int first_dec_places = first_dec.length();
        int second_dec_places = second_dec.length();
        int total_dec_places = first_dec_places-second_dec_places;

        
        AFloat denominatorAFloat = new AFloat(denominator);
        //for the subtraction and comparision we are converting this into afloat

        int extra_dec_digits = 30;
        numerator += "0".repeat(extra_dec_digits);//add extra zeroes to the numerator
        total_dec_places += extra_dec_digits;//updating the total decimal places with adding the extra zeroes upto to 30

        StringBuilder quotient = new StringBuilder();
        String current = "";

        for(int i =0; i< numerator.length(); i++){
            current += numerator.charAt(i);
            current = removeleadingzeros(current);

            AFloat currentAFloat = new AFloat(current);

            int r = 0;//count for the quotient or the quotient digit
            while(currentAFloat.comparing(denominatorAFloat) >= 0){
                currentAFloat = currentAFloat.subtract(denominatorAFloat);
                r++;
            }
            quotient.append(r);
        }
        String quotientStr = quotient.toString();
        while(quotientStr.length() <= total_dec_places){
            quotientStr = "0"+quotientStr;//to make sure the quotient has enough digits
        }
        String int_part = quotientStr.substring(0, quotientStr.length() - total_dec_places);
        String dec_part = quotientStr.substring(quotientStr.length() - total_dec_places);
        String final_res = int_part + "." + dec_part;

        final_res = final_res.replaceAll("\\.?0+$", "");
        //writing 2.5000.. as 2.5

        boolean isit_negative = (this.is_negative != other_num.is_negative);
        if(isit_negative && !final_res.replace(".","").equals("0")){
            final_res = "-" + final_res;
        }
        return new AFloat(final_res);
    }
}