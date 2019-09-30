package easy;

import java.util.HashMap;

public class FirstUniqueCharacter {
    public int firstUniqChar(String s) {
       HashMap<Character,Integer> count = new HashMap<>();
       int n= s.length();
       for (int i=0;i<n;i++){
           char c = s.charAt(i);
           count.put(c,count.getOrDefault(c,0)+1);
       }

       for (int i=0;i<n;i++){
           if (count.get(s.charAt(i)) == 1){
               return i;
           }
       }
       return -1;
    }

    public int firstUniqChar1(String s) {
        if (s == null) {
            return -1;
        }
        int len = s.length();
        if (len == 0) {
            return -1;
        }
        if (len == 1) {
            return 0;
        }
        int[] freq = new int[26];
        char[] chars = s.toCharArray();
        for (char c : chars) {
            freq[c - 'a']++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (freq[chars[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String a[]){
        System.out.println(new FirstUniqueCharacter().firstUniqChar("hello"));
    }
}
