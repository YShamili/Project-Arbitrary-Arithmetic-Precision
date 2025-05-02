import sys#to interact with the command line argumene
import os#to interact with the operating system

def build_project():#function to build the project using ant
    result = os.system("ant build")#using the os.system() to run the ant build command in the shll

    if result == 0:
        print("build successful")
    else:
        print("build failed")
        sys.exit(1)#exiting with 1 indicating that it failed

def run_java(arg):#for running the java code
    command = f"java -cp build arbitraryarithmetic.MyInfArith {' '.join(arg)}"
    res = os.system(command)

    if res ==0:
        print("program ran succefully")
    else:
        print("ERROR")
        sys.exit(1)

def main(): #for the task to be runned
    if len(sys.argv)!= 5:
        print("The format is python run_project.py 'type' 'operation' 'num1' 'num2' ")
        sys.exit(1)
    type_ = sys.argv[1]
    operation = sys.argv[2]
    num1 = sys.argv[3]
    num2 = sys.argv[4]

    if type_ not in ["float","int"]:
        print("ERROR:The type should either be 'float' or 'integer'")
        sys.exit(1)
    build_project()#build project before running the java code
    run_java([type_, operation, num1, num2])

if __name__=="__main__":
    main()#calling the main