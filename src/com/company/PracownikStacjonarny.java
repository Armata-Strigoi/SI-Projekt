package com.company;

public class PracownikStacjonarny extends Pracownik {

    PracownikStacjonarny(Magazyn magazyn, int id){
        super(magazyn, id);
    }
//int id_pracownik, float wysokosc, float szerokosc, float glebokosc, float waga
    private void NadajPaczke(){
        System.out.println("--- Nadawanie paczki ---");
        System.out.println("Podaj wysokosc paczki:");
        float wysokosc = scanner.nextFloat();
        System.out.println("Podaj szerokosc paczki:");
        float szerokosc = scanner.nextFloat();
        System.out.println("Podaj glebokosc paczki:");
        float glebokosc = scanner.nextFloat();
        System.out.println("Podaj wage paczki:");
        float waga = scanner.nextFloat();
        System.out.println("Podaj imie nadawcy:");
        scanner.nextLine();
        String imie_n = scanner.nextLine();
        System.out.println("Podaj nazwisko nadawcy:");
        String nazwisko_n = scanner.nextLine();
        System.out.println("Podaj nr telefonu nadawcy");
        String nr_tel_n = scanner.nextLine();
        System.out.println("Podaj nr telefonu odbiorcy");
        String nr_tel_o = scanner.nextLine();
        System.out.println("Podaj ulice odbiorcy");
        String ulica_o = scanner.nextLine();
        System.out.println("Podaj nr domu odbiorcy");
        int nr_dom_o = scanner.nextInt();

        this.magazyn.NadajPaczke(this.id_pracownik,wysokosc,szerokosc,glebokosc,waga,imie_n,nazwisko_n,nr_tel_n,nr_tel_o,ulica_o,nr_dom_o);
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
