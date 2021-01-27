package com.company;

import java.util.ArrayList;

public class PaczkiIterator implements IteratorCore{
    private ArrayList<Paczka> paczki;// TO NA PACZKACORE !
    private int obecnaPozycja = 0;

    public PaczkiIterator(ArrayList<Paczka> paczki){
        this.paczki = new ArrayList<Paczka>();
        this.paczki = paczki;
    }

    public float ZwrocZyskZPaczek(int miesiac){
        ArrayList<Paczka> paczki_z_miesiaca = ZwrocPaczkiZMiesiaca(miesiac);
        float zysk_z_paczek = 0;
        for(int i=0;i<paczki_z_miesiaca.size();i++){
            zysk_z_paczek += paczki_z_miesiaca.get(i).koszt;
        }
        return zysk_z_paczek;
    }

    public ArrayList<Paczka> ZwrocPaczkiWDoreczeniu(){
        ArrayList<Paczka> paczuchy = new ArrayList<Paczka>();
        while(hasNext()){
            if(getNext().numer_statusu == 1){ // Potem na enume
                paczuchy.add(getNext());
            }
        }
        reset();
        return paczuchy;
    }

    public ArrayList<Paczka> ZwrocPaczkiZMiesiaca(int miesiac){
        if(miesiac-1 > new java.util.Date().getMonth()){
            System.err.println("Podano zly miesiac !");
            return null;
        }
        ArrayList<Paczka> paczuchy = new ArrayList<Paczka>();
        while(hasNext()){
            if(getNext().data_dostarczenia.getMonth() == (miesiac-1)){ // Potem na enume
                paczuchy.add(getNext());
            }
        }
        return paczuchy;
    }

    @Override
    public boolean hasIndex(int i){
        return i < paczki.size();
    }

    @Override
    public boolean hasNext(){
        return obecnaPozycja < paczki.size();
    }

    @Override
    public Paczka getNext(){
        if(!hasNext()){
            return null;
        }
        obecnaPozycja++;
        return paczki.get(obecnaPozycja-1);
    }

    @Override
    public void reset(){
        obecnaPozycja = 0;
    }

}
