package com.company;

public class PojazdSredni extends Pojazd{

    PojazdSredni(int id, String typ, String marka, String model, float ladownosc){
        super(id,typ,marka,model,ladownosc);
        this.nazwa = POJAZDY.SREDNI.nazwa;
    }
}
