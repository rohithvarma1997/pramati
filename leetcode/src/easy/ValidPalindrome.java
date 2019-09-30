package easy;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        if (s.length() == 0){
            return true;
        }
        s = s.toLowerCase().replaceAll("[^0-9A-Za-z]","");

        int min = 0;
        int max = s.length()-1;

        while(min<max){
            if (s.charAt(max) != s.charAt(min)){
                return false;
            }
            min++;
            max--;
        }
        return true;
    }

    public boolean isPalindrome1(String s) {
        if (s.length() == 0){
            return true;
        }

        int min = 0;
        int max = s.length()-1;
        char[] ch = s.toCharArray();

        while(min<max){
            if (ch[min] >= 'A' && ch[min] <= 'Z'){
                ch[min] += 'a' - 'A';
            }
            if (ch[max] >= 'A' && ch[max] <= 'Z'){
                ch[max] += 'a' - 'A';
            }
            if (!(ch[min] >= 'a' && ch[min] <= 'z' || ch[min] >= '0' && ch[min] <= '9')){
                min++;
                continue;
            }
            if (!(ch[max] >= 'a' && ch[max] <= 'z' || ch[max] >= '0' && ch[max] <= '9')){
                max--;
                continue;
            }
            if (ch[min] != ch[max]){
                return false;
            }
            min++;
            max--;
        }
        return true;
    }

    public  static void  main(String a[]){
        System.out.println(new ValidPalindrome().isPalindrome("race a car"));
    }

}
