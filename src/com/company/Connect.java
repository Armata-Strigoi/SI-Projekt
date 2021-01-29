package com.company;

import java.sql.*;

public class Connect {
    public Connection connection;

    public Connect(String login, String pass){
        if(this.connect(login, pass)) System.err.println("Połączono się z bazą\n");
        else System.err.print("Nie udało się połączyć z bazą\n");
    }

    public boolean connect(String login, String pass){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/inzynieriabaza",login,pass);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean close(){
        try {
            this.connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
