package com.company;


import java.util.Date;

public class Paczka {
    int id,id_pracownik,id_kuriera,numer_statusu,nr_dom_o;
    float wysokosc,szerokosc,glebokosc,waga,kubatura;
    String status,imie_n,nazwisko_n,nr_tel_n,nr_tel_o,ulica_o;
    Date data_nadania,data_dostarczenia;

    Paczka(int id, int id_pracownik, float wysokosc, float szerokosc, float glebokosc, float waga, String imie_n, String nazwisko_n, String nr_tel_n, String nr_tel_o, String ulica_o, int nr_dom_o){
        this.id = id;
        this.id_pracownik = id_pracownik;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.glebokosc = glebokosc;
        this.waga = waga;
        this.kubatura = ObliczKubature();
        this.numer_statusu = 0;
        this.status = "Nadana";
        this.id_kuriera = -1; // Zaden kurier nie dostarczyl jeszcze paczki

        this.nr_dom_o = nr_dom_o;
        this.imie_n = imie_n;
        this.nazwisko_n = nazwisko_n;
        this.nr_tel_n = nr_tel_n;
        this.nr_tel_o = nr_tel_o;
        this.ulica_o = ulica_o;
        this.data_nadania = new java.util.Date();
        this.data_dostarczenia = null;
    }

    private float ObliczKubature(){
        return this.wysokosc * this.szerokosc * this.glebokosc;
    }

    public void ZaktualizujStatus() {
        this.numer_statusu++;
        if(this.numer_statusu == 1) {
            this.status = "Przekazana do doreczenia";
        }else if(this.numer_statusu == 2){
            this.status = "Doreczono";
        }else if(this.numer_statusu > 2) this.numer_statusu = 2;
    }

    public void Dostarcz(int id_kuriera){
        this.id_kuriera = id_kuriera;
        this.data_dostarczenia = new java.util.Date();
        ZaktualizujStatus();
    }

    public void CofnijStatus(){ // Jesli nie udalo sie dostarczyc przesylki to wraca do magazuny
        this.numer_statusu = 0;
        this.status = "Nadana";
    }


}
