package recursion;

public class PrintBinary {
    public void printBinary(int n){
        printBinaryHelper(n,"");
    }

    public void printBinaryHelper(int n,String s){
        if (n == 0){
            System.out.println(s);
        }
        else {
            printBinaryHelper(n - 1, s + "0");
            printBinaryHelper(n - 1, s + "1");
        }
    }

    public static void main(String a[]){
        new PrintBinary().printBinary(5);
    }
}
