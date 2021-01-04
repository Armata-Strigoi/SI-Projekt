package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Magazyn { // Obsluga wysylania paczek, obliczania kubatury i generowania listy paczek czekajacych na wysylke
    private ArrayList<Paczka> paczki;
    private ArrayList<Samochod> dostepne_samochody;

    Magazyn(){
        this.paczki = new ArrayList<Paczka>();
        this.dostepne_samochody = PobierzSamochody();
        //Pobieranie paczek z bazy danych i zapelnianie listy;
    }

    private ArrayList<Samochod> PobierzSamochody(){
        ArrayList<Samochod> tmp = new ArrayList<Samochod>();
        //TUtaj powinno sie pobierac z basy a nie z buta wpisywac

        tmp.add(new Samochod(1,"Dostawczy","Mercedess","Sprinter",1000));
        tmp.add(new Samochod(2,"Kurierski","Volkswagen","Polo",200));
        tmp.add(new Samochod(3,"Dostawczy","Ford","Transit",1200));
        return tmp;
    }

    private float ZnajdzNajbardziejLadownySamochod(){
        if(this.dostepne_samochody.size()>0) {
            int max = 0;
            for (int i = 0; i < this.dostepne_samochody.size(); i++) {
                if (this.dostepne_samochody.get(i).ladownosc > this.dostepne_samochody.get(max).ladownosc)max = i;
            }
            return this.dostepne_samochody.get(max).ladownosc;
        }
        return 0;
    }


    public void NadajPaczke(int id_pracownik, float wysokosc, float szerokosc, float glebokosc, float waga){
        int size = this.paczki.size();
        this.paczki.add(new Paczka(size,id_pracownik,wysokosc,szerokosc,glebokosc,waga));
        System.out.println("Pomyslnie nadano paczke o id: " + size);
    };

    public ArrayList<Paczka> PrzydzielPaczki(){
        float kubaturaTMP = 0;
        float kubaturaMAX = ZnajdzNajbardziejLadownySamochod();
        if(kubaturaMAX == 0) return null;
        ArrayList<Paczka> tmp = new ArrayList<Paczka>();

        for(int i=0;i<paczki.size();i++){
            if(kubaturaTMP + this.paczki.get(i).kubatura <= kubaturaMAX){
                this.paczki.get(i).ZaktualizujStatus();
                tmp.add(this.paczki.get(i));
                kubaturaTMP += this.paczki.get(i).kubatura;
            }
        }
        System.out.println("Przydzielono paczki: " + tmp.size());
        return tmp;
    }

    public Samochod PrzydzielSamochod(ArrayList<Paczka> ladunek){
        float kubatura = 0;
        for(int i=0;i<ladunek.size();i++){
            kubatura += ladunek.get(i).kubatura;
        }
        return PrzydzielOdpowiedniSamochod(kubatura);
    }

    private Samochod PrzydzielOdpowiedniSamochod(float kubatura){
        if(this.dostepne_samochody.size() <= 0)return null;
        int min = 0;
        for(int i=0;i<dostepne_samochody.size();i++){
            if(this.dostepne_samochody.get(i).ladownosc > kubatura && this.dostepne_samochody.get(i).ladownosc < this.dostepne_samochody.get(min).ladownosc)min = i;
        }
        Samochod tmp = this.dostepne_samochody.get(min);
        this.dostepne_samochody.remove(min); // Oddany w rece kuriera wiec niedostepny
        System.out.println("Przydzielono pojazd nr: " + tmp.id);
        return tmp;
    }

    public void DostarczPaczki(ArrayList<Paczka> dostarczony_ladunek){
        //Zmiana statusu odpowiednich paczek
    }

}
