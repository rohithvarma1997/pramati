package Hard;

import java.util.Arrays;
import java.util.HashMap;

public class LargestRange {

    public static int[] largestRange(int[] array) {
        int n = array.length;
        int count= 0;

        int[] res = null;

        HashMap<Integer,Boolean> set = new HashMap();
        for (int i = 0;i < n; i++){
            set.put(array[i],true);
        }

        for (int i = 0;i < n; i++){
            int currentCount = 0;
            if (!set.get(array[i]))
                continue;
            set.put(array[i],false);
            currentCount = 1;
            int left = array[i]-1;
            int right = array[i]+1;
            while (set.get(left)!= null){
                currentCount++;
                set.put(left,false);
                left--;
            }
            while (set.get(right) != null){
                currentCount++;
                set.put(right,false);
                right++;
            }
            if (currentCount > count){
                count = currentCount;
                res = new int[]{left + 1, right - 1};
            }
        }
        return res;
    }

    public static void main(String a[]){
        int input[] = {9,5,4,8,3,6,2};
        System.out.println(Arrays.toString(LargestRange.largestRange(input)));
    }
}
