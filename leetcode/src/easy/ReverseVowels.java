package easy;

public class ReverseVowels {
    public String reverseVowels(String s) {
        char c[] = s.toCharArray();
        int low = 0;
        int high = s.length()-1;

        while(low<high){
            if (c[low] != 'a' && c[low] != 'e' && c[low] != 'i' && c[low] != 'o' && c[low] != 'u' && c[low] != 'A' && c[low] != 'E' && c[low] != 'I' && c[low] != 'O' && c[low] != 'U' ){
                low++;
                continue;
            }
            if (c[high] != 'a' && c[high] != 'e' && c[high] != 'i' && c[high] != 'o' && c[high] != 'u' && c[high] != 'A' && c[high] != 'E' && c[high] != 'I' && c[high] != 'O' && c[high] != 'U' ){
                high--;
                continue;
            }

            char t = c[low];
            c[low] = c[high];
            c[high] = t;
            low++;
            high--;
        }
        return new String(c);
    }

    public static void main(String a[]){
        System.out.println(new ReverseVowels().reverseVowels("Yo! Bottoms up, U.S. Motto, boy!"));
    }
}
