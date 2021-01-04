package com.company;

import java.util.Scanner;

public class Logowanie {
    Magazyn magazyn;
    private String[][] dane = new String[2][4]; // Login haslo rodzaj pracownika i id (pobierane z bazy)

    private Uzytkownik Uprawnij(String kogo,int id){
        if(kogo.equals("Pracownik_stacjonarny"))return new PracownikStacjonarny(magazyn,id);
        else if(kogo.equals("Kierowca")) return new Kurier(magazyn,id);
        else return null;
    }

    private Uzytkownik SprawdzDane(String login, String haslo){
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

    Logowanie(Magazyn magazyn){
        this.magazyn = magazyn;
        dane[0][0] = "root";
        dane[0][1] = "toor";
        dane[0][2] = "Pracownik_stacjonarny";
        dane[0][3] = "1";

        dane[1][0] = "kierowca";
        dane[1][1] = "toor";
        dane[1][2] = "Kierowca";
        dane[1][3] = "2";
    }

    public Uzytkownik Loguj(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj login:");
        String login = scan.nextLine();
        System.out.println("Podaj haslo:");
        String haslo = scan.nextLine();

        return SprawdzDane(login,haslo);
    }
}
