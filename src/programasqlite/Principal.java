package programasqlite;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexao.ConexaoSqlite;

public class Principal {
	static Scanner ler = new Scanner(System.in);
	static ConexaoSqlite conexaoSqlite = new ConexaoSqlite();
	public static void escolha() {
		
		int escolha;
		do {
			System.out.println("");
			System.out.println("BEM VINDO!");
			System.out.println("1 - VER TODOS OS REGISTROS");
			System.out.println("2 - BUSCAR UM REGISTRO");
			System.out.println("3 - INSERIR UM REGISTRO");
			System.out.println("4 - ATUALIZAR UM REGISTRO");
			System.out.println("5 - DELETAR UM REGISTRO");
			System.out.println("6 - SAIR");
			escolha = ler.nextInt();
			switch(escolha) {
				case 1:
					selectGeral();
				case 2:
					busca();
				case 3: 
					insert();
				case 4:
					update();
				case 5: 
					delete();
				case 6: 
					System.exit(0);
				default:
					System.out.println("Opção Inválida");
			}
		}while(escolha != 6);
	}
	
	public static void main(String[] args) {
		escolha();
	}
	
	public static void selectGeral() {
		//Select geral 
		ResultSet resultSet = null; 
		java.sql.Statement statement = null;
		conexaoSqlite.conectar();
		
		String query = "SELECT * FROM tbl_pessoa";
		statement = conexaoSqlite.criarStatement();
		
		try {
			//Todos os dados so select esta dentro do ResultSet
			resultSet = statement.executeQuery(query); 
			while(resultSet.next()) { //Vai buscando os resultados
				System.out.println("DADOS DAS PESSOAS:");
				
				System.out.println("id = "    + resultSet.getInt("id"));
				System.out.println("Nome = "  + resultSet.getString("nome"));
				System.out.println("Idade = " + resultSet.getInt("idade"));
				
				System.out.println("**********************");
			}
		}catch(SQLException e) {
			System.out.println("Erro ao Realizar a Busca:" +e);
		}finally {
			try {
				if(resultSet.isClosed() != true) {
					resultSet.close();
				}
				if(statement.isClosed() != true) {
					statement.close();
				}
			}catch(SQLException e) {
				System.out.println("Erro ao fechar conexões" +e);
			}
			conexaoSqlite.desconectar();
			escolha();
		}		
	}
	public static void busca() {
		//Select Específico
		ResultSet resultSetE = null;
		PreparedStatement preparedStatement= null;
		conexaoSqlite.conectar();
		System.out.println("Digite o Id para Buscar os Dados:");
		int id = ler.nextInt();
		
		String query1 = "SELECT * FROM tbl_pessoa WHERE id = ?";
		try {
			//Utilizamos o método criado na classe conexao
			//PreparedStatement pois precisamos passar parâmetros SQl
			preparedStatement = conexaoSqlite.criarPreparedStatement(query1);
			preparedStatement.setInt(1, id);
			resultSetE = preparedStatement.executeQuery();
			
			while(resultSetE.next()) {
				System.out.println("DADOS DA PESSOA SELECIONADA:");
				System.out.println("id = "    + resultSetE.getInt("id"));
				System.out.println("Nome = "  + resultSetE.getString("nome"));
				System.out.println("Idade = " + resultSetE.getInt("idade"));
			}
		}catch(SQLException e) {
			System.out.println("Erro ao Buscar Dados: "+e);
		}finally {
			try {
				if(preparedStatement.isClosed() != true) {
					preparedStatement.close();
				}
				
				if(resultSetE.isClosed() != true) {
					resultSetE.close();
				}
			}catch(SQLException e1) {
				System.out.println("Erro ao Fechar Conexões" +e1);
			}
			conexaoSqlite.desconectar();
			escolha();
		}		
	}
	public static void insert() {
		//Insert
		//Criar uma pessoa
		Pessoa pessoa1 = new Pessoa();
		System.out.println("Digite o Id: ");
		int id = ler.nextInt();
		System.out.println("Digite o Nome: ");
		String nome = ler.next();
		System.out.println("Digite a Idade");
		int idade = ler.nextInt();
		pessoa1.setId(id);
		pessoa1.setNome(nome);
		pessoa1.setIdade(idade);
		
		conexaoSqlite.conectar();
		String sqlinsert = "INSERT INTO tbl_pessoa(id,nome,idade) VALUES(?,?,?)";
		PreparedStatement preparedStatement = conexaoSqlite.criarPreparedStatement(sqlinsert);
		try {
			preparedStatement.setInt(1, pessoa1.getId());
			preparedStatement.setString(2, pessoa1.getNome());
			preparedStatement.setInt(3, pessoa1.getIdade());
			
			int resultado = preparedStatement.executeUpdate();
			
			if(resultado == 1) {
				System.out.println("Insert Realizado com Sucesso!");
			}else {
				System.out.println("Erro no Insert");
			}
		}catch(SQLException e) {
			System.out.println("Erro no Insert: "+e);
		}finally {
			try {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			}catch(SQLException e) {
				System.out.println("Erro ao Fechar o PreparedStatement:" +e);
			}
			conexaoSqlite.desconectar();
			escolha();
		}		
	}
	public static void update() {
		//Update
		conexaoSqlite.conectar();
		System.out.println("Digite o Id do Registro:");
		int idnew = ler.nextInt();
		System.out.println("Digite o Novo Nome:");
		String nome = ler.next();
		System.out.println("Digite a Nova Idade:");
		int idade = ler.nextInt();
		
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE tbl_pessoa SET nome = ?, idade = ? WHERE id = ?";
		
		try {
			
			preparedStatement = conexaoSqlite.criarPreparedStatement(sql);
			preparedStatement.setString(1, nome);
			preparedStatement.setInt(2, idade);
			preparedStatement.setInt(3, idnew);
			preparedStatement.executeUpdate();
			System.out.println("Pessoa Atualizada");
		}catch(SQLException e){
			System.out.println("Erro ao Atualizar Dados: " +e);
		}finally{
			try {
				if(preparedStatement.isClosed() != true) {
					preparedStatement.close();
				}
			}catch(SQLException ex) {
				System.out.println("Erro ao Fechar Conexões" +ex);
			}
			conexaoSqlite.desconectar();
			escolha();
		}		
	}
	
	public static void delete() {
		conexaoSqlite.conectar();
		System.out.println("Digite o Id do Registro que Deseja Deletar: ");
		int id = ler.nextInt();
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM tbl_pessoa WHERE id = ?";
		try {
			preparedStatement = conexaoSqlite.criarPreparedStatement(sql);
			preparedStatement.setInt(1, id);
			int linhasDeletadas = preparedStatement.executeUpdate();
			System.out.println(linhasDeletadas+" Registro(s) Apagado(s)");
		}catch(SQLException e){
			System.out.println("Erro ao Deletar: "+e);
		}finally {
			try {
				if(preparedStatement.isClosed() != true) {
					preparedStatement.close();
				}
			}catch(SQLException e1){
				System.out.println("Erro ao Fechar Conexões: "+e1);
			}
			conexaoSqlite.desconectar();
			escolha();
		}
	}
}
