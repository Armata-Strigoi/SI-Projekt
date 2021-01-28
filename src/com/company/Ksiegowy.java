package com.company;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class Ksiegowy  extends Pracownik {

    Ksiegowy(Magazyn magazyn, int id, Connection connection){
        super(magazyn,id,connection);
    }

    @Override
    public void pracuj() {
        while (!this.wyloguj) {
            System.out.println("--- KONTO KSIEGOWEGO ---");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1. Wygeneruj zestawienie (raport) miesieczne");
            System.out.println("2. Wygeneruj ranking pracownikow");
            System.out.println("3. Daj premie pracownikowi");
            System.out.println("4. Zapisz stan bazy paczek");
            System.out.println("5. Przywroc stan paczek z caretaker'a");
            System.out.println("0. Wyjdz");
            this.opcja = scanner.nextInt();

            if(this.opcja == 1){
                System.out.println("Podaj numer miesiaca do wygenerowania raportu:");
                int miesiac = scanner.nextInt();
                generujRaport(miesiac);
            }else if(this.opcja == 2){
                System.out.println("Podaj numer miesiaca do wygenerowania rankingu:");
                int miesiac = scanner.nextInt();
                generujRanking(miesiac);
            }else if(this.opcja == 3) {
                System.out.println("Podaj imie pracownika u ktorego chcesz dac premie:");
                String imie = scanner.next();
                System.out.println("Podaj nazwisko pracownika u ktorego chcesz dac premie:");
                String nazwisko = scanner.next();
                System.out.println("Podaj wysokosc premii:");
                float premia = scanner.nextFloat();
                dajPremie(imie, nazwisko, premia);
            }else if(this.opcja == 4){
                this.magazyn.ZapiszStanPaczek();
                this.magazyn.pIterator.reset();
            }else if(this.opcja == 5){
                this.magazyn.kopia.PrzywrocPoprzedniStan(magazyn);
                this.magazyn.pIterator.reset();
                this.magazyn.kopia.ZapiszDoBazy(connection);
                this.magazyn.pIterator.reset();
            }else if(this.opcja == 0) {
                this.wyloguj = true;
            }
            scanner.reset();
        }
    }

    private void generujRaport(int miesiac){

        if(miesiac-1 > new java.util.Date().getMonth() || miesiac-1 > 11){
            System.out.println("Podaj odpowiedni miesiac!");
            return;
        }
        System.out.println("--- Generuje raport z miesiaca: " + (miesiac+1) + " ---");
        float zysk_z_paczek = this.magazyn.pIterator.ZwrocZyskZPaczek(miesiac);
        float wyplaty = 0;
        System.out.println("Zysk z paczek: " + zysk_z_paczek);
        try {
            Statement query = this.connection.createStatement();
            ResultSet result = query.executeQuery("SELECT SUM(wypłata) FROM pracownicy;");
            if(result.next()){
                wyplaty = result.getFloat(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Koszt pracownikow: " + wyplaty);
        System.out.println("Razem: " + (zysk_z_paczek - wyplaty));
        this.magazyn.pIterator.reset();

    }

    private void generujRanking(int miesiac){ // Trzeba pobrac liste paczek i na tej podstawie wygenerowac ranking (id pracownika jest w paczce)
        if(miesiac-1 > new java.util.Date().getMonth() || miesiac-1 > 11){
            System.out.println("Podaj odpowiedni miesiac!");
            return;
        }
        ArrayList<Paczka> paczki_z_miesiaca = this.magazyn.pIterator.ZwrocPaczkiZMiesiaca(miesiac);
        HashMap<Integer, Float> ranking = new HashMap<>();
        for(int i=0;i<paczki_z_miesiaca.size();i++){
            Paczka tmp = paczki_z_miesiaca.get(i);
            if(ranking.containsKey(tmp.id_kuriera)){
                ranking.put(tmp.id_kuriera,
                        ranking.get(tmp.id_kuriera) + obliczWartosc(tmp));
            } else {
                ranking.put(tmp.id_kuriera,obliczWartosc(tmp));
            }
        }
        Map<Integer, Float> result = ranking.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("Ranking pracownikow:");
        System.out.println(result);
        this.magazyn.pIterator.reset();
    }

    private Float obliczWartosc(Paczka paczka){
        return paczka.wspoldzielone_dane.koszt + (paczka.wspoldzielone_dane.kubatura/10000) + (paczka.waga/100);
    }

    private void dajPremie(String imie, String nazwisko, float premia){ // Damy premie pracownikowi o podanym imieniu i nazwisku
        String update = "UPDATE pracownicy SET wypłata = ? WHERE imie = ? AND nazwisko = ?;";
        try {
            PreparedStatement queryUpdate = this.connection.prepareStatement(update);
            Statement querySelect = this.connection.createStatement();
            ResultSet result = querySelect.executeQuery("SELECT wypłata FROM pracownicy WHERE imie = '" + imie + "' AND nazwisko = '" + nazwisko + "'");
            if(result.next()) {
                queryUpdate.setFloat(1, result.getFloat(1) + premia);
                queryUpdate.setString(2, imie);
                queryUpdate.setString(3, nazwisko);
                queryUpdate.executeUpdate();
                System.out.println("Przyznano prmie dla " + imie + " " + nazwisko + " w wysokości " + premia);
            }
            else{
                System.out.println("Nie znaleziono pracownika o podanych parametrach");
                return;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
