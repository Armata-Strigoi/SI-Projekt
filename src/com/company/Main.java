package com.company;

import java.sql.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        GlownySystem system = GlownySystem.getInstance();
        system.zaladujSystem("root","root");
        system.start();



    }
}
