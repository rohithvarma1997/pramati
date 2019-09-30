package easy;

//https://leetcode.com/problems/length-of-last-word/

public class LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        if (s.equals(""))
            return 0;
        StringBuffer sb = new StringBuffer();
        int length = s.length() - 1;
        while (length >= 0 && s.charAt(length) == ' ') {
            length--;
        }
        for (int i = length; i >= 0; i--) {
            if (s.charAt(i) != ' ')
                sb.append(s.charAt(i));
            else
                break;
        }
        return sb.length();
    }

    public static void main(String args[]) {
        LengthOfLastWord lastWord = new LengthOfLastWord();
        System.out.println(lastWord.lengthOfLastWord("a"));
    }
}
