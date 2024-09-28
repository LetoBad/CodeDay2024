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

public class Atraso {
    ConDB condb = new ConDB();
    
    private Aluno aluno;
    private String data, hora, justificativa;
    private Funcionario funcionario;

    public Atraso(Aluno aluno, String data, String hora, String justificativa, Funcionario funcionario) {
        this.aluno = aluno;
        this.data = data;
        this.hora = hora;
        this.justificativa = justificativa;
        this.funcionario = funcionario;
    }
    
    
    //cria relato de atraso a partir do objeto  (FORMATO DA HORA HH:MM:SS / FORMATO DA DATA AAAA-MM-DD)
    public void criarAtrasoBD() throws SQLException {
        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("INSERT INTO atraso(`data_atraso`,`hora_atraso`,`justificativa`,`id_aluno`,`id_func`)VALUES('"+data+"','"+hora+"','"+justificativa+"',"+aluno.getAlunoId()+","+funcionario.getFuncionarioId()+");");
            query.executeUpdate();
        }
    }

    //Retorna id do relato de atraso
    public int getAtrasoId() throws SQLException {

        int retorno = 0;

        try (Connection con = DriverManager.getConnection(condb.getUrl(), "root", "123abc");) {
            PreparedStatement query = con.prepareStatement("SELECT id_atraso FROM atraso WHERE id_aluno = '" + aluno.getAlunoId() + "' AND id_func = '" + funcionario.getFuncionarioId() + "' AND data_atraso = '" + data+";");
            ResultSet rSet = query.executeQuery();

            rSet.next();
            retorno = rSet.getInt(1);
            
            con.close();
            return retorno;
        }
    }

    
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
