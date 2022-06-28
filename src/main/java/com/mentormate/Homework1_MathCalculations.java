package com.mentormate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Homework1_MathCalculations {


    @DataProvider(name = "dataProviderSum")
    public Object[][] dpMethod1() {
        return new Object[][]{{2, 3, 5}, {5, 7, 12}};
    }

    @Test(dataProvider = "dataProviderSum", groups = "Sum")
    public void mathSum(int a, int b, int result) {
        int sum = a + b;
        Assert.assertEquals(result, sum);

    }

    @DataProvider(name = "dataProviderSubstraction")
    public Object[][] dpMethod2() {
        return new Object[][]{{8, 3, 5}, {19, 7, 12}};
    }

    @Test(dataProvider = "dataProviderSubstraction", groups = "Substraction")
    public void mathSubstraction(int a, int b, int result) {
        int substraction = a - b;
        Assert.assertEquals(result, substraction);

    }

    @DataProvider(name = "dataProviderMultiplication")
    public Object[][] dpMethod3() {
        return new Object[][]{{2, 3, 6}, {5, 7, 35}};
    }

    @Test(dataProvider = "dataProviderMultiplication", groups = "Multiplication")
    public void mathMultiplication(int a, int b, int result) {
        int multiplication = a * b;
        Assert.assertEquals(result, multiplication);
    }

    @DataProvider(name = "dataProviderDivision")
    public Object[][] dpMethod4() {
        return new Object[][]{{15, 3, 5}, {28, 7, 4}};
    }

    @Test(dataProvider = "dataProviderDivision", groups = "Division")
    public void mathDivision(int a, int b, int result) {
        int division = a / b;
        Assert.assertEquals(result, division);
    }

    @DataProvider(name = "dataProviderModule")
    public Object[][] dpMethod5() {
        return new Object[][]{{10, 2, 0}, {36, 6, 0}};
    }

    @Test(dataProvider = "dataProviderModule", groups = "Division")
    public void mathDModule(int a, int b, int result) {
        int division = a % b;
        Assert.assertEquals(result, division);
    }
}