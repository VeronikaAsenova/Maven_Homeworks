package com.mentormate;

import org.testng.annotations.Test;

import java.io.IOException;

public class Exceptions {

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = "Pass Message test")
    public void exceptionTestTwo() throws Exception{
        throw new IOException("Pass message test!");
    }

    @Test(expectedExceptions = {IOException.class}, expectedExceptionsMessageRegExp = "Pass Mssage test")
    public void exceptionTestOne() throws Exception{
        throw new IOException("Fail Message test");
    }
}