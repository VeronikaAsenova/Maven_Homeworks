package com.mentormate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvidersDemo {

    @DataProvider(name = "dataProvider")
    public Object[][] dpMethod() {
        return new Object[][]{{2, 3, 5}, {5, 7, 9}};
    }

    @Test(dataProvider = "dataProvider")
    public void myTest ( int a, int b, int result){
        int sum = a + b;
        //  Assert.assertEquals(result, sum);
        Assert.assertTrue(result==9);
    }
}