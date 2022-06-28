package com.mentormate.JavaRestAssured;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

class ObjectToJSON {

    public static void main(String[] args) {

        //Creating object of Product
        Product product = new Product();

        //inserting the data into the object
        product = getObjectData(product);

        //Creating Object of ObjectMapper define in Jackson API
        ObjectMapper obj = new ObjectMapper();

        try {
            //converting the Java object into a JSON string
            String jsonStr = obj.writeValueAsString(product);

            //displaying Java object into a JSON String
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Product getObjectData(Product product) {

        //insert the data
        product.setId(101);
        product.setName("Spark 131");
        product.setPrice(10000);
        //returning the product object
        return product;
    }
}


public class Product {

    private int id;
    private String name;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
