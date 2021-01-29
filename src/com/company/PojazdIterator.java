package com.company;

import java.util.ArrayList;

public class PojazdIterator implements IteratorCore {
    private ArrayList<Pojazd> dostepne_samochody;
    private int obecnaPozycja = 0;

    PojazdIterator(ArrayList<Pojazd> samochody){
        this.dostepne_samochody = new ArrayList<Pojazd>();
        this.dostepne_samochody = samochody;
    }

    @Override
    public boolean hasIndex(int i) {
        return i < size();
    }

    @Override
    public boolean hasNext() {
        return obecnaPozycja < size();
    }

    @Override
    public Object getNext() {
        if(!hasNext()){
            return null;
        }
        obecnaPozycja++;
        return dostepne_samochody.get(obecnaPozycja-1);
    }

    @Override
    public void reset() {
        obecnaPozycja = 0;
    }

    @Override
    public Pojazd getIndex(int index) {
        if(index < size()) return dostepne_samochody.get(index);
        return null;
    }

    public void add(Pojazd co) {
        this.dostepne_samochody.add(co);
    }

    @Override
    public int size() {
        return this.dostepne_samochody.size();
    }

    public void remove(int index){
        this.dostepne_samochody.remove(index);
    }
}
