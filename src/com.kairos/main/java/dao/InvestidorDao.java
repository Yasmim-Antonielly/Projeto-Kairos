package dao;

import factory.ConnectionFactory;
import model.Investidor;
import exception.EntidadeNaoEncontradaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestidorDao {

    private Connection conexao;

    public InvestidorDao() throws SQLException {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public void cadastrar(Investidor investidor) throws SQLException {
        String sql = "INSERT INTO KAIROS_INVESTIDOR (ID, NOME, TIPO_INVESTIDOR, TESE_INVESTIMENTO, CRITERIOS_ESG, ORCAMENTO_DISPONIVEL, CNPJ_CPF) " +
                     "VALUES (KAIROS_INVESTIDOR_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql, new String[]{"ID"});
        stm.setString(1, investidor.getNome());
        stm.setString(2, investidor.getTipoInvestidor());
        stm.setString(3, investidor.getTeseInvestimento());
        stm.setString(4, investidor.getCriteriosEsg());
        stm.setDouble(5, investidor.getOrcamentoDisponivel());
        stm.setString(6, investidor.getCnpjCpf());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            investidor.setId(generatedKeys.getLong(1));
        }
    }

    public Investidor pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM KAIROS_INVESTIDOR WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Investidor não encontrado com ID: " + id);
        }
        return mapear(result);
    }

    public List<Investidor> listar() throws SQLException {
        String sql = "SELECT * FROM KAIROS_INVESTIDOR";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<Investidor> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(mapear(result));
        }
        return lista;
    }

    public void atualizar(Investidor investidor) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE KAIROS_INVESTIDOR SET NOME = ?, TIPO_INVESTIDOR = ?, TESE_INVESTIMENTO = ?, " +
                     "CRITERIOS_ESG = ?, ORCAMENTO_DISPONIVEL = ?, CNPJ_CPF = ? WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, investidor.getNome());
        stm.setString(2, investidor.getTipoInvestidor());
        stm.setString(3, investidor.getTeseInvestimento());
        stm.setString(4, investidor.getCriteriosEsg());
        stm.setDouble(5, investidor.getOrcamentoDisponivel());
        stm.setString(6, investidor.getCnpjCpf());
        stm.setLong(7, investidor.getId());

        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Investidor não encontrado para atualização.");
        }
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM KAIROS_INVESTIDOR WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Investidor não encontrado para exclusão.");
        }
    }

    private Investidor mapear(ResultSet result) throws SQLException {
        return new Investidor(
                result.getLong("ID"),
                result.getString("NOME"),
                result.getString("TIPO_INVESTIDOR"),
                result.getString("TESE_INVESTIMENTO"),
                result.getString("CRITERIOS_ESG"),
                result.getDouble("ORCAMENTO_DISPONIVEL"),
                result.getString("CNPJ_CPF")
        );
    }
}
