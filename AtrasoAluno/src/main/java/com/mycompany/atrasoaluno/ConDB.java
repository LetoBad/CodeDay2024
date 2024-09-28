package com.mycompany.atrasoaluno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConDB {
    
    //Métodos que buscam as informações do banco
    
    ArrayList<Aluno> arrayAluno = new ArrayList<>();
    ArrayList<Turma> arrayTurma = new ArrayList<>();
    ArrayList<Funcionario> arrayProfessor = new ArrayList<>();
    ArrayList<Funcionario> arrayFuncionario = new ArrayList<>();
    ArrayList<Atraso> arrayAtraso = new ArrayList<>();
    
    private String url = "jdbc:mysql://localhost:3306/mydb";

    public String getUrl() {
        return url;
    }
    
    public Aluno returnAluno(int idAluno) throws SQLException{
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Aluno aluno = null;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM aluno WHERE id_aluno = "+idAluno+";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                aluno = new Aluno(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5));
            }
            con.close();
            return aluno;
        }
    }
    
    
    public Funcionario returnFuncionario(int idFunc) throws SQLException{
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Funcionario funcionario = null ;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM funcionario WHERE id_func = "+idFunc+";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                funcionario = new Funcionario(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5));
            }
            con.close();
            return funcionario;
        }
    }
    
    //Esvazia o arrayAluno e Preenche ele com todos os alunos
    public void returnTodosAlunos() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Aluno aluno;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM aluno");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayAluno.add(aluno = new Aluno(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5)));
            }
            con.close();
        }
    }
    
    //Esvazia o arrayAluno e Preenche com todos os alunos de uma determinada turma
    public void returnAlunosPorTurma(String nome_turma) throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            int idTurma = -1;
            
            PreparedStatement query = con.prepareStatement("SELECT id_turma FROM turma WHERE nome_turma LIKE '"+nome_turma+"';");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                idTurma = rSet.getInt("id_turma");
            }
            Aluno aluno;
            
            PreparedStatement query2 = con.prepareStatement("SELECT * FROM aluno WHERE id_turma = "+idTurma+";");
            ResultSet rSet2 = query2.executeQuery();
            while (rSet2.next()) {
                arrayAluno.add(aluno = new Aluno(rSet2.getString(2), rSet2.getString(3), rSet2.getString(4), rSet2.getInt(5)));
            }
            con.close();
        }
    }
    
    //Esvazia o arrayAluno e Preenche ele com todos os alunos
    public void returnTodasTurmas() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Turma turma;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM turma");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayTurma.add(turma = new Turma(rSet.getString(2)));
            }
            con.close();
        }
    }
    
    public void returnProfessoresPorTurma(String nome_turma) throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            int idTurma = -1;
            
            PreparedStatement query1 = con.prepareStatement("SELECT id_turma FROM turma WHERE nome_turma LIKE '"+nome_turma+"';");
            ResultSet rSet1 = query1.executeQuery();
            while (rSet1.next()) {
                idTurma = rSet1.getInt("id_turma");
            }
            
            Funcionario professor;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM funcionario f INNER JOIN turma_funcionario tf ON f.id_func = tf.id_func WHERE id_tipofunc = 1 AND id_turma = "+idTurma+"");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayProfessor.add(professor = new Funcionario(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5)));
            }
            con.close();
        }
    }
    
    public void returnTodosFuncionarios() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Funcionario funcionario;
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM funcionario WHERE id_tipofunc = 2");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayFuncionario.add(funcionario = new Funcionario(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5)));
            }
            con.close();
        }
    }
    
    
    public void returnTodosAtrasosAluno(String nomeAluno) throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "123abc");) {
            
            Atraso atraso;
            
            int idAluno = -1;
            
            PreparedStatement query1 = con.prepareStatement("SELECT id_aluno FROM aluno WHERE nome_aluno LIKE '"+nomeAluno+"';");
            ResultSet rSet1 = query1.executeQuery();
            while (rSet1.next()) {
                idAluno = rSet1.getInt("id_aluno");
            }
            
            
            PreparedStatement query = con.prepareStatement("SELECT * FROM atraso WHERE id_aluno = "+idAluno+";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayAtraso.add(atraso = new Atraso(returnAluno(rSet.getInt(5)), rSet.getString(2), rSet.getString(3), rSet.getString(4), returnFuncionario(rSet.getInt(6))));
            }
            con.close();
        }
    }
}
