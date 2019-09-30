package easy;

public class AddBinary {
    public String addBinary(String a, String b) {
        StringBuilder sb =new StringBuilder();
        if(a == null || b == null){
            return a == null ? b : a;
        }
        int carry = 0;
        for(int i = a.length() -1,j = b.length() -1; i>=0 || j>=0; i--,j--){
            int sum = 0;
            sum += i>=0? a.charAt(i) - '0':0;
            sum += j>=0? b.charAt(j) - '0':0;
            sum += carry;

            carry = sum/2;
            sum %=2;
            sb.append(sum);
        }
        if(carry > 0){
            sb.append(carry);
        }
        return sb.reverse().toString();
    }

    public static void main(String a[]){
        AddBinary addBinary = new AddBinary();
        System.out.println(addBinary.addBinary("11","11"));
    }
}
