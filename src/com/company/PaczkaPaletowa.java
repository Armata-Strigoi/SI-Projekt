package com.company;

public class PaczkaPaletowa extends PaczkaDecorator{
    public PaczkaPaletowa(){
        super();
    }
    @Override
    public String decorate(){
        return super.decorate() + " Paletowa";
    }
}