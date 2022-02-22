package Test2;

import Test01.ObjectShallowSize;

public class Test{
    public static void main(String[] args){
        Student student = new Student();
        student.SetRecord(001,"小红",90);
        Student student1 = new Student(002,"小明",100);
        System.out.println(ObjectShallowSize.sizeOf(student));
    }
}


/**
 * 1、类（基础）
 *
 * 定义一个类Student，属性为学号、姓名和成绩；方法为增加记录SetRecord和得到记录GetRecord。 SetRecord给出学号、姓名和成绩的赋值，GetRecord通过学号得到学号得到考生的成绩。
 *
 *
 *
 * 2、类（构造方法）
 *
 * 给出上题（1）中设计类的构造函数，要求初始化一条记录。
 *
 */
//定义一个类Student，属性为学号、姓名和成绩；方法为增加记录SetRecord和得到记录GetRecord。
//SetRecord给出学号、姓名和成绩的赋值，GetRecord通过学号得到学号得到考生的成绩。
class Student{
        int num;
        String name;
        int score;
        public Student(){

        }
        public Student(int num,String name,int score){
            this.num = num;
            this.name = name;
            this.score = score;
        }
        public void SetRecord(int num,String name,int score){
            this.num = num;
            this.name = name;
            this.score = score;
        }
        public int GetRecord(){
            return score;
        }

        @Override
        public String toString() { return "Student{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
