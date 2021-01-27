package com.company;

import java.sql.Connection;
import java.util.*;
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
                System.out.println("Podaj id pracownika u ktorego chcesz dac premie:");
                int pracownik = scanner.nextInt();
                System.out.println("Podaj wysokosc premii:");
                float premia = scanner.nextFloat();
                dajPremie(pracownik, premia);
            }else if(this.opcja == 0) {
                this.wyloguj = true;
            }
        }
    }

    private void generujRaport(int miesiac){ // Trzeba obliczyc wartosc paczek (koszt) - wyplata pracownikow (wyplata)

        if(miesiac >= new java.util.Date().getMonth() || miesiac > 11){
            System.out.println("Podaj odpowiedni miesiac!");
            return;
        }
        System.out.println("--- Generuje raport z miesiaca: " + (miesiac+1) + " ---");
        float zysk_z_paczek = this.magazyn.paczkiiii.ZwrocZyskZPaczek(miesiac);
        float wyplaty = 0;
        System.out.println("Zysk z paczek: " + zysk_z_paczek);

        // @@@@@@@@@@@@@@@@@ Tutaj trzeba zsumowac z bazy danych wyplaty pracownikow

        System.out.println("Koszt pracownikow: " + wyplaty);
        System.out.println("Razem: " + (zysk_z_paczek - wyplaty));

    }

    private void generujRanking(int miesiac){ // Trzeba pobrac liste paczek i na tej podstawie wygenerowac ranking (id pracownika jest w paczce)
        if(miesiac >= new java.util.Date().getMonth() || miesiac > 11){
            System.out.println("Podaj odpowiedni miesiac!");
            return;
        }
        ArrayList<Paczka> paczki_z_miesiaca = this.magazyn.paczkiiii.ZwrocPaczkiZMiesiaca(miesiac);
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

    }

    private Float obliczWartosc(Paczka paczka){
        return paczka.wspoldzielone_dane.koszt + (paczka.wspoldzielone_dane.kubatura/10000) + (paczka.waga/100);
    }

    private void dajPremie(Integer id, float premia){ // Damy premie pracownikowi o podanym id
        // Tutaj wystarczy dac id i w bazie zmienic wyplate
    }
}
