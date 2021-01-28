package com.company;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.UUID;


public class Magazyn { // Obsluga wysylania paczek, obliczania kubatury i generowania listy paczek czekajacych na wysylke
    private Connection connection;
    private ArrayList<Samochod> dostepne_samochody;
    public PaczkiIterator pIterator;
    public Memento kopia;

    Magazyn(Connection connection){
        this.connection = connection;
        this.pIterator = new PaczkiIterator(PobierzPaczki());
        this.dostepne_samochody = PobierzSamochody();
        this.kopia = new Memento(this.connection);
    }

    public void ZapiszStanPaczek(){
        this.kopia.ZapiszStanPaczek(this.pIterator,this.connection);
    }

    private ArrayList<Samochod> PobierzSamochody(){
        ArrayList<Samochod> tmp = new ArrayList<Samochod>();
        try {
            Statement query = this.connection.createStatement();
            ResultSet result = query.executeQuery("select * from Samochody");
            while(result.next())
                tmp.add(new Samochod(result.getInt("idSamochody"),result.getString("typ"),
                        result.getString("marka"),result.getString("model"),result.getFloat("pojemność")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tmp;
    }

    private ArrayList<Paczka> PobierzPaczki(){
        ArrayList<Paczka> tmpList = new ArrayList<Paczka>();
        try {
            Statement query = this.connection.createStatement();
            ResultSet result = query.executeQuery("SELECT paczki.* FROM paczki, wspoldzielonedane WHERE paczki.idWspoldzieloneDane = wspoldzielonedane.idWspoldzieloneDane");
            while(result.next()) {
                PaczkaCore tmpCore = null;
                if(result.getInt("CzyDelikatna")==1) tmpCore = new PaczkaDelikatna(tmpCore);
                if(result.getInt("CzyMagnetyczna")==1) tmpCore = new PaczkaZZawartosciaMagnetyczna(tmpCore);
                if(result.getInt("CzyPaletowa")==1) tmpCore = new PaczkaPaletowa(tmpCore);
                Paczka tmpPaczka = new Paczka(
                        result.getString("idPaczki"), PaczkaFactory.getSharedPaczka(result.getString("idWspoldzieloneDane"),this.connection),
                        result.getInt("idPracownicy"), result.getInt("Status"),  result.getFloat("Waga"), result.getDate("Data_nadania"),
                        result.getDate("Data_dostarczenia"), result.getString("Ulica_o"), result.getInt("Nr_ulica_o"),
                        result.getInt("Nr_dom_o"), result.getString("Nr_tel_o"), result.getString("Ulica_n"),
                        result.getInt("Nr_ulica_n"), result.getInt("Nr_dom_n"), result.getString("Nr_tel_n"),tmpCore);
                tmpList.add(tmpPaczka);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tmpList;
    }

    private float ZnajdzNajbardziejLadownySamochod(){
        if(this.dostepne_samochody.size()>0) {
            int max = 0;
            for (int i = 0; i < this.dostepne_samochody.size(); i++) {
                if (this.dostepne_samochody.get(i).ladownosc > this.dostepne_samochody.get(max).ladownosc)max = i;
            }
            return this.dostepne_samochody.get(max).ladownosc;
        }
        return 0;
    }


    public void NadajPaczke(
            String typ, float waga, String ulica_o, // String typ tez jest dodany, tu sie przekazuje ten typ paczki ze rozmiar, funkcja
            int nr_ulica_o, int nr_dom_o, String nr_tel_o,String ulica_n, int nr_ulica_n, int nr_dom_n, String nr_tel_n,int delikatna, int magnetyczna, int paletowa
    ){
        String uuid = UUID.randomUUID().toString().toUpperCase();
        SharedPaczka sp = PaczkaFactory.getSharedPaczka(typ,this.connection);
        if(sp == null){
            System.err.println("Nie udalo sie nadac paczki (brak danych wspoldzielonych)");
            return;
        }
        PaczkaCore tmpCore = null;
        if(delikatna==1) tmpCore = new PaczkaDelikatna(tmpCore);
        if(magnetyczna==1) tmpCore = new PaczkaZZawartosciaMagnetyczna(tmpCore);
        if(paletowa==1) tmpCore = new PaczkaPaletowa(tmpCore);
        Paczka tmpPaczka = new Paczka(uuid,sp,waga,ulica_o,nr_ulica_o,nr_dom_o,nr_tel_o,ulica_n,nr_ulica_n,nr_dom_n,nr_tel_n,tmpCore);
        this.pIterator.paczki.add(tmpPaczka);
        System.out.println(this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).idPaczki);
        String sql = "insert into Paczki values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement query = this.connection.prepareStatement(sql);
            query.setString(1,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).idPaczki);
            query.setString(2,typ);
            query.setNull(3,java.sql.Types.INTEGER);
            query.setInt(4,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).numer_statusu);
            query.setFloat(5,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).waga);
            query.setObject(6,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).data_nadania);
            query.setObject(7,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).data_dostarczenia);
            query.setString(8,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).ulica_o);
            query.setInt(9,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_ulica_o);
            query.setInt(10,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_dom_o);
            query.setString(11,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_tel_o);
            query.setString(12,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).ulica_n);
            query.setInt(13,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_ulica_n);
            query.setInt(14,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_dom_n);
            query.setString(15,this.pIterator.paczki.get(this.pIterator.paczki.size() - 1).nr_tel_n);
            query.setInt(16,delikatna);
            query.setInt(17,magnetyczna);
            query.setInt(18,paletowa);
            query.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Pomyslnie nadano paczke o id: " + uuid);
    };

    public ArrayList<Paczka> PrzydzielPaczki(){
        this.pIterator.paczki = PobierzPaczki();
        float kubaturaTMP = 0;
        float kubaturaMAX = ZnajdzNajbardziejLadownySamochod();
        if(kubaturaMAX == 0) return null;
        ArrayList<Paczka> tmpList = new ArrayList<Paczka>();
        if(this.pIterator.paczki.size() != 0) System.out.println("Kurier bierze paczki: ");
        for(int i=0;i<pIterator.paczki.size();i++){
            if(this.pIterator.paczki.get(i).numer_statusu == 0) {
                if (kubaturaTMP + this.pIterator.paczki.get(i).wspoldzielone_dane.kubatura <= kubaturaMAX) {
                    String sql = "UPDATE Paczki SET status = ? WHERE idPaczki = ?";
                    try {
                        PreparedStatement query = this.connection.prepareStatement(sql);
                        query.setInt(1,this.pIterator.paczki.get(i).ZaktualizujStatus());
                        query.setString(2,this.pIterator.paczki.get(i).idPaczki);
                        query.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    tmpList.add(this.pIterator.paczki.get(i));
                    System.out.println("ID: " + this.pIterator.paczki.get(i).idPaczki);
                    kubaturaTMP += this.pIterator.paczki.get(i).wspoldzielone_dane.kubatura;
                }
            }
        }
        System.out.println("Przydzielono paczki: " + tmpList.size());
        return tmpList;
    }

    public Samochod PrzydzielSamochod(ArrayList<Paczka> ladunek){
        float kubatura = 0;
        for(int i=0;i<ladunek.size();i++){
            kubatura += ladunek.get(i).wspoldzielone_dane.kubatura;
        }
        return PrzydzielOdpowiedniSamochod(kubatura);
    }

    private Samochod PrzydzielOdpowiedniSamochod(float kubatura){
        if(this.dostepne_samochody.size() <= 0)return null;
        int min = 0;
        for(int i=0;i<dostepne_samochody.size();i++){
            if(this.dostepne_samochody.get(i).ladownosc > kubatura && this.dostepne_samochody.get(i).ladownosc < this.dostepne_samochody.get(min).ladownosc)min = i;
        }
        Samochod tmp = this.dostepne_samochody.get(min);
        this.dostepne_samochody.remove(min); // Oddany w rece kuriera wiec niedostepny
        System.out.println("Przydzielono pojazd nr: " + tmp.id);
        return tmp;
    }

    private int PrzeszukajPaczki(Paczka paka){
        for(int i=0;i<pIterator.paczki.size();i++){
            if(this.pIterator.paczki.get(i).idPaczki.equals(paka.idPaczki)){
                return i;
            }
        }
        return -1;
    }

    private void Magazynuj(ArrayList<Paczka> dostarczony_ladunek){//Zmiana statusu odpowiednich paczek
        int index;
        if(dostarczony_ladunek != null){
            for(int i=0;i<dostarczony_ladunek.size();i++) {
                if ((index = PrzeszukajPaczki(dostarczony_ladunek.get(i))) >= 0) {
                    if (dostarczony_ladunek.get(i).numer_statusu >= 2) {
                        String sql = "UPDATE Paczki SET status = ? WHERE idPaczki = ?";
                        try {
                            PreparedStatement query = this.connection.prepareStatement(sql);
                            query.setInt(1, this.pIterator.paczki.get(index).ZaktualizujStatus());
                            query.setString(2, this.pIterator.paczki.get(index).idPaczki);
                            query.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        this.pIterator.paczki.get(index).CofnijStatus();
                        String sql = "UPDATE Paczki SET status = 0, idPracownicy = NULL WHERE idPaczki = ?";
                        try {
                            PreparedStatement query = this.connection.prepareStatement(sql);
                            query.setString(1, this.pIterator.paczki.get(index).idPaczki);
                            query.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    public void DostarczPaczki(ArrayList<Paczka> dostarczony_ladunek, Samochod zwrocony_samochod){
        Magazynuj(dostarczony_ladunek);
        this.dostepne_samochody.add(zwrocony_samochod);
    }

}
