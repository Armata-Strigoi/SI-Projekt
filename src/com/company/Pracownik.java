package com.company;

import java.sql.Connection;
import java.util.Scanner;

public class Pracownik implements UzytkownikCore {
    Scanner scanner = new Scanner(System.in);
    int opcja,id_pracownik;
    volatile Magazyn magazyn;
    Connection connection;

    Pracownik(Magazyn magazyn, int id, Connection connection){
        this.magazyn = magazyn;
        this.id_pracownik = id;
        this.connection = connection;
    }

    Pracownik(Magazyn magazyn, int id){
        this.magazyn = magazyn;
        this.id_pracownik = id;
    }

    public boolean wyloguj = false;
    public void pracuj(){};
}
