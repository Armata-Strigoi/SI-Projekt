package com.company;

import java.sql.Connection;

public class Ksiegowy  extends Pracownik {

    Ksiegowy(Magazyn magazyn, int id, Connection connection){
        super(magazyn,id,connection);
    }

    @Override
    public void pracuj() {
        while (!this.wyloguj) {
            System.out.println("--- KONTO KSIEGOWEGO ---");
            System.out.println("Co chcesz zrobic?");
            System.out.println("0. Wyjdz");
            this.opcja = scanner.nextInt();

            if(this.opcja == 0) {
                this.wyloguj = true;
            }
        }
    }

    private void generujRaport(){ // Trzeba obliczyc wartosc paczek (koszt) - wyplata pracownikow (wyplata)

    }

    private void generujRanking(){ // Trzeba pobrac liste paczek i na tej podstawie wygenerowac ranking (id pracownika jest w paczce)

    }

    private void dajPremie(){ // Damy premie pracownikowi o podanym id, mozna np dac flage w generuj ranking zeby od razu dawala premie dla kilku najlepszych pracownikow

    }
}
