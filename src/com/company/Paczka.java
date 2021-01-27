package com.company;


import java.util.Date;

public class Paczka implements PaczkaCore{
    int numer_statusu,nr_ulica_o,nr_dom_o,nr_ulica_n,nr_dom_n;
    Integer id_kuriera;

    float waga,koszt;

    String idPaczki,status,ulica_o,nr_tel_o,ulica_n,nr_tel_n;
    Date data_nadania,data_dostarczenia;
    SharedPaczka wspoldzielone_dane;

    Paczka(

            String idPaczki, SharedPaczka wpoldzieloneDane, float waga, String ulica_o,
            int nr_ulica_o, int nr_dom_o, String nr_tel_o,String ulica_n, int nr_ulica_n, int nr_dom_n, String nr_tel_n

    ){

        this.idPaczki = idPaczki;
        this.wspoldzielone_dane = wpoldzieloneDane;
        this.waga = waga;
        this.numer_statusu = 0;
        this.status = "Nadana";
        this.id_kuriera = null;

        this.nr_ulica_n = nr_ulica_n;
        this.nr_ulica_o = nr_ulica_o;
        this.nr_dom_n = nr_dom_n;
        this.nr_dom_o = nr_dom_o;
        this.nr_tel_n = nr_tel_n;
        this.nr_tel_o = nr_tel_o;
        this.ulica_n = ulica_n;
        this.ulica_o = ulica_o;
        this.data_nadania = new java.util.Date();
        this.data_dostarczenia = null;
    }

    Paczka(

           String idPaczki, int id_danych_wspoldzielonych, int idPracownicy, int status, float waga,

           Date data_nadania, Date data_dostarczenia, String ulica_o, int nr_ulica_o, int nr_dom_o,
           String nr_tel_o,String ulica_n, int nr_ulica_n, int nr_dom_n, String nr_tel_n, float koszt
    ){

        this.idPaczki = idPaczki;
        this.id_kuriera = idPracownicy;

        this.waga = waga;
        this.numer_statusu = status;
        this.koszt = koszt;
        this.UstawStatus();

        this.nr_dom_o = nr_dom_o;
        this.nr_tel_n = nr_tel_n;
        this.nr_tel_o = nr_tel_o;
        this.ulica_o = ulica_o;
        this.data_nadania = data_nadania;
        this.data_dostarczenia = data_dostarczenia;
    }


    public int ZaktualizujStatus() {
        this.numer_statusu++;
        if(this.numer_statusu == 1) {
            this.status = "Przekazana do doreczenia";
        }else if(this.numer_statusu == 2){
            this.status = "Doreczono";
        }else if(this.numer_statusu > 2) this.numer_statusu = 2;
        return this.numer_statusu;
    }

    public void UstawStatus() {
        if(this.numer_statusu == 0) {
            this.status = "Nadana";
        }else if(this.numer_statusu == 1){
            this.status = "Przekazana do doreczenia";
        }
        else if(this.numer_statusu == 2) {
            this.status = "Doreczono";
        }
    }

    public Date Dostarcz(int id_kuriera){
        this.id_kuriera = id_kuriera;
        this.data_dostarczenia = new java.util.Date();
        ZaktualizujStatus();
        return this.data_dostarczenia;
    }

    public void CofnijStatus(){ // Jesli nie udalo sie dostarczyc przesylki to wraca do magazuny
        this.id_kuriera = null;
        this.numer_statusu = 0;
        this.status = "Nadana";
    }

    @Override
    public String decorate(){
        return "Paczka";
    }

}
