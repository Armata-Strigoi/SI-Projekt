package test;

import com.company.SharedPaczka;
import com.company.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MagazynTest {

    Connect contest;
    Connect connect;
    Magazyn magazyn;
    Magazyn magtest;

    @BeforeEach
    void init(){
        contest = new Connect("root","root");
        connect = new Connect("root","root");
        PaczkaFactory.PobierzZBazy(connect.connection);
        magazyn = new Magazyn(connect.connection);
        magtest = new Magazyn(contest.connection);

    }

    @Test
    void pobierzSamochody() {
        assertEquals(magtest.dostepne_samochody.size(),magazyn.dostepne_samochody.size(),"Rozmiar obu list powinien byc taki sam");
        for(int i=0;i<magtest.dostepne_samochody.size();i++)
            assertEquals(magtest.dostepne_samochody.getIndex(i).id,magazyn.dostepne_samochody.getIndex(i).id,"Obieky powinny byc takie same");
    }

    @Test
    void pobierzPaczki() {
        assertEquals(magtest.pIterator.paczki.size(),magazyn.pIterator.paczki.size(),"Rozmiar obu list powinien byc taki sam");
        for(int i=0;i<magtest.pIterator.paczki.size();i++)
            assertEquals(magtest.pIterator.paczki.get(i).idPaczki,magazyn.pIterator.paczki.get(i).idPaczki,"Obieky powinny byc takie same");

    }

    @Test
    void znajdzNajbardziejLadownySamochod() {
        assertEquals(magtest.ZnajdzNajbardziejLadownySamochod(),magazyn.ZnajdzNajbardziejLadownySamochod(),"Oba obiekty powinny zwroic te same wartosci");
    }


    @Test
    void przeszukajPaczki() {
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        magazyn.pIterator.paczki.add(paczka);
        assertEquals(magazyn.pIterator.paczki.size()-1,magazyn.PrzeszukajPaczki(paczka),"Powinna zostac zwrocona najnowsza dodana paczka");
    }

    @Test
    void nadajPaczke(){
        int ile = magazyn.pIterator.paczki.size();
        assertEquals(ile,magazyn.pIterator.paczki.size());
        magazyn.NadajPaczke("RozmiarA",20,"dobra",13,13,"444113133","nijaka",99,92,"541232123",0,0,1);
        assertNotEquals(ile,magazyn.pIterator.paczki.size());
    }

}