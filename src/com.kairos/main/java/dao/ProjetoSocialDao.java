package dao;

import factory.ConnectionFactory;
import model.ProjetoSocial;
import exception.EntidadeNaoEncontradaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoSocialDao {

    private Connection conexao;

    public ProjetoSocialDao() throws SQLException {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public void cadastrar(ProjetoSocial projeto) throws SQLException {
        String sql = "INSERT INTO KAIROS_PROJETO_SOCIAL (ID, TITULO, DESCRICAO, CATEGORIA_ESG, ORCAMENTO_NECESSARIO, ONG_RESPONSAVEL, CNPJ, METAS_COMUNIDADE) " +
                     "VALUES (KAIROS_PROJ_SOCIAL_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql, new String[]{"ID"});
        stm.setString(1, projeto.getTitulo());
        stm.setString(2, projeto.getDescricao());
        stm.setString(3, projeto.getCategoriaEsg());
        stm.setDouble(4, projeto.getOrcamentoNecessario());
        stm.setString(5, projeto.getOngResponsavel());
        stm.setString(6, projeto.getCnpj());
        stm.setString(7, projeto.getMetasComunidade());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            projeto.setId(generatedKeys.getLong(1));
        }
    }

    public ProjetoSocial pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM KAIROS_PROJETO_SOCIAL WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Projeto Social não encontrado com ID: " + id);
        }
        return mapear(result);
    }

    public List<ProjetoSocial> listar() throws SQLException {
        String sql = "SELECT * FROM KAIROS_PROJETO_SOCIAL";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<ProjetoSocial> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(mapear(result));
        }
        return lista;
    }

    public void atualizar(ProjetoSocial projeto) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE KAIROS_PROJETO_SOCIAL SET TITULO = ?, DESCRICAO = ?, CATEGORIA_ESG = ?, ORCAMENTO_NECESSARIO = ?, " +
                     "ONG_RESPONSAVEL = ?, CNPJ = ?, METAS_COMUNIDADE = ? WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, projeto.getTitulo());
        stm.setString(2, projeto.getDescricao());
        stm.setString(3, projeto.getCategoriaEsg());
        stm.setDouble(4, projeto.getOrcamentoNecessario());
        stm.setString(5, projeto.getOngResponsavel());
        stm.setString(6, projeto.getCnpj());
        stm.setString(7, projeto.getMetasComunidade());
        stm.setLong(8, projeto.getId());
        
        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Projeto Social não encontrado para atualização.");
        }
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM KAIROS_PROJETO_SOCIAL WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Projeto Social não encontrado para exclusão.");
        }
    }

    private ProjetoSocial mapear(ResultSet result) throws SQLException {
        return new ProjetoSocial(
                result.getLong("ID"),
                result.getString("TITULO"),
                result.getString("DESCRICAO"),
                result.getString("CATEGORIA_ESG"),
                result.getDouble("ORCAMENTO_NECESSARIO"),
                result.getString("ONG_RESPONSAVEL"),
                result.getString("CNPJ"),
                result.getString("METAS_COMUNIDADE")
        );
    }
}
