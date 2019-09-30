package easy;

// https://leetcode.com/problems/valid-parentheses/

class Solution {
    int top = -1;
    char a[] =new char[10];

    public boolean isValid(String s) {
        for (int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if (c == '{' || c == '[' || c == '('){
                a[++top] = c;
            }
            else if (c == '}' || c == ']' || c == ')'){
                if ( top < 0 ){
                    return false;
                }
                else{
                    if (a[top] == '{' && c == '}'){
                        top --;
                    }else if (a[top] == '[' && c == ']'){
                        top --;
                    }else if (a[top] == '(' && c == ')'){
                        top --;
                    }else{
                        return false;
                    }
                }
            }
        }
        if (top != -1){
            return false;
        }
        return true;
    }
}

public class ValidParentheses {
    public static void main(String args[]) {
        Solution s = new Solution();
        System.out.println(s.isValid("}"));
    }
}
