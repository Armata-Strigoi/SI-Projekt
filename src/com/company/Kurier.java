package com.company;

import java.util.ArrayList;

public class Kurier extends Pracownik {
    ArrayList<Paczka> lista_paczek;
    Samochod samochod;

    Kurier(Magazyn magazyn, int id){
        super(magazyn,id);
    }

    private void PobierzPaczki(){
        this.lista_paczek = this.magazyn.PrzydzielPaczki();
    };

    private void PrzydzielPojazd(){
        if(lista_paczek != null) {
            this.samochod = this.magazyn.PrzydzielSamochod(lista_paczek);
        }
    };

    @Override
    public void pracuj(){
        while(!this.wyloguj){
            System.out.println("--- KONTO KIEROWCY ---");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1. Pobierz paczki z magazynu"); // Pobiera liste paczek z magazunu;
            System.out.println("2. Przydziel pojazd"); // Pojazd zostaje przydzielony na podstawie kubatury paczek
            System.out.println("2. Dostarcz paczke"); // Dodaje do listy dostarczonych paczek i zmienia status dostarczonej paczki
            System.out.println("3. Zglos dostarczenie paczek"); // Wysyla liste dostarczonych paczek do systemu
            System.out.println("0. Wyjdz");

            this.opcja = scanner.nextInt();

            if(this.opcja == 1){
                PobierzPaczki();
            }else if(this.opcja == 0) {
                this.wyloguj = true;
            }
        }
    };

}
