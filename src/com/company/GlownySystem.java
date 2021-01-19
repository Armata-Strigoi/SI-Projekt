package com.company;

public class GlownySystem {
    private Connect connection;
    private Logowanie log;
    private Uzytkownik user;
    private Magazyn magazyn;

    public void stworzSystem(String login, String pass){
        if(this.magazyn == null) {
            this.connection = new Connect(login,pass);
            this.magazyn = new Magazyn(connection.connection);
            this.log = new Logowanie(magazyn,connection.connection);
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
            if(i == 4)
                System.err.println("Przekroczono limit prób logowania!");
        }
        if(null != this.user){
            this.user.pracuj();
        }
        this.connection.close();
    }
}
