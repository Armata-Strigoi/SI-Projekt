package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class PaczkaFactory {
    static Map<String, SharedPaczka> typyPaczek = new HashMap<>();

    public static void PobierzZBazy(){
        //Uzupelnianie shared paczek z bazy moze byc nawet uzyta ta funkcja nizej
    }

    public static List<String> PobierzListeTypow(){ // Pobiera liste typow do wyboru dla pracownika stacjonarnego
        List<String> typy = new ArrayList<String>();

        Object[] cos = typyPaczek.keySet().toArray();
        for(int i =0;i<typyPaczek.size();i++){
            typy.add(typyPaczek.get(cos[i]).toString() + " " + typyPaczek.get(cos[i]).toString());
        }
        return typy;
    }

    public static SharedPaczka getSharedPaczka(String typ){
        SharedPaczka sharedpaczka = typyPaczek.get(typ);
        if(sharedpaczka == null){
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
            sharedpaczka = new SharedPaczka(wysokosc,szerokosc,glebokosc,koszt);
            typyPaczek.put(typ,sharedpaczka);

            // DODANIE STWORZONEJ PACZKI DO BAZY I WYGENEROWANIE ID
        }
        return sharedpaczka;
    }


}
