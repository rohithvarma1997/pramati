package recursion;

public class PrintDecimal {
    public void printDecimal(int n){
        printDecimalHelper(n,"");
    }

    public void printDecimalHelper(int n,String s){
        if (n == 0){
            System.out.println(s);
        }
        else {
            for (int i =0;i<=9;i++)
            printDecimalHelper(n - 1, s + i);
        }
    }

    public static void main(String a[]){
        new PrintDecimal().printDecimal(2);
    }
}
