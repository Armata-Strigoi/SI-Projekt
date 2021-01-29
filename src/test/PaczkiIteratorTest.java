package test;

import com.company.Paczka;
import com.company.PaczkaCore;
import com.company.PaczkiIterator;
import com.company.SharedPaczka;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaczkiIteratorTest {


    @Test
    void zwrocPaczkiWDoreczeniu() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka2 = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka3 = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka2);
        paczki.add(paczka3);
        assertEquals(2,iterator.ZwrocPaczkiWDoreczeniu().size(),"Ilosc paczek powinna sie zgadzac");
        assertNotEquals(1,iterator.ZwrocPaczkiWDoreczeniu().size(),"Ilosc paczek nie powinna sie zgadzac");
    }

    @Test
    void getIndex() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka);
        assertNotNull(iterator.getIndex(1));
        assertNull(iterator.getIndex(2));
    }

    @Test
    void hasIndex() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka2 = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka2);
        assertTrue(iterator.hasIndex(0));
        assertTrue(iterator.hasIndex(1));
        assertFalse(iterator.hasIndex(2));
    }

    @Test
    void hasNext() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka2 = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka2);
        assertTrue(iterator.hasNext());
        assertEquals(paczka,iterator.getNext());
        assertTrue(iterator.hasNext());
        assertEquals(paczka2,iterator.getNext());
        assertFalse(iterator.hasNext());
        assertNull(iterator.getNext());
    }

    @Test
    void reset() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka2 = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka2);
        assertEquals(0,iterator.obecnaPozycja);
        iterator.getNext();
        assertNotEquals(0,iterator.obecnaPozycja);
    }

    @Test
    void size() {
        ArrayList<Paczka> paczki = new ArrayList<Paczka>();
        PaczkiIterator iterator = new PaczkiIterator(paczki);
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        Paczka paczka2 = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczki.add(paczka);
        paczki.add(paczka2);
        assertEquals(2,iterator.size());
    }
}