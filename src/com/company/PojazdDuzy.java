package com.company;

public class PojazdDuzy extends Pojazd{
    public PojazdDuzy(int id, String typ, String marka, String model, float ladownosc){
        super(id,typ,marka,model,ladownosc);
        this.nazwa = POJAZDY.DUZY.nazwa;
    }
}
