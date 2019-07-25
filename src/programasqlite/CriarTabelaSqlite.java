package programasqlite;
import java.sql.Statement;
import java.sql.SQLException;

import conexao.ConexaoSqlite;
//Classe para criar todas as minhas tabelas
public class CriarTabelaSqlite {
	private final ConexaoSqlite  conexaoSqlite;
	
	public CriarTabelaSqlite(ConexaoSqlite pConexaoSqlite) { 
		
		this.conexaoSqlite = pConexaoSqlite;
	}
	public void criarTabelaPessoa() {
		String sql = "CREATE TABLE IF NOT EXISTS tbl_pessoa"
				+"("
				+"id integer PRIMARY KEY,"
				+"nome text NOT NULL,"
				+"idade integer"
				+");";
		//Executando o sql 
		boolean conectou = false;
		try {
			conectou = this.conexaoSqlite.conectar();
			Statement stmt = this.conexaoSqlite.criarStatement();
			stmt.execute(sql);
			System.out.println("Tabela Pessoa Criada");
		}catch(SQLException e){
			System.out.println("Erro ao criar tabela");
		}finally {
			if(conectou) {
				this.conexaoSqlite.desconectar();
			}
		}
	}
}
