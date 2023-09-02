package stack;

import java.util.*;

public class PostfixNotationEx {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();
        String s = "3 2 4 * + 9 3 / -";
        StringTokenizer st = new StringTokenizer(s);
        while(st.hasMoreTokens()){
            String n = st.nextToken();
            if(n.length() == 1){
                char operator = n.charAt(0);
                if(operator<=47 && operator >= 42){
                    Integer B = stack.pop();
                    Integer A = stack.pop();
                    if(operator == 43) stack.push(A + B);
                    if(operator == 45) stack.push(A - B);
                    if(operator == 42) stack.push(A * B);
                    if(operator == 47) stack.push(A / B);
                } else stack.push(Integer.parseInt(n));

            }
        }
        System.out.println(stack.pop());
    }
}
