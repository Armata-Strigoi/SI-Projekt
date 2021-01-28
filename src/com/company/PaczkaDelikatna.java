package com.company;

public class PaczkaDelikatna extends PaczkaDecorator{
    public PaczkaDelikatna(PaczkaCore paczka){
        super(paczka);
    }
    @Override
    public String decorate(){
        if(core != null) return super.decorate() + " Delikatna";
        return " Delikatna";
    }
}
