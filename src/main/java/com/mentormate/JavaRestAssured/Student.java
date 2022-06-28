package com.mentormate.JavaRestAssured;

import java.io.*;

public class Student implements Serializable {

    @Serial
    private static final long SerialVersionUID = 1234L;
    //static field will not be serialized
    static int regNo;
    String name;

    //Transient field wii be reset to its default type value after deserialization
    double gpa;
    transient boolean isHosteler;

    public Student(String name, double gpa, int regNo, boolean isHosteller) {
        this.name = name;
        this.gpa = gpa;
        this.regNo = regNo;
        this.isHosteler = isHosteller;
    }

    public void print() {
        System.out.println("Name: " + this.name);
        System.out.println("GPA: " + this.gpa);
        System.out.println("regNo: " + this.regNo);
        System.out.println("isHosteler: " + this.isHosteler);
        System.out.println("Serial version UID: " + SerialVersionUID);
    }
}


class Demo {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student = new Student("Veronika", 6, 271, true);
        System.out.println("Before Serialization");
        student.print();


        //Serialization
        FileOutputStream fileOutputStream = new FileOutputStream("demo.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(student);
        objectOutputStream.close();
        fileOutputStream.close();

        Student.regNo = 102; //changing the static field

        //Deserialization
        FileInputStream fileInputStream = new FileInputStream("demo.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Student deserializedStudent = (Student) objectInputStream.readObject();

        System.out.println("\nAfter Deserialization");

        deserializedStudent.print();
        objectInputStream.close();
        fileInputStream.close();


    }
}
