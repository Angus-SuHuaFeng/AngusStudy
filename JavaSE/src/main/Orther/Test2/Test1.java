package Test2;

import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        //四则运算
        int num1,num2,result = 0;
        String Fuhao = null;
        Scanner sc = new Scanner(System.in);
        num1 = sc.nextInt();
        Fuhao = sc.next();
        num2 = sc.nextInt();
        if (Fuhao.equals("+")){
            result = num1+num2;
        }else if (Fuhao.equals("-")){
            result = num1-num2;
        }else if (Fuhao.equals("*")){
            result = num1*num2;
        }else if (Fuhao.equals("/")){
            result = num1/num2;
        }
        System.out.println(result);
}
}
