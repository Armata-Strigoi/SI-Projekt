package com.company;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        GlownySystem system = GlownySystem.getInstance(); //Singleton
        system.zaladujSystem("root","root");
        system.start();
    }
}
