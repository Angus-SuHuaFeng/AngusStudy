package Test01;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUITest {
    public static void main(String[] args) {
        new GUITest();
    }

    public GUITest(){
        JFrame frame = new JFrame("学生信息录入程序");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(400, 400);
        frame.setLocation(500, 300);
        JLabel jLabel1 = new JLabel("学号:",JLabel.CENTER);
        JLabel jLabel2 = new JLabel("姓名:",JLabel.CENTER);
        JLabel jLabel3 = new JLabel("年龄:",JLabel.CENTER);
        JLabel jLabel4 = new JLabel("班级:",JLabel.CENTER);
        JLabel jLabel5 = new JLabel("成绩:",JLabel.CENTER);
        final JTextField account1 = new JTextField(10);
        final JTextField account2 = new JTextField(10);
        final JTextField account3 = new JTextField(10);
        final JTextField account4 = new JTextField(10);
        final JTextField account5 = new JTextField(10);
        JPanel jp1 = new JPanel(new GridLayout(5,2));
        jp1.add(jLabel1);
        jp1.add(account1);
        jp1.add(jLabel2);
        jp1.add(account2);
        jp1.add(jLabel3);
        jp1.add(account3);
        jp1.add(jLabel4);
        jp1.add(account4);
        jp1.add(jLabel5);
        jp1.add(account5);
        frame.add(jp1,BorderLayout.CENTER);
        JPanel jp2 = new JPanel();
        frame.add(jp2,BorderLayout.SOUTH);
        JButton b1 = new JButton("注册");
        b1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String num = account1.getText();
                String name = account2.getText();
                String age = account3.getText();
                String clas = account4.getText();
                String score = account5.getText();
                student student = new student(num, name, age, clas, score);
                System.out.println(student.toString());
            }
        });

        JButton b2 = new JButton("重置");
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                account1.setText("");
                account2.setText("");
                account3.setText("");
                account4.setText("");
                account5.setText("");

            }
        });
        jp2.add(b1);
        jp2.add(b2);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class student{
    String num;
    String name;
    String age;
    String clas;
    String score;

    public student(String num, String name, String age, String clas, String score) {
        this.num = num;
        this.name = name;
        this.age = age;
        this.clas = clas;
        this.score = score;
    }

    public student() {

    }

    @Override
    public String toString() {
        return "student{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", clas='" + clas + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}

