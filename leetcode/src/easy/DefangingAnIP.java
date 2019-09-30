package easy;

// https://leetcode.com/problems/defanging-an-ip-address/

public class DefangingAnIP {
    public String defangIPaddr(String address) {
        StringBuilder sb = new StringBuilder(address.length()+2*3);
        for(int i = 0;i < address.length();i++){
            char c = address.charAt(i);
            if(c == '.'){
                sb.append("[.]");
            }
            else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String args[]){
        DefangingAnIP ip = new DefangingAnIP();
        System.out.println(ip.defangIPaddr("1.1.1.1"));
    }
}
