package lp2a4.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AlunoJDBCDAO implements AlunoDAO{
	
	//Método que retorna uma conexão com o banco de dados
	public static Connection conectaMySQL() {		
		try {
			//Linha necessária para registrar o driver do mysql.
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			//String de conexão. Trocar "databasename, username, password" pelos dados de seu banco de dados.
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/databasename"
					, "username"
					, "password");
			return conn;
		} catch (SQLException e) {
			System.out.println("Erro! Não foi possível conectar com o banco de dados: " + e);
			return null;
		}
		
	}
	
	@Override
	public boolean create(AlunoPOJO aluno) {
		//Monta a instrução
		String instrucaoSQL = "INSERT INTO Aluno (matricula, nome, endereco, data_inicio, data_termino) VALUES (?,?,?,?,?)";
			
		PreparedStatement statement;
		
		try {
			//Prepara a execução da instrução e substitui os "?" pelas strings respectivas.
			statement = AlunoJDBCDAO.conectaMySQL().prepareStatement(instrucaoSQL);
			statement.setString(1, aluno.getMatricula());
			statement.setString(2, aluno.getNome());
			statement.setString(3, aluno.getEndereco());
			statement.setString(4, aluno.getDataIngresso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
			statement.setString(5, aluno.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
			//Executa a instrução
			statement.execute();
			//Fecha a instrução e a conexão com o banco de dados.
			statement.close();
			AlunoJDBCDAO.conectaMySQL().close();
			return true;
		} catch (SQLException e) {
			System.out.println("Erro! Não foi possível realizar a inserção: " + e);
			return false;
		}
	}

	@Override
	public AlunoPOJO retrieve(String matricula) {
		try {
			//Mesmos procedimentos do método create, mas monta um objeto Aluno para retornar ao controller.
			AlunoPOJO aluno = new AlunoPOJO();
			PreparedStatement statement = conectaMySQL().prepareStatement("SELECT matricula,nome,endereco,data_inicio,data_termino FROM Aluno WHERE matricula = ?;");
			statement.setString(1, matricula);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String nome = result.getString("nome");
				String endereco = result.getString("endereco");
				String dtIngresso = result.getString("data_inicio");
				String dtConclusao = result.getString("data_termino");
				aluno.setMatricula(matricula);
				aluno.setNome(nome);
				aluno.setEndereco(endereco);
				aluno.setDataIngresso(LocalDate.parse(dtIngresso, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				aluno.setDataConclusao(LocalDate.parse(dtConclusao, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			}
			statement.close();
			AlunoJDBCDAO.conectaMySQL().close();
			return aluno;
		} catch(Exception e) {
			System.out.println("Erro! Não foi possível consultar os dados." + e);
			return null;
		}
		
	}

	@Override
	public boolean update(AlunoPOJO aluno) {
		//Idêntico ao método create, com mudança apenas na instrução.
		String instrucaoSQL = "UPDATE Aluno SET matricula = ?, nome = ?, endereco = ?, data_inicio = ?, data_termino = ? WHERE matricula = ?;";
		
		PreparedStatement statement;
		
		try {
			statement = AlunoJDBCDAO.conectaMySQL().prepareStatement(instrucaoSQL);
			statement.setString(1, aluno.getMatricula());
			statement.setString(2, aluno.getNome());
			statement.setString(3, aluno.getEndereco());
			statement.setString(4, aluno.getDataIngresso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
			statement.setString(5, aluno.getDataConclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
			statement.setString(6, aluno.getMatricula());
			statement.execute();
			statement.close();
			AlunoJDBCDAO.conectaMySQL().close();
			return true;
		} catch (SQLException e) {
			System.out.println("Erro! Não foi possível realizar a atualização: " + e);
			return false;
		}
	}

	@Override
	public boolean delete(String matricula) {
		try {
			PreparedStatement statement = AlunoJDBCDAO.conectaMySQL().prepareStatement("DELETE FROM Aluno WHERE matricula = ?;");
			statement.setString(1, matricula);
			statement.execute();
			statement.close();
			AlunoJDBCDAO.conectaMySQL().close();
			return true;
		} catch (SQLException e) {
			System.out.println("Erro! Não foi possível realizar a deleção: " + e);
			return false;
		}
		
	}
}
