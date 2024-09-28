/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atrasoaluno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author faart
 */
public class Turma {
    ConDB condb = new ConDB();
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Turma(String nome) {
        this.nome = nome;
    }

    //cria turma a partir do objeto
    public void criarTurma() throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("INSERT INTO turma (`nome_turma`) VALUES ('"+nome+"');");
            query.executeUpdate();
        }
    }

    //atualiza aluno com os dados novos
    public void atualizarTurmaDB(String nome) throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("UPDATE turma SET `nome_turma` = '"+nome+"' WHERE `id_turma` = "+getTurmaId()+";");
            query.executeUpdate();

            con.close();
        }

    }

    //Retorna id da turma
    public int getTurmaId() throws SQLException {

        int retorno = 0;

        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("SELECT id_turma FROM turma WHERE nome_turma = '" + nome + "';");
            ResultSet rSet = query.executeQuery();

            rSet.next();
            retorno = rSet.getInt(1);
            
            con.close();
            return retorno;
        }
    }
}
