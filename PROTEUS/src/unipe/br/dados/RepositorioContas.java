package unipe.br.dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import unipe.br.contas.Conta;
import unipe.br.contas.ContaCorrente;

public class RepositorioContas implements IRepositorioContas {

	@Override
	public void inserir(Conta conta){
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;
		String sql = "insert into conta (numero,nome,saldo) values (?,?,?)";

		try {
			stmt = conn.prepareStatement(sql);
			int i = 0;
			stmt.setString(++i,conta.getConta());
			stmt.setString(++i,conta.getNome());
			stmt.setDouble(++i,conta.getSaldo());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					stmt.close();			
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Conta procura(Conta conta){
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Conta contaResult = new ContaCorrente();
		String sql = "select id, numero, nome, saldo from conta where numero = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, conta.getConta());

			rs = stmt.executeQuery();
			if(rs.next()){
				contaResult.setId(rs.getInt("id"));
				contaResult.setConta(rs.getString("numero"));
				contaResult.setNome(rs.getString("nome"));
				contaResult.setSaldo(rs.getDouble("saldo"));				
			}

		}catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return contaResult;
	}

	@Override
	public void atualizar(Conta conta){
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;
		String sql = "update conta set numero=?, nome=?, saldo=? where id=?";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, conta.getConta());
			stmt.setString(2, conta.getNome());
			stmt.setDouble(3, conta.getSaldo());
			stmt.setLong(4, conta.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					stmt.close();			
				if(conn != null)
					conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void remover(Conta conta){
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;

		String sql = "delete from conta where id = ?";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setLong(1, conta.getId());
			stmt.executeUpdate();   
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					stmt.close();			
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Conta> listar(String nome) {
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Conta> contas = new ArrayList<>();
		
		try {
			String sql = "select id, numero, nome, saldo from conta where LOWER(nome) ilike ? order by nome ASC";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+nome+"%");
			rs = stmt.executeQuery();	 

			while (rs.next()) {

				Conta conta = new ContaCorrente();

				conta.setId(rs.getInt("id"));
				conta.setConta(rs.getString("numero"));
				conta.setNome(rs.getString("nome"));
				conta.setSaldo(rs.getDouble("saldo"));

				contas.add(conta);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return contas;
	}
	
	@Override
	public boolean existe(Conta conta){
		Connection conn = new ConnectBank().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		boolean result;
		
		String sql = "select id from conta where numero = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, conta.getConta());

			rs = stmt.executeQuery();
			result = rs.next();
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				if(stmt != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}