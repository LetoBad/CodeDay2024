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
public class Funcionario {
    ConDB condb = new ConDB();
    private String nome, telefone, email;
    private int tipo;

    public Funcionario(String nome, String telefone, String email, int tipo) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.tipo = tipo;
    }
    
    //Cria funcionario a partir do objeto
    public void criarFuncionarioBD() throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("INSERT INTO funcionario (`nome_func`,`telefone_func`,`email_func`,`id_tipofunc`)VALUES('" + nome + "','" + telefone + "','" + email + "'," + tipo + ");");
            query.executeUpdate();
        }
    }
    
    //Coloca professor em uma nova turma (FORMATO DA HORA TEM QUE SER HH:MM:SS)
    public void profNovaTurma(String nome_turma, String horario, String nomeAula, Turma turma) throws SQLException{
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("INSERT INTO turma_funcionario(`id_turma`,`id_func`,`horario_aula`,`nome_aula`)VALUES("+turma.getTurmaId()+","+getFuncionarioId()+",`"+horario+"`,`"+nomeAula+"`);");
            query.executeUpdate();
        }
    }
    
    //atualiza funcionario com dados novos
    public void atualizarFuncionarioDB(String nome, String telefone, String email, int turma) throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("UPDATE funcionario SET `nome_func` = '"+nome+"',`telefone_func` = '"+telefone+"',`email_func` ='"+email+"',`id_tipofunc` = "+tipo+" WHERE `id_func` = "+getFuncionarioId()+";");
            query.executeUpdate();

            con.close();
        }

    }

    //Retorna id do funcionario
    public int getFuncionarioId() throws SQLException {

        int retorno = 0;

        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("SELECT id_func FROM funcionario WHERE nome_func = '" + nome + "' AND telefone_func = '" + telefone + "' AND email_func = '" + email + "' AND id_tipofunc = " + tipo + ";");
            ResultSet rSet = query.executeQuery();

            rSet.next();
            retorno = rSet.getInt(1);
            
            con.close();
            return retorno;
        }
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
