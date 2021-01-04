package com.company;

public class Paczka {
    int id,id_pracownik,numer_statusu;
    float wysokosc,szerokosc,glebokosc,waga,kubatura;
    String status;

    Paczka(int id, int id_pracownik, float wysokosc, float szerokosc, float glebokosc, float waga){
        this.id = id;
        this.id_pracownik = id_pracownik;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        this.glebokosc = glebokosc;
        this.waga = waga;
        this.kubatura = ObliczKubature();
        this.numer_statusu = 0;
        this.status = "Nadana";
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
        }
    }


}
