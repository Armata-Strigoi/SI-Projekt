package com.company;

public class GlownySystem {
    public static Connect connect;
    public Logowanie log;
    public UzytkownikCore user;
    public Magazyn magazyn;
    public static GlownySystem singleton = null; // Singleton

    public GlownySystem(){};
    public static GlownySystem getInstance(){// Singleton
        if(singleton == null)
            singleton = new GlownySystem();
        return singleton;
    }

    public void zaladujSystem(String login, String pass){
        if(this.magazyn == null) {
            this.connect = new Connect(login,pass);
            PaczkaFactory.PobierzZBazy(connect.connection);
            this.magazyn = new Magazyn(connect.connection);
            this.log = new Logowanie(magazyn,connect.connection);
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
        this.magazyn.kopia.ZapiszStanPaczek(this.magazyn.pIterator,this.connect.connection);
        this.connect.close();
    }
}
