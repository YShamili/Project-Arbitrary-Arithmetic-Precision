#Arbitrary precision Arithmetic Library:  
This java library supports arbitrary-precision arithmetic operations for integers and floating point numbers.  
  
###Features:  
-Addition, Subtraction, Multiplication, Division  
-Supports large numbers beyond default Java types  
  
###What is the uses:  
-The operations can be done for the very large numbers beyond the limits of standard data types like int and float  
-Even the sign of the respective numbers are also considered according to the logic .  
-The large numbers ooperations are done in the form of strings  
  
###How to build:  
-Use the ant command to build the project which is: ant jar  
The above will generate the .jar files in the build/ folder  
  
-Using the jar to run the code:   java -jar build/arbitraryarithmetic.jar int add 123456789 987654321  
  
-Using the classes : java -cp build arbitraryarithmetic.MyInfArith int div 25 125  
  
-Using the python compiler which compiles the java codes:   python3 run_project.py int add 123 456  
  
###Syntax:  
java -jar build/arbitraryarithmetic.jar [type] [operation] [num1] [num2]   
(The above when we use the .jar files in the buld folder)  
  
java -cp build arbitraryarithmetic.MyInfArith [type] [operation] [num1] [num2]  
(While using the classes)  
  
python3 run_project.py [type] [operation] [num1] [num2]  
(while using the python compiler)  
 
