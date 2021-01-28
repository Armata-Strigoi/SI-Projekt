package com.company;

public class PaczkaZZawartosciaMagnetyczna extends PaczkaDecorator{
    public PaczkaZZawartosciaMagnetyczna(PaczkaCore paczka){
        super(paczka);
    }
    @Override
    public String decorate(){
        if(core != null) return super.decorate() + " Z zawartoscia megnetyczna";
        return " Z zawartoscia megnetyczna";
    }
}