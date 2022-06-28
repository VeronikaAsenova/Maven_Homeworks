package com.mentormate.JavaRestAssured;

import com.google.gson.Gson;

public class MobilePhone {

    private String brand;
    private String name;
    private int ram;
    private int rom;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }
}

class ConvertJSON {

    public static MobilePhone getMobile(MobilePhone mobile) {
        mobile.setBrand("Apple");
        mobile.setName("13 Pro");
        mobile.setRam(2);
        mobile.setRom(4);
        return mobile;
    }

    public static void main(String[] args) {

        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone = getMobile(mobilePhone);
        System.out.println("The JSON representation of Object mobilePhone is: ");
        System.out.println(new Gson().toJson(mobilePhone));
    }
}