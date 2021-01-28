package com.company;

public class PojazdFactory {
    public static Pojazd stworzPojazd(int id, String typ,String marka, String model, float ladownosc){
        if(ladownosc < POJAZDY.MALY.ladownosc) return new PojazdMaly(id,typ,marka,model,ladownosc);
        else if(ladownosc < POJAZDY.SREDNI.ladownosc) return new PojazdSredni(id,typ,marka,model,ladownosc);
        else if(ladownosc < POJAZDY.DUZY.ladownosc) return new PojazdDuzy(id,typ,marka,model,ladownosc);
        else {
            System.err.println("Nie mozna stworzyc samochodu o tak duzej ladownosci!");
            return null;
        }
    }
}
