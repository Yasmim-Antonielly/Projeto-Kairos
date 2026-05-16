package dao;

import dto.CadastroOngDto;
import exception.EntidadeNaoEncontradaException;
import factory.ConnectionFactory;
import model.Ong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OngDao {

    private Connection conexao;

    public OngDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrar(Ong ong) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "INSERT INTO kairos_ong (id, nome, descricao, area_atuacao, cnpj) " +
                        "VALUES (kairos_ong_seq.NEXTVAL, ?, ?, ?, ?)",
                new String[]{"id"});
        stm.setString(1, ong.getNome());
        stm.setString(2, ong.getDescricao());
        stm.setString(3, ong.getAreaAtuacao());
        stm.setString(4, ong.getCnpj());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next())
            ong.setId(Long.valueOf(String.valueOf(generatedKeys.getLong(1))));
    }

    public Ong pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_ong WHERE id = ?");
        stm.setLong(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEncontradaException("ONG não encontrada");
        return mapear(result);
    }

    public List<Ong> listar() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_ong");
        ResultSet result = stm.executeQuery();
        List<Ong> lista = new ArrayList<>();
        while (result.next())
            lista.add(mapear(result));
        return lista;
    }

    public List<Ong> listarPorArea(String area) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_ong WHERE LOWER(area_atuacao) LIKE LOWER(?)");
        stm.setString(1, "%" + area + "%");
        ResultSet result = stm.executeQuery();
        List<Ong> lista = new ArrayList<>();
        while (result.next())
            lista.add(mapear(result));
        return lista;
    }

    public void atualizar(Ong ong) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "UPDATE kairos_ong SET nome = ?, descricao = ?, area_atuacao = ?, cnpj = ? " +
                        "WHERE id = ?");
        stm.setString(1, ong.getNome());
        stm.setString(2, ong.getDescricao());
        stm.setString(3, ong.getAreaAtuacao());
        stm.setString(4, ong.getCnpj());
        stm.setLong(5, ong.getId());
        int linhas = stm.executeUpdate();
        if (linhas == 0)
            throw new EntidadeNaoEncontradaException("ONG não encontrada para ser atualizada");
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "DELETE FROM kairos_ong WHERE id = ?");
        stm.setLong(1, id);
        int linhas = stm.executeUpdate();
        if (linhas == 0)
            throw new EntidadeNaoEncontradaException("ONG não encontrada para ser removida");
    }

    private Ong mapear(ResultSet result) throws SQLException {
        long id             = result.getLong("id");
        String nome         = result.getString("nome");
        String descricao    = result.getString("descricao");
        String areaAtuacao  = result.getString("area_atuacao");
        String cnpj         = result.getString("cnpj");
        return new Ong(id, nome, descricao, areaAtuacao, cnpj);
    }
}
