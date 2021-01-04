package com.company;

import java.util.Scanner;

public class Pracownik implements Uzytkownik {
    Scanner scanner = new Scanner(System.in);
    int opcja,id_pracownik;
    volatile Magazyn magazyn;

    Pracownik(Magazyn magazyn, int id){
        this.magazyn = magazyn;
        this.id_pracownik = id;
    }

    public boolean wyloguj = false;
    public void pracuj(){};
}
