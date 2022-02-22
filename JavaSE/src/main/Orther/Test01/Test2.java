package Test01;

import java.util.Random;

public class Test2 {
        public static void main(String[] args) {
            System.out.println("***********************************");
            System.out.println("***         算术学习系统          ***");
            System.out.println("***      作者: 内蒙古工业大学      ***");
            System.out.println("***          2019.07.08         ***");

            int N,b,c,str,num2=0,num1 = 0,result = 0,resultin;
            Random random = new Random();
            N = random.nextInt(9)+1;
            //System.out.println(a);
            num1 = random.nextInt(10);
            for (int i = 0; i < N; i++) {
                String Fuhao = null;
                str = random.nextInt(4);
                if (str==3){
                    num2 = random.nextInt(9)+1;
                }else {
                    num2 = random.nextInt(10);
                }
                num1 = random.nextInt(10);
                switch (str){
                    case 0: Fuhao="+";
                            result = num1+num2;
                    break;
                    case 1: Fuhao="-";
                            result = num1-num2;
                    break;
                    case 2: Fuhao="*";
                            result = num1*num2;
                    break;
                    case 3: Fuhao="➗";
                            result = num1/num2;
                    break;

                    default:break;
                }
                System.out.println(num1 + Fuhao + num2 + "=" + result);
            }
        }
}


