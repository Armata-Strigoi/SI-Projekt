package com.company;

import java.util.ArrayList;

import com.company.STATUS;

import static com.company.STATUS.PIERWSZY;

public class PaczkiIterator implements IteratorCore{
    public ArrayList<Paczka> paczki;
    public int obecnaPozycja = 0;

    public PaczkiIterator(ArrayList<Paczka> paczki){
        this.paczki = paczki;
    }


    public float ZwrocMiesiecznyZysk(int miesiac){
        ArrayList<Paczka> paczki_z_miesiaca = ZwrocPaczkiZMiesiaca(miesiac);
        float zysk_z_paczek = 0;
        for(int i=0;i<paczki_z_miesiaca.size();i++){
            zysk_z_paczek += paczki_z_miesiaca.get(i).wspoldzielone_dane.koszt;
        }
        return zysk_z_paczek;
    }

    public float ZwrocRocznyZysk(int rok){
        ArrayList<Paczka> paczki_z_roku = ZwrocPaczkiZRok(rok);
        float zysk_z_paczek = 0;
        for(int i=0;i<paczki_z_roku.size();i++){
            zysk_z_paczek += paczki_z_roku.get(i).wspoldzielone_dane.koszt;
        }
        return zysk_z_paczek;
    }

    public ArrayList<Paczka> ZwrocPaczkiWDoreczeniu(){
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        while(hasNext()){
            if(getNext().numer_statusu == 1){
                paczki.add(getNext());
            }
        }
        reset();
        return paczki;
    }

    public ArrayList<Paczka> ZwrocPaczkiZMiesiaca(int miesiac){
        if(miesiac-1 > new java.util.Date().getMonth()){
            System.err.println("Podano zly miesiac !");
            return null;
        }
        ArrayList<Paczka> paczuchy = new ArrayList<Paczka>();
        while(hasNext()){
            Paczka tmp = getNext();
            if(tmp.data_dostarczenia != null && tmp.data_dostarczenia.getMonth() == (miesiac-1)){
                paczuchy.add(tmp);
            }
        }
        this.reset();
        return paczuchy;
    }

    public ArrayList<Paczka> ZwrocPaczkiZRok(int rok){
        if(rok > new java.util.Date().getYear() + 1900){
            System.err.println("Podano zly rok !");
            return null;
        }
        ArrayList<Paczka> paczuchy = new ArrayList<Paczka>();
        while(hasNext()){
            Paczka tmp = getNext();
            if(tmp.data_dostarczenia != null && tmp.data_dostarczenia.getYear() + 1900 == rok){
                paczuchy.add(tmp);
            }
        }
        this.reset();
        return paczuchy;
    }
    @Override
    public Paczka getIndex(int index){
        if(index < size()) return paczki.get(index);
        return null;
    }

    @Override
    public boolean hasIndex(int i){
        return i < size();
    }

    @Override
    public boolean hasNext(){
        return obecnaPozycja < size();
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


    public void add(Paczka co){
        this.paczki.add(co);
    }

    @Override
    public int size(){
        return this.paczki.size();
    }
}
