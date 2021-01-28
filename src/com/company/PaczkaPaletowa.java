package com.company;

public class PaczkaPaletowa extends PaczkaDecorator{
    public PaczkaPaletowa(PaczkaCore paczka){
        super(paczka);
    }
    @Override
    public String decorate(){
        if(core != null) return super.decorate() + " Paletowa";
        return " Paletowa";
    }
}