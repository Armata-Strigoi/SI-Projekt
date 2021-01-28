package com.company;


public class Memento {

    private Caretaker caretaker;

    Memento(){
        ZaladujZBazy();
        this.caretaker = new Caretaker();
    }


    private void ZaladujZBazy(){
        // Ladowanie z bazy do caretakera
    }

    public void ZapiszStanPaczek(PaczkiIterator iterator){
        caretaker.typy = PaczkaFactory.ZwrocListeTypow();
        while(iterator.hasNext()){
            Paczka tmp = iterator.getNext();
            SharedPaczka sptmp = tmp.wspoldzielone_dane;
            caretaker.paczki.add(tmp);
            // Zapisanie do bazy //
        }
    }

    public void PrzywrocPoprzedniStan(Magazyn magazyn){
        PaczkiIterator tmp = new PaczkiIterator(caretaker.paczki);
        PaczkaFactory.setTypyPaczek(caretaker.typy);
        magazyn.pIterator = tmp;
    }
}
