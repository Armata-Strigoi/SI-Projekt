package com.company;

public abstract class PaczkaDecorator implements PaczkaCore {
    public PaczkaCore core;

    public PaczkaDecorator(PaczkaCore paczka) {
        this.core = paczka;
    }

    @Override
    public String decorate(){
        return core.decorate();
    }
}
