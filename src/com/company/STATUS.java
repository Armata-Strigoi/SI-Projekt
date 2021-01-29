package com.company;

public enum STATUS {
    PIERWSZY("NADANO"),
    DRUGI("PRZEKAZANO DO DORECZENIA"),
    TRZECI("DOSTARCZONO");

    public final String nazwa;
    STATUS(String nazwa){
        this.nazwa = nazwa;
    }
}
