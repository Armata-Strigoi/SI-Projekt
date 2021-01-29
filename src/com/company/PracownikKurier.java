package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PracownikKurier extends Pracownik {
    ArrayList<Paczka> lista_paczek;
    Pojazd pojazd;

    public PracownikKurier(Magazyn magazyn, int id, Connection connection){
        super(magazyn,id,connection);
    }

    private void PobierzPaczki(){

        this.lista_paczek = this.magazyn.PrzydzielPaczki();
        if(this.lista_paczek != null && this.lista_paczek.size()>0){
            for(int i=0;i<lista_paczek.size();i++){
                String sql = "UPDATE Paczki SET idPracownicy = ? WHERE idPaczki = ?";
                try {
                    PreparedStatement query = this.connection.prepareStatement(sql);
                    query.setInt(1,1);
                    query.setString(2,this.lista_paczek.get(i).idPaczki);
                    query.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    };

    private void PrzydzielPojazd(){
        if(lista_paczek != null) {
            this.pojazd = this.magazyn.PrzydzielSamochod(lista_paczek);
            if(this.pojazd != null)System.out.println("Przydzielono: " + this.pojazd.toString());
        }
    };

    private void DostarczPaczke(){
        String idPaczki;
        System.out.println("--- DOSTARCZANIE PACZKI ---");
        System.out.println("Podaj id paczki do dostarczenia");
        idPaczki = scanner.next();
        int index = ZnajdzPaczke(idPaczki);
        if(index >= 0){
            if(this.lista_paczek.get(index).numer_statusu>=3) System.err.println("Juz dostarczono te paczke!");
            else{
                String sql = "UPDATE Paczki SET status = 2,data_dostarczenia = ? WHERE idPaczki = ?";
                try {
                    PreparedStatement query = this.connection.prepareStatement(sql);
                    query.setObject(1,this.lista_paczek.get(index).Dostarcz(id_pracownik));
                    query.setString(2,this.lista_paczek.get(index).idPaczki);
                    query.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println("Dostarczono paczke");
            }
        } else {
            System.err.println("Nie znaleziono paczki o podanym ID");
        }
    }

    private int ZnajdzPaczke(String idPaczki){
        int index = -1;
        if(lista_paczek != null) {
            for (int i = 0; i < lista_paczek.size(); i++) {
                if (this.lista_paczek.get(i).idPaczki.contains(idPaczki)) index = i;
            }
        }
        return index;
    }

    private void ZglosDostarczeniePaczek(){// zwroc samochod i liste
        if(this.lista_paczek != null) {
            this.magazyn.DostarczPaczki(this.lista_paczek, this.pojazd);
            this.lista_paczek.clear();
            this.pojazd = null;
        }
    }

    private void Listuj(){
        if(this.lista_paczek != null && this.lista_paczek.size()>0){
            System.out.println("ID | RODZAJ | ADRES | STATUS");
            for(int i=0;i<lista_paczek.size();i++){
                System.out.println(this.lista_paczek.get(i).idPaczki + " | " + this.lista_paczek.get(i).decorate() + " | " + this.lista_paczek.get(i).ulica_o + " | " + this.lista_paczek.get(i).status);
            }
        }
    }

    @Override
    public void pracuj(){
        while(!this.wyloguj){
            System.out.println("--- KONTO KIEROWCY ---");
            System.out.println("Co chcesz zrobic?");
            System.out.println("1. Pobierz paczki z magazynu"); // Pobiera liste paczek z magazunu;
            System.out.println("2. Przydziel pojazd"); // Pojazd zostaje przydzielony na podstawie kubatury paczek
            System.out.println("3. Dostarcz paczke"); // Dodaje do listy dostarczonych paczek i zmienia status dostarczonej paczki
            System.out.println("4. Zglos dostarczenie paczek"); // Wysyla liste dostarczonych paczek do systemu i zwraca samochod
            System.out.println("5. Wyswietl liste paczek");
            System.out.println("0. Wyjdz");

            this.opcja = scanner.nextInt();

            if(this.opcja == 1){
                PobierzPaczki();
            }else if(this.opcja == 2){
                PrzydzielPojazd();
            }else if(this.opcja == 3){
                DostarczPaczke();
            }else if(this.opcja == 4){
                ZglosDostarczeniePaczek();
            }else if(this.opcja == 5){
                Listuj();
            }else if(this.opcja == 0) {
                if((this.lista_paczek != null && this.lista_paczek.size()==0) || this.lista_paczek == null)
                    this.wyloguj = true;
                else System.out.println("Skoncz prace lub odloz pozostale paczki!");
            }
        }
    };

}
