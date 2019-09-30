package easy;

// https://leetcode.com/problems/implement-strstr/

public class ImplementStrStr {
        public int strStr(String haystack, String needle) {
        if (needle.length() == 0){
            return 0;
        }
        if (needle.length() > haystack.length()){
            return -1;
        }
        char c1[] = haystack.toCharArray();
        char c2[] = needle.toCharArray();
        int count = 0;

        for (int i = 0 ; i < haystack.length();i++){
            int k =i;
            if (c1[i] != c2[0]){
                continue;
            }
            else if(c1[i] == c2[0]){
                for (int j=0;j<needle.length();j++){
                    if (k>=haystack.length()){
                        return -1;
                    }
                    if (c1[k] == c2[j]){
                        k++;
                        count++;
                    }
                    else{
                        count = 0;
                        break;
                    }
                }
                if (count == needle.length()){
                    return i;
                }

            }
        }
        return -1;
    }


    public static void main(String args[]){
        ImplementStrStr strStr = new ImplementStrStr();
        System.out.println(strStr.strStr("hello","ll"));
    }
}

