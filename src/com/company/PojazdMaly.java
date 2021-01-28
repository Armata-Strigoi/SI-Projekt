package com.company;

public class PojazdMaly extends Pojazd {
    PojazdMaly(int id, String typ, String marka, String model, float ladownosc){
        super(id,typ,marka,model,ladownosc);
        this.nazwa = POJAZDY.MALY.nazwa;
    }
}

