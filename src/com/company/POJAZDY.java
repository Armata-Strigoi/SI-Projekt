package com.company;



public enum POJAZDY {
 
    MALY(10100, "SKUTER"),
    SREDNI(50000,"SAMOCHOD"),
    DUZY(150000,"VAN");

    public final float ladownosc;
    public final String nazwa;
    POJAZDY(float i,String nazwa) {
        this.ladownosc = i;
        this.nazwa = nazwa;
    }
}
