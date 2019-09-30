package easy;

public class ReverseString {
    public void reverseString(char[] s) {
        int max = s.length-1;
        int min =0;
        while (min<max){
            char temp = s[min];
            s[min] = s[max];
            s[max] = temp;

            min++;
            max--;
        }
        System.out.println(s);
    }

    public void reverseString1(char[] s) {
        int max = s.length-1;
        int min =0;
        while (min<max){
            s[min] = (char)((int) s[min] + (int) s[max]);
            s[max] = (char)((int) s[min] - (int) s[max]);
            s[min] = (char)((int) s[min] - (int) s[max]);

            min++;
            max--;
        }
        System.out.println(s);
    }


    public static void main(String a[]){

        new ReverseString().reverseString1("hello".toCharArray());
    }
}
