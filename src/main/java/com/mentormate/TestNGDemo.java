package com.mentormate;

import org.testng.annotations.*;

public class TestNGDemo {

    @BeforeTest
    public void setUp() {
        System.out.println("This is before Test");
    }

    @Test
    public void testDemo() {
        System.out.println("This is my first TestNG test");
    }

    @Test(groups = "group1", dependsOnGroups = "group2")
    public void testGroup() {
        System.out.println("This is my group test");
    }

    @Test(groups = "group2")
    public void testGroup2() {
        System.out.println("This is my 2nd group test");
    }


    @AfterTest
    public void cleanUp() {
        System.out.println("This is after Test");
    }

}
