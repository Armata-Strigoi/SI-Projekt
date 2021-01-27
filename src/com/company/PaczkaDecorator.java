package com.company;

public abstract class PaczkaDecorator implements PaczkaCore {
    private PaczkaCore core;

    public PaczkaDecorator() {
    }

    @Override
    public String decorate(){
        return core.decorate();
    }
}
