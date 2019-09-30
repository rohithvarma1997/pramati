package easy;

// https://leetcode.com/problems/count-and-say/

public class CountAndSay {
    public String countAndSay(int n) {
        if (n == 1) return "1";

        StringBuilder stringBuilder = new StringBuilder();
        String s = countAndSay(n-1);
        int count  = 0;

        for (int i = 0; i < s.length(); i++){
            count++;

            if (i+1 >= s.length() || s.charAt(i) != s.charAt(i+1)){
                stringBuilder.append(count);
                stringBuilder.append(s.charAt(i));
                count = 0;
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String args[]){
        CountAndSay countAndSay = new CountAndSay();
        System.out.println(countAndSay.countAndSay(5));
    }
}
