package easy;

public class CountSegment {
    //TODO:wrong,check the question
    public int countSegments(String s) {
        if (s.length() == 0){
            return 0;
        }
        int min = 0;
        int max = s.length()-1;

        while (min<max){
            if (s.charAt(min) == ' '){
                min++;
                continue;
            }
            if (s.charAt(max) == ' '){
                max--;
                continue;
            }
            break;
        }
        int count = 0;
        if (min == max && min > 0){
            return 0;
        }
        for (int i =min;i<=max;i++){
            if (s.charAt(i) == ' '){
                count++;
            }
        }
        return count+1;
    }

    public static void main(String a[]){
        System.out.println(new CountSegment().countSegments(", , , ,        a, eaefa"));
    }
}
