package com.example;

/**
 * A sample class in .flux format that will be converted to .java
 */
public class SampleClass {
    
    private String name;
    
    public SampleClass(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "SampleClass{name='" + name + "'}";
    }
}