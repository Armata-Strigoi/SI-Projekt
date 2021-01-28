package com.company;

public class GlownySystem {
    private static Connect connection;
    private Logowanie log;
    private UzytkownikCore user;
    private Magazyn magazyn;
    private static GlownySystem singleton = null; // Singleton
    private GlownySystem(){};

    public static GlownySystem getInstance(){ // Singleton
        if(singleton == null)
            singleton = new GlownySystem();
        return singleton;
    }

    public void zaladujSystem(String login, String pass){
        if(this.magazyn == null) {
            this.connection = new Connect(login,pass);
            PaczkaFactory.PobierzZBazy(connection.connection);
            this.magazyn = new Magazyn(connection.connection);
            this.log = new Logowanie(magazyn,connection.connection);
        } else {
            System.err.println("System juz zostal zaladowany!");
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
        this.magazyn.kopia.ZapiszStanPaczek(this.magazyn.pIterator,this.connection.connection);
        this.connection.close();
    }
}
