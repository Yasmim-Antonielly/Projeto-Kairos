package dao;

import exception.EntidadeNaoEncontradaException;
import factory.ConnectionFactory;
import model.Projeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDao {

    private Connection conexao;

    public ProjetoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrar(Projeto projeto) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "INSERT INTO kairos_projeto (id, titulo, relato, orcamento, status, ong_id) " +
                        "VALUES (kairos_projeto_seq.NEXTVAL, ?, ?, ?, ?, ?)",
                new String[]{"id"});
        stm.setString(1, projeto.getTitulo());
        stm.setString(2, projeto.getRelato());
        stm.setDouble(3, projeto.getOrcamento());
        stm.setString(4, projeto.getStatus());
        stm.setLong(5, projeto.getOngId());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next())
            projeto.setId(generatedKeys.getLong(1));
    }

    public Projeto pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_projeto WHERE id = ?");
        stm.setLong(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next())
            throw new EntidadeNaoEncontradaException("Projeto não encontrado");
        return mapear(result);
    }

    public List<Projeto> listar() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_projeto");
        ResultSet result = stm.executeQuery();
        List<Projeto> lista = new ArrayList<>();
        while (result.next())
            lista.add(mapear(result));
        return lista;
    }

    public List<Projeto> listarPorOng(long ongId) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_projeto WHERE ong_id = ?");
        stm.setLong(1, ongId);
        ResultSet result = stm.executeQuery();
        List<Projeto> lista = new ArrayList<>();
        while (result.next())
            lista.add(mapear(result));
        return lista;
    }

    public List<Projeto> listarPorStatus(String status) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement(
                "SELECT * FROM kairos_projeto WHERE LOWER(status) = LOWER(?)");
        stm.setString(1, status);
        ResultSet result = stm.executeQuery();
        List<Projeto> lista = new ArrayList<>();
        while (result.next())
            lista.add(mapear(result));
        return lista;
    }

    public void atualizar(Projeto projeto) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "UPDATE kairos_projeto SET titulo = ?, relato = ?, orcamento = ?, " +
                        "status = ?, ong_id = ? WHERE id = ?");
        stm.setString(1, projeto.getTitulo());
        stm.setString(2, projeto.getRelato());
        stm.setDouble(3, projeto.getOrcamento());
        stm.setString(4, projeto.getStatus());
        stm.setLong(5, projeto.getOngId());
        stm.setLong(6, projeto.getId());
        int linhas = stm.executeUpdate();
        if (linhas == 0)
            throw new EntidadeNaoEncontradaException("Projeto não encontrado para ser atualizado");
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement(
                "DELETE FROM kairos_projeto WHERE id = ?");
        stm.setLong(1, id);
        int linhas = stm.executeUpdate();
        if (linhas == 0)
            throw new EntidadeNaoEncontradaException("Projeto não encontrado para ser removido");
    }

    private Projeto mapear(ResultSet result) throws SQLException {
        Projeto p = new Projeto();
        p.setId(result.getLong("id"));
        p.setTitulo(result.getString("titulo"));
        p.setRelato(result.getString("relato"));
        p.setOrcamento(result.getDouble("orcamento"));
        p.setStatus(result.getString("status"));
        p.setOngId(result.getLong("ong_id"));
        return p;
    }
}

