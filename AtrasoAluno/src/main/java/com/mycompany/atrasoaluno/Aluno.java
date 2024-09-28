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
public class Aluno {

    ConDB condb = new ConDB();
    private String nome, telefone, email;
    private int id_aluno, turma;

    public Aluno(String nome, String telefone, String email, int turma) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.turma = turma;
    }

    //cria aluno a partir do objeto
    public void criarAlunoBD() throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("INSERT INTO aluno (`nome_aluno`,`telefone_aluno`,`email_aluno`,`id_turma`)VALUES('" + nome + "','" + telefone + "','" + email + "'," + turma + ");");
            query.executeUpdate();
        }
    }

    //atualiza aluno com os dados novos
    public void atualizarAlunoDB(String nome, String telefone, String email, int turma) throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("UPDATE aluno SET `nome_aluno` = '" + nome + "',`telefone_aluno` = '" + telefone + "',`email_aluno` = '" + email + "',`id_turma` = " + turma + " WHERE `id_aluno` = " + getAlunoId() + ";");
            query.executeUpdate();

            con.close();
        }

    }

    //Retorna id do aluno
    public int getAlunoId() throws SQLException {

        int retorno = 0;

        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("SELECT id_aluno FROM aluno WHERE nome_aluno = '" + nome + "' AND telefone_aluno = '" + telefone + "' AND email_aluno = '" + email + "' AND id_turma = " + turma + ";");
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

    public int getTurma() {
        return turma;
    }

    public void setTurma(int turma) {
        this.turma = turma;
    }
}
