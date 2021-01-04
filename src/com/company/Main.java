package com.company;

public class Main {

    public static void main(String[] args) {
        GlownySystem system = new GlownySystem();
        system.stworzSystem();
        system.start();

        System.out.println("TESTOWANIE KOLEJNYCH OSOB LOGUJACYCH SIE DO SYSTEMU");
        system.start();
    }
}
