package easy;

public class RansomNote {
    // for continuous data
    public boolean canConstruct(String ransomNote, String magazine) {

        if (ransomNote.length()>magazine.length()){
            return  false;
        }

        int min = 0;
        int max = magazine.length()-1;
        int i =0;
        while (min<max){
            if (magazine.charAt(min) != ransomNote.charAt(i)){
                min++;
                continue;
            }
            int temp = min;
            int count =0;
            for(int j = 0;j<ransomNote.length();j++){
                if (temp >= magazine.length()){
                    return false;
                }
                if (magazine.charAt(temp) == ransomNote.charAt(j)){
                    count++;
                    temp++;
                }
            }
            if (count == ransomNote.length()){
                return true;
            }
            else{
                min++;
                continue;
            }
        }
        return false;
    }

    public boolean canConstruct1(String ransomNote, String magazine) {

        if (ransomNote.length()>magazine.length()){
            return  false;
        }

        int[] ransomFreq = new int[26];
        char[] chars1 = ransomNote.toCharArray();
        for (char c : chars1) {
            ransomFreq[c - 'a']++;
        }

        int[] magazineFreq = new int[26];
        char[] chars2 = magazine.toCharArray();
        for (char c : chars2) {
            magazineFreq[c - 'a']++;
        }

        for (int i = 0;i<ransomNote.length();i++){
            if (ransomFreq[ransomNote.charAt(i) - 'a'] > magazineFreq[ransomNote.charAt(i) - 'a']){
                return false;
            }
        }

        return true;
    }



    public static void main(String a[]) {
        System.out.println(new RansomNote().canConstruct1("a",
                "b"
        ));
    }
}
