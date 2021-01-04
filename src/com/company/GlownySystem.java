package com.company;

public class GlownySystem {
    private Logowanie log;
    private Uzytkownik user;
    private Magazyn magazyn;

    public void stworzSystem(){
        if(this.magazyn == null) {
            this.magazyn = new Magazyn();
            this.log = new Logowanie(magazyn);
        } else {
            System.err.println("System juz istnieje!");
        }
    }

    public void start(){
        for(int i = 0;i<5;i++) {
            if (null != (this.user = this.log.Loguj())){
                System.out.println("Pomyslnie zalogowano do systemu");
                break;
            }
            else System.err.println("Nie udało się zalogować");
            if(i == 4) System.err.println("Przekroczono limit prób logowania!");
        }
        if(null != this.user){
            this.user.pracuj();
        }
    }
}
