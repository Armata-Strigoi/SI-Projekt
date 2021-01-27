package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Logowanie {
    Magazyn magazyn;
    private String[][] dane = new String[2][4]; // Login haslo rodzaj pracownika i id (pobierane z bazy)
    Connection connection;

    private UzytkownikCore Uprawnij(String kogo, int id){
        if(kogo.equals("PracownikStacjonarny"))return new PracownikStacjonarny(magazyn,id);
        else if(kogo.equals("Kurier")) return new Kurier(magazyn,id,connection);
        else return null;
    }

    private UzytkownikCore SprawdzDane(String login, String haslo){
        for(int i = 0;i<this.dane.length;i++){
            if(null != this.dane[i][0]) {
                if (this.dane[i][0].equals(login)) {
                    if (this.dane[i][1].equals(haslo)) {
                        return Uprawnij(this.dane[i][2], i);
                    }
                }
            }
        }
        return null;
    }

    Logowanie(Magazyn magazyn, Connection connection){
        this.magazyn = magazyn;
        this.connection = connection;
        int i=0;
        try {
            Statement query = this.connection.createStatement();
            ResultSet result = query.executeQuery("select login,hasło,stanowisko,idPracownicy from Pracownicy");
            while(result.next()) {
                dane[i][0] = result.getString("Login");
                dane[i][1] = result.getString("Hasło");
                dane[i][2] = result.getString("stanowisko");
                dane[i][3] = result.getString("idPracownicy");
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UzytkownikCore Loguj(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj login:");
        String login = scan.nextLine();
        System.out.println("Podaj haslo:");
        String haslo = scan.nextLine();

        return SprawdzDane(login,haslo);
    }
}
