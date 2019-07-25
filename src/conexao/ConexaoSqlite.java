package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSqlite {
	
	private Connection conexao;
	//Conecta ao banco de dados e também cria um banco se ele não existir.
	public boolean conectar() {
		try {
			//O banco vai ficar localizado na pasta do projeto, na pasta especificada
			//Caso o banco não exista ele vai ser criado automaticamente
			//Não se esqueça de importar o driver do SQLite
			String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";
			this.conexao = DriverManager.getConnection(url);
		}catch(SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		System.out.println("Conetado com Sucesso!");
		return true;
	}
	
	public boolean desconectar() {
		try {
			if(this.conexao.isClosed() == false) { //Se a conexão estiver aberta
				this.conexao.close();
			} 
		}catch(SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		System.out.println("Desconetado com Sucesso!");
		return true;	
	}
	
	//Criação do Statement para "executar" SQLs
	public Statement criarStatement() {
		try {
			return this.conexao.createStatement();
		}catch(SQLException e){
			System.err.println(e.getMessage());
			return null;
		}
	}
	public PreparedStatement criarPreparedStatement(String sql) {
		try {
			return this.conexao.prepareStatement(sql);
		}catch(SQLException e){
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	
	public Connection getConexao() {
		return this.conexao;
	}
}
