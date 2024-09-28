/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.atrasoaluno;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author faart
 */
public class AtrasoAluno {

    public static void main(String[] args) throws SQLException {

        ConDB condb = new ConDB();
        
        condb.returnTodosAtrasosAluno("Julia Costa");
        for(Atraso a: condb.arrayAtraso){
            System.out.println(a.getAluno().getNome());
            System.out.println(a.getJustificativa());
        }
    }
}
