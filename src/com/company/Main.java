package com.company;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        GlownySystem system = new GlownySystem();
        system.stworzSystem("root","root");
        system.start();
    }
}
