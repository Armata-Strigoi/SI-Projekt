package com.company;

import java.sql.*;
import java.util.*;

public class PaczkaFactory {
    private static HashMap<String, SharedPaczka> typyPaczek = new HashMap<>();

    public static void PobierzZBazy(Connection connection){
        try {
            Statement query = connection.createStatement();
            ResultSet result = query.executeQuery("select * from wspoldzielonedane");
            while(result.next()) {
                typyPaczek.put(result.getString("idWspoldzieloneDane"), new SharedPaczka(result.getFloat("Wysokość"),
                        result.getFloat("Szerokość"), result.getFloat("Głębokość"), result.getFloat("Kubatura"), result.getFloat("Koszt")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<String> PobierzListeTypow(){ // Pobiera liste typow do wyboru dla pracownika stacjonarnego
        List<String> typy = new ArrayList<String>();
        Object[] cos = typyPaczek.keySet().toArray();

        for(int i =0;i<typyPaczek.size();i++){
            typy.add(cos[i] + " " + typyPaczek.get(cos[i]).toString());
        }
        return typy;
    }

    public static SharedPaczka getSharedPaczka(String typ, Connection connection){
        SharedPaczka sharedpaczka = typyPaczek.get(typ);
        if(sharedpaczka == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Tworzenie nowego typu paczki ---");
            System.out.println("Podaj wysokosc paczki:");
            float wysokosc = scanner.nextFloat();
            System.out.println("Podaj szerokosc paczki:");
            float szerokosc = scanner.nextFloat();
            System.out.println("Podaj glebokosc paczki:");
            float glebokosc = scanner.nextFloat();
            System.out.println("Podaj koszt wyslania paczki:");
            float koszt = scanner.nextFloat();
            sharedpaczka = new SharedPaczka(wysokosc, szerokosc, glebokosc, koszt);
            typyPaczek.put(typ, sharedpaczka);
            String sql = "insert into wspoldzielonedane values (?,?,?,?,?,?)";
            try {
                PreparedStatement query = connection.prepareStatement(sql);
                query.setString(1, typ);
                query.setFloat(2, typyPaczek.get(typ).szerokosc);
                query.setFloat(3, typyPaczek.get(typ).wysokosc);
                query.setFloat(4, typyPaczek.get(typ).glebokosc);
                query.setFloat(5, typyPaczek.get(typ).kubatura);
                query.setFloat(6, typyPaczek.get(typ).koszt);
                query.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return sharedpaczka;
    }

    public static String getType(SharedPaczka sharedPaczka){
        for(String iterator : typyPaczek.keySet()){
            if(sharedPaczka.equals(typyPaczek.get(iterator)))
                return iterator;
        }
        return null;
    }
}
