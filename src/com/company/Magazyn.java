package com.company;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Magazyn { // Obsluga wysylania paczek, obliczania kubatury i generowania listy paczek czekajacych na wysylke
    private Connection connection;
    private ArrayList<Paczka> paczki;
    private ArrayList<Samochod> dostepne_samochody;

    private ArrayList<PaczkaCore> paczkii;// To trzeba zrobic

    Magazyn(Connection connection){
        this.connection = connection;
        this.paczki = PobierzPaczki();
        this.dostepne_samochody = PobierzSamochody();

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
        ArrayList<Paczka> tmp = new ArrayList<Paczka>();
        try {
            Statement query = this.connection.createStatement();
            ResultSet result = query.executeQuery("select * from Paczki");
            while(result.next())

                tmp.add(new Paczka( /////////////////// DOdane idDanychWspoldzielonych i usuniete wymiary
                        result.getString("idPaczki"), result.getInt("idDanychWspoldzielonych"), result.getInt("idPracownicy"), result.getInt("Status"),
                        result.getFloat("Waga"),
                        result.getDate("Data_nadania"),result.getDate("Data_dostarczenia"),

                        result.getString("Ulica_o"),result.getInt("Nr_ulica_o"),result.getInt("Nr_dom_o"),
                        result.getString("Nr_tel_o"), result.getString("Ulica_n"),result.getInt("Nr_ulica_n"),
                        result.getInt("Nr_dom_n"),result.getString("Nr_tel_n"), result.getFloat("Koszt")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tmp;
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
            int nr_ulica_o, int nr_dom_o, String nr_tel_o,String ulica_n, int nr_ulica_n, int nr_dom_n, String nr_tel_n
    ){
        String uuid = UUID.randomUUID().toString().toUpperCase();
        SharedPaczka sp = PaczkaFactory.getSharedPaczka(typ);
        if(sp == null){
            System.err.println("Nie udalo sie nadac paczki (brak danych wspoldzielonych)");
        }
        this.paczki.add(new Paczka(uuid,sp,waga,ulica_o,nr_ulica_o,nr_dom_o,nr_tel_o,ulica_n,nr_ulica_n,nr_dom_n,nr_tel_n));

        String sql = "insert into Paczki values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement query = this.connection.prepareStatement(sql);
            query.setString(1,this.paczki.get(this.paczki.size() - 1).idPaczki);
            query.setNull(2,java.sql.Types.INTEGER);
            query.setInt(3,this.paczki.get(this.paczki.size() - 1).numer_statusu);

            query.setFloat(7,this.paczki.get(this.paczki.size() - 1).waga);
            query.setObject(9,this.paczki.get(this.paczki.size() - 1).data_nadania);
            query.setObject(10,this.paczki.get(this.paczki.size() - 1).data_dostarczenia);
            query.setString(11,this.paczki.get(this.paczki.size() - 1).ulica_o);
            query.setInt(12,this.paczki.get(this.paczki.size() - 1).nr_ulica_o);
            query.setInt(13,this.paczki.get(this.paczki.size() - 1).nr_dom_o);
            query.setString(14,this.paczki.get(this.paczki.size() - 1).nr_tel_o);
            query.setString(15,this.paczki.get(this.paczki.size() - 1).ulica_n);
            query.setInt(16,this.paczki.get(this.paczki.size() - 1).nr_ulica_n);
            query.setInt(17,this.paczki.get(this.paczki.size() - 1).nr_dom_n);
            query.setString(18,this.paczki.get(this.paczki.size() - 1).nr_tel_n);
            query.setFloat(19,this.paczki.get(this.paczki.size() - 1).koszt);
            query.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Pomyslnie nadano paczke o id: " + uuid);
    };

    public ArrayList<Paczka> PrzydzielPaczki(){
        float kubaturaTMP = 0;
        float kubaturaMAX = ZnajdzNajbardziejLadownySamochod();
        if(kubaturaMAX == 0) return null;
        ArrayList<Paczka> tmp = new ArrayList<Paczka>();
        if(this.paczki.size() != 0) System.out.println("Kurier bierze paczki: ");
        for(int i=0;i<paczki.size();i++){
            if(this.paczki.get(i).numer_statusu == 0) {
                if (kubaturaTMP + this.paczki.get(i).wspoldzielone_dane.kubatura <= kubaturaMAX) {
                    String sql = "UPDATE Paczki SET status = ? WHERE idPaczki = ?";
                    try {
                        PreparedStatement query = this.connection.prepareStatement(sql);
                        query.setInt(1,this.paczki.get(i).ZaktualizujStatus());
                        query.setString(2,this.paczki.get(i).idPaczki);
                        query.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    tmp.add(this.paczki.get(i));
                    System.out.println("ID: " + this.paczki.get(i).idPaczki);

                    kubaturaTMP += this.paczki.get(i).wspoldzielone_dane.kubatura;

                }
            }
        }
        System.out.println("Przydzielono paczki: " + tmp.size());
        return tmp;
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
        for(int i=0;i<paczki.size();i++){
            if(this.paczki.get(i).idPaczki.equals(paka.idPaczki)){
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
                            query.setInt(1, this.paczki.get(index).ZaktualizujStatus());
                            query.setString(2, this.paczki.get(index).idPaczki);
                            query.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        this.paczki.get(index).CofnijStatus();
                        String sql = "UPDATE Paczki SET status = 0 WHERE idPaczki = ?";
                        try {
                            PreparedStatement query = this.connection.prepareStatement(sql);
                            query.setString(1, this.paczki.get(index).idPaczki);
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
