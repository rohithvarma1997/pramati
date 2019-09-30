package recursion;


public class PrintDice {
    public void printDice(int n) {
        printDiceHelper(n, "");
    }

    public void printDiceHelper(int n, String s) {
        if (n == 0) {
            System.out.println(s);
        } else {
            for (int i = 1; i <= 6; i++)
                printDiceHelper(n - 1, s + i);
        }
    }

    public static void main(String a[]) {
        new PrintDice().printDice(3);
    }
}
