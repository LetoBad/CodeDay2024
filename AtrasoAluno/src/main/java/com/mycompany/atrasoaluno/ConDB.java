package com.mycompany.atrasoaluno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConDB {

    // Métodos que buscam as informações do banco

    ArrayList<Aluno> arrayAluno = new ArrayList<>();
    ArrayList<Turma> arrayTurma = new ArrayList<>();
    ArrayList<Funcionario> arrayProfessor = new ArrayList<>();
    ArrayList<Funcionario> arrayFuncionario = new ArrayList<>();
    ArrayList<Atraso> arrayAtraso = new ArrayList<>();

    private String url = "jdbc:mysql://localhost:3306/mydb";

    public String getUrl() {
        return url;
    }

    public Aluno returnAluno(int idAluno) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            Aluno aluno = null;

            PreparedStatement query = con.prepareStatement("SELECT * FROM aluno WHERE id_aluno = " + idAluno + ";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                aluno = new Aluno(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5));
            }
            con.close();
            return aluno;
        }
    }

    public Funcionario returnFuncionario(int idFunc) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            Funcionario funcionario = null;

            PreparedStatement query = con.prepareStatement("SELECT * FROM funcionario WHERE id_func = " + idFunc + ";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                funcionario = new Funcionario(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5));
            }
            con.close();
            return funcionario;
        }
    }

    // Esvazia o arrayAluno e Preenche ele com todos os alunos
    public void returnTodosAlunos() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            Aluno aluno;

            PreparedStatement query = con.prepareStatement("SELECT * FROM aluno");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayAluno.add(
                        aluno = new Aluno(rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getInt(5)));
            }
            con.close();
        }
    }

    // Esvazia o arrayAluno e Preenche com todos os alunos de uma determinada turma
    public void returnAlunosPorTurma(String nome_turma) throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            int idTurma = -1;

            PreparedStatement query = con
                    .prepareStatement("SELECT id_turma FROM turma WHERE nome_turma LIKE '" + nome_turma + "';");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                idTurma = rSet.getInt("id_turma");
            }
            Aluno aluno;

            PreparedStatement query2 = con.prepareStatement("SELECT * FROM aluno WHERE id_turma = " + idTurma + ";");
            ResultSet rSet2 = query2.executeQuery();
            while (rSet2.next()) {
                arrayAluno.add(
                        aluno = new Aluno(rSet2.getString(2), rSet2.getString(3), rSet2.getString(4), rSet2.getInt(5)));
            }
            con.close();
        }
    }

    // Esvazia o arrayAluno e Preenche ele com todos os alunos
    public void returnTodasTurmas() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

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
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            int idTurma = -1;

            PreparedStatement query1 = con
                    .prepareStatement("SELECT id_turma FROM turma WHERE nome_turma LIKE '" + nome_turma + "';");
            ResultSet rSet1 = query1.executeQuery();
            while (rSet1.next()) {
                idTurma = rSet1.getInt("id_turma");
            }

            Funcionario professor;

            PreparedStatement query = con.prepareStatement(
                    "SELECT * FROM funcionario f INNER JOIN turma_funcionario tf ON f.id_func = tf.id_func WHERE id_tipofunc = 1 AND id_turma = "
                            + idTurma + "");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayProfessor.add(professor = new Funcionario(rSet.getString(2), rSet.getString(3), rSet.getString(4),
                        rSet.getInt(5)));
            }
            con.close();
        }
    }

    public void returnTodosFuncionarios() throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            Funcionario funcionario;

            PreparedStatement query = con.prepareStatement("SELECT * FROM funcionario WHERE id_tipofunc = 2");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayFuncionario.add(funcionario = new Funcionario(rSet.getString(2), rSet.getString(3),
                        rSet.getString(4), rSet.getInt(5)));
            }
            con.close();
        }
    }

    public void returnTodosAtrasosAluno(String nomeAluno) throws SQLException {
        arrayAluno.clear();
        try (Connection con = DriverManager.getConnection(url, "root", "leto");) {

            Atraso atraso;

            int idAluno = -1;

            PreparedStatement query1 = con
                    .prepareStatement("SELECT id_aluno FROM aluno WHERE nome_aluno LIKE '" + nomeAluno + "';");
            ResultSet rSet1 = query1.executeQuery();
            while (rSet1.next()) {
                idAluno = rSet1.getInt("id_aluno");
            }

            PreparedStatement query = con.prepareStatement("SELECT * FROM atraso WHERE id_aluno = " + idAluno + ";");
            ResultSet rSet = query.executeQuery();
            while (rSet.next()) {
                arrayAtraso.add(atraso = new Atraso(returnAluno(rSet.getInt(5)), rSet.getString(2), rSet.getString(3),
                        rSet.getString(4), returnFuncionario(rSet.getInt(6))));
            }
            con.close();
        }
    }




    // Leto
    public void registrarAtraso(String nomeAluno, String nomeTurma, String nomeProfessor, String data, String hora,
            String razao) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, "root", "leto")) {

            // aqui es para obtener el ID del alumno
            int idAluno = getIdAlunoPorNome(nomeAluno);
            if (idAluno == -1) {
                System.out.println("Aluno não encontrado: " + nomeAluno);
                return;
            }

            // aqui es obtener el ID de la turma
            int idTurma = getIdTurmaPorNome(nomeTurma);
            if (idTurma == -1) {
                System.out.println("Turma não encontrada: " + nomeTurma);
                return;
            }

            // aqui es obtener el ID del profesor
            int idProfessor = getIdProfessorPorNome(nomeProfessor);
            if (idProfessor == -1) {
                System.out.println("Professor não encontrado: " + nomeProfessor);
                return;
            }

            // aqui registramos el atraso en la base de datos
            String sql = "INSERT INTO atraso (data_atraso, hora_atraso, justificativa, id_aluno, id_func) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement query = con.prepareStatement(sql)) {
                query.setString(1, data);
                query.setString(2, hora);
                query.setString(3, razao);
                query.setInt(4, idAluno);
                query.setInt(5, idProfessor);
                query.executeUpdate();
                System.out.println("Atraso registrado com sucesso!");
            }
        }
    }

    // aca son metodos auxiliares para obtener IDs
    private int getIdAlunoPorNome(String nomeAluno) throws SQLException {
        String sql = "SELECT id_aluno FROM aluno WHERE nome_aluno = ?";
        try (Connection con = DriverManager.getConnection(url, "root", "leto");
                PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, nomeAluno);
            ResultSet rSet = query.executeQuery();
            if (rSet.next()) {
                return rSet.getInt("id_aluno");
            }
            return -1; // Retorna -1 si no se encuentra el alumno
        }
    }

    private int getIdTurmaPorNome(String nomeTurma) throws SQLException {
        String sql = "SELECT id_turma FROM turma WHERE nome_turma = ?";
        try (Connection con = DriverManager.getConnection(url, "root", "leto");
                PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, nomeTurma);
            ResultSet rSet = query.executeQuery();
            if (rSet.next()) {
                return rSet.getInt("id_turma");
            }
            return -1; // Retorna -1 si no se encuentra la turma
        }
    }

    private int getIdProfessorPorNome(String nomeProfessor) throws SQLException {
        String sql = "SELECT id_func FROM funcionario WHERE nome_func = ? AND id_tipofunc = 1"; // Asegura que sea un profesor
        try (Connection con = DriverManager.getConnection(url, "root", "leto");
                PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, nomeProfessor);
            ResultSet rSet = query.executeQuery();
            if (rSet.next()) {
                return rSet.getInt("id_func");
            }
            return -1; // Retorna -1 si no se encuentra el profesor
        }
    }

    public void obterHistoricoAtrasos(String nomeAluno) throws SQLException {
        int idAluno = getIdAlunoPorNome(nomeAluno);
        if (idAluno == -1) {
            System.out.println("Aluno não encontrado: " + nomeAluno);
            return;
        }

        String sql = "SELECT atraso.id_atraso, atraso.data_atraso, atraso.hora_atraso, atraso.justificativa, funcionario.nome_func "
                +
                "FROM atraso " +
                "JOIN funcionario ON atraso.id_func = funcionario.id_func " +
                "WHERE atraso.id_aluno = ?";
        try (Connection con = DriverManager.getConnection(url, "root", "leto");
                PreparedStatement query = con.prepareStatement(sql)) {
            query.setInt(1, idAluno);
            ResultSet rSet = query.executeQuery();

            System.out.println("Histórico de Atrasos para " + nomeAluno + ":");
            while (rSet.next()) {
                int idAtraso = rSet.getInt("id_atraso");
                String data = rSet.getString("data_atraso");
                String hora = rSet.getString("hora_atraso");
                String justificativa = rSet.getString("justificativa");
                String nomeProfessor = rSet.getString("nome_func");

                System.out.println("ID do Atraso: " + idAtraso);
                System.out.println("Data: " + data);
                System.out.println("Hora: " + hora);
                System.out.println("Justificativa: " + justificativa);
                System.out.println("Professor: " + nomeProfessor);
                System.out.println("---------------");
            }
        }
    }

    // aqui es un metodo para editar um atraso específico
    public void editarAtraso(int idAtraso, String novaData, String novaHora, String novaJustificativa)
            throws SQLException {
        String sql = "UPDATE atraso SET data_atraso = ?, hora_atraso = ?, justificativa = ? WHERE id_atraso = ?";
        try (Connection con = DriverManager.getConnection(url, "root", "leto");
                PreparedStatement query = con.prepareStatement(sql)) {
            query.setString(1, novaData);
            query.setString(2, novaHora);
            query.setString(3, novaJustificativa);
            query.setInt(4, idAtraso);
            int linhasAfetadas = query.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Atraso atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar o atraso. Verifique o ID.");
                
            }
        }
    }

}
