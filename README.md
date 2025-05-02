# Arbitrary Precision Arithmetic Library

This Java library supports arbitrary-precision arithmetic operations for integers and floating-point numbers.

### Features:
- Addition, Subtraction, Multiplication, Division
- Supports large numbers beyond the default Java types

### Uses:
- Operations can be done for very large numbers beyond the limits of standard data types like `int` and `float`.
- The signs of the respective numbers are considered according to the logic.
- Large number operations are handled using strings.

### How to build:
- Use the following Ant command to build the project:
  ```bash
  ant jar

*The above will generate the .jar files in the build/ folder  
  
#### Using the JAR to run the code:  
```bash
java -jar build/arbitraryarithmetic.jar int add 123456789 987654321
```
    
### Using the classes:
```bash
java -cp build arbitraryarithmetic.MyInfArith int div 25 125
```

#### Using the python compiler which compiles the java codes: 
```bash
python3 run_project.py int add 123 456  
```

### Syntax: 
```bash
java -jar build/arbitraryarithmetic.jar [type] [operation] [num1] [num2]
```
(The above when we use the .jar files in the buld folder)  

```bash
java -cp build arbitraryarithmetic.MyInfArith [type] [operation] [num1] [num2]
```
(While using the classes)  

```bash
python3 run_project.py [type] [operation] [num1] [num2]
```
(while using the python compiler)  
 
