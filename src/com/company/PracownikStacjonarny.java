package com.company;

import java.sql.Connection;

public class PracownikStacjonarny extends Pracownik {

    PracownikStacjonarny(Magazyn magazyn, int id){
        super(magazyn, id);
    }
    private void NadajPaczke(){
        System.out.println("--- Nadawanie paczki ---");
        System.out.println("Podaj typ paczki:");
        String typ = scanner.nextLine();
        System.out.println("Podaj wage paczki:");
        float waga = scanner.nextFloat();

        System.out.println("Podaj ulice odbiorcy:");
        String ulica_o = scanner.next();
        System.out.println("Podaj numer ulicy odbiorcy:");
        int nr_ulica_o = scanner.nextInt();
        System.out.println("Podaj numer domu odbiorcy:");
        int nr_dom_o = scanner.nextInt();
        System.out.println("Podaj numer telefonu odbiorcy:");
        String nr_tel_o = scanner.next();

        System.out.println("Podaj ulice nadawcy:");
        String ulica_n = scanner.next();
        System.out.println("Podaj numer ulicy nadawcy:");
        int nr_ulica_n = scanner.nextInt();
        System.out.println("Podaj numer domu nadawcy:");
        int nr_dom_n = scanner.nextInt();
        System.out.println("Podaj numer telefonu nadawcy:");
        String nr_tel_n = scanner.next();

        this.magazyn.NadajPaczke(typ,waga,ulica_o,nr_ulica_o,nr_dom_o,nr_tel_o,ulica_n,nr_ulica_n,nr_dom_n,nr_tel_n);
    }

    @Override
    public void pracuj(){
        while(!this.wyloguj){
            System.out.println("--- KONTO PRACOWNIKA STACJONARNEGO ---");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1. Nadaj paczke");
            System.out.println("0. Wyjdz");

            this.opcja = scanner.nextInt();

            if(this.opcja == 1){
                NadajPaczke();
            }else if(this.opcja == 0) {
                this.wyloguj = true;
            }
        }
    };

}
