package com.company;

public class PaczkaZZawartosciaMagnetyczna extends PaczkaDecorator{
    public PaczkaZZawartosciaMagnetyczna(){
        super();
    }
    @Override
    public String decorate(){
        return super.decorate() + " Z zawartoscia magnetyczna";
    }
}
