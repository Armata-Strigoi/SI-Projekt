package com.company;

public class PaczkaDelikatna extends PaczkaDecorator{
    public PaczkaDelikatna(){
        super();
    }
    @Override
    public String decorate(){
        return super.decorate() + " Delikatna";
    }
}
