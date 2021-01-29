package com.company;

public abstract class Pojazd extends Object{
    public int id;
    public String typ,marka,model,nazwa;
    public float ladownosc;

    Pojazd(int id, String typ,String marka, String model, float ladownosc){
        this.id = id;
        this.typ = typ;
        this.marka = marka;
        this.model = model;
        this.ladownosc = ladownosc;
    }

    public String toString(){
        return this.nazwa + " o id: " + this.id + " i ladownosci: " + this.ladownosc;
    }
}
