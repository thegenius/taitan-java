package com.example;

/**
 * An example class written in .flux format that will be converted to .java
 */
public class ExampleClass {
    
    private String message;
    
    public ExampleClass(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void printMessage() {
        System.out.println("Message: " + message);
    }
}