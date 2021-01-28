package com.company;


import java.sql.*;

public class Memento {

    private Caretaker caretaker;

    Memento(Connection connection){
        this.caretaker = new Caretaker();
        ZaladujZBazy(connection);
    }

    private void ZaladujZBazy(Connection connection){
        try {
            Statement query = connection.createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM paczkiKopia");
            while(result.next()) {
                PaczkaCore tmpCore = null;
                if(result.getInt("CzyDelikatna")==1) tmpCore = new PaczkaDelikatna(tmpCore);
                if(result.getInt("CzyMagnetyczna")==1) tmpCore = new PaczkaZZawartosciaMagnetyczna(tmpCore);
                if(result.getInt("CzyPaletowa")==1) tmpCore = new PaczkaPaletowa(tmpCore);
                Paczka tmpPaczka = new Paczka(
                        result.getString("idPaczki"), PaczkaFactory.getSharedPaczka(result.getString("idWspoldzieloneDane"),connection),
                        result.getInt("idPracownicy"), result.getInt("Status"),  result.getFloat("Waga"), result.getDate("Data_nadania"),
                        result.getDate("Data_dostarczenia"), result.getString("Ulica_o"), result.getInt("Nr_ulica_o"),
                        result.getInt("Nr_dom_o"), result.getString("Nr_tel_o"), result.getString("Ulica_n"),
                        result.getInt("Nr_ulica_n"), result.getInt("Nr_dom_n"), result.getString("Nr_tel_n"),tmpCore);
                this.caretaker.paczki.add(tmpPaczka);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Załadowano kopie do bazy!");
    }

    public void ZapiszDoBazy(Connection connection){

        String sqlDelete = "DELETE FROM paczki";
        try{
            Statement delete = connection.createStatement();
            delete.executeUpdate(sqlDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Blad wczytywanai listy paczek");
            return;
        }
        PaczkiIterator iterator = new PaczkiIterator(caretaker.paczki);
        while(iterator.hasNext()){
            try {
                Paczka tmp = iterator.getNext();
                SharedPaczka sptmp = tmp.wspoldzielone_dane;
                String sql = "insert into paczki values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement query = connection.prepareStatement(sql);
                query.setString(1,tmp.idPaczki);
                query.setString(2,PaczkaFactory.getType(sptmp));
                if(tmp.id_kuriera != null && tmp.id_kuriera != 0) query.setInt(3,tmp.id_kuriera);
                else query.setNull(3,java.sql.Types.INTEGER);
                query.setInt(4,tmp.numer_statusu);
                query.setFloat(5,tmp.waga);
                query.setObject(6,tmp.data_nadania);
                query.setObject(7,tmp.data_dostarczenia);
                query.setString(8,tmp.ulica_o);
                query.setInt(9,tmp.nr_ulica_o);
                query.setInt(10,tmp.nr_dom_o);
                query.setString(11,tmp.nr_tel_o);
                query.setString(12,tmp.ulica_n);
                query.setInt(13,tmp.nr_ulica_n);
                query.setInt(14,tmp.nr_dom_n);
                query.setString(15,tmp.nr_tel_n);
                if (tmp.decorate().contains("Delikatna"))
                    query.setInt(16,1);
                else query.setInt(16,0);
                if (tmp.decorate().contains("Z zawartoscia megnetyczna"))
                    query.setInt(17,1);
                else query.setInt(17,0);
                if (tmp.decorate().contains("Paletowa"))
                    query.setInt(18,1);
                else query.setInt(18,0);
                query.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("Przeladowano historie");
    }

    public void ZapiszStanPaczek(PaczkiIterator iterator, Connection connection){
        caretaker.paczki.clear();
        String sqlDelete = "DELETE FROM paczkiKopia";
        try{
            Statement delete = connection.createStatement();
            delete.executeUpdate(sqlDelete);
            } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Blad wczytywanai listy paczek");
            return;
        }
        while(iterator.hasNext()){
            Paczka tmp = iterator.getNext();
            SharedPaczka sptmp = tmp.wspoldzielone_dane;
            caretaker.paczki.add(tmp);
            String sql = "insert into PaczkiKopia values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement query = connection.prepareStatement(sql);
                query.setString(1,tmp.idPaczki);
                query.setString(2,PaczkaFactory.getType(sptmp));
                if(tmp.id_kuriera != null && tmp.id_kuriera != 0) query.setInt(3,tmp.id_kuriera);
                    else query.setNull(3,java.sql.Types.INTEGER);
                query.setFloat(4,sptmp.szerokosc);
                query.setFloat(5,sptmp.wysokosc);
                query.setFloat(6,sptmp.glebokosc);
                query.setFloat(7,sptmp.kubatura);
                query.setFloat(8,sptmp.koszt);
                query.setInt(9,tmp.numer_statusu);
                query.setFloat(10,tmp.waga);
                query.setObject(11,tmp.data_nadania);
                query.setObject(12,tmp.data_dostarczenia);
                query.setString(13,tmp.ulica_o);
                query.setInt(14,tmp.nr_ulica_o);
                query.setInt(15,tmp.nr_dom_o);
                query.setString(16,tmp.nr_tel_o);
                query.setString(17,tmp.ulica_n);
                query.setInt(18,tmp.nr_ulica_n);
                query.setInt(19,tmp.nr_dom_n);
                query.setString(20,tmp.nr_tel_n);
                if (tmp.decorate().contains("Delikatna"))
                    query.setInt(21,1);
                else query.setInt(21,0);
                if (tmp.decorate().contains("Z zawartoscia megnetyczna"))
                    query.setInt(22,1);
                else query.setInt(22,0);
                if (tmp.decorate().contains("Paletowa"))
                    query.setInt(23,1);
                else query.setInt(23,0);
                query.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("Przeladowano historie");
    }

    public void PrzywrocPoprzedniStan(Magazyn magazyn){
        PaczkiIterator tmp = new PaczkiIterator(caretaker.paczki);
        magazyn.pIterator = tmp;
    }
}
