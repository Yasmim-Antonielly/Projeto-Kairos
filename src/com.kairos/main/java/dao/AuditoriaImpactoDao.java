package dao;

import factory.ConnectionFactory;
import model.AuditoriaImpacto;
import exception.EntidadeNaoEncontradaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaImpactoDao {

    private Connection conexao;

    public AuditoriaImpactoDao() throws SQLException {
        this.conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public void cadastrar(AuditoriaImpacto auditoria) throws SQLException {
        String sql = "INSERT INTO KAIROS_AUDITORIA_IMPACTO (ID, PROJETO_SOCIAL_ID, VALOR_INVESTIDO, VALOR_RETORNO_SOCIAL, " +
                     "INDICE_SROI, CAMINHO_RECIBOS, CAMINHO_FOTOS, DADOS_BRUTOS_TEXTO, ANALISE_IA, STATUS_AUDITORIA, DATA_AUDITORIA) " +
                     "VALUES (KAIROS_AUDITORIA_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
        PreparedStatement stm = conexao.prepareStatement(sql, new String[]{"ID"});
        stm.setLong(1, auditoria.getProjetoSocialId());
        stm.setDouble(2, auditoria.getValorInvestido());
        stm.setDouble(3, auditoria.getValorRetornoSocial());
        stm.setDouble(4, auditoria.getIndiceSroi());
        stm.setString(5, auditoria.getCaminhoRecibos());
        stm.setString(6, auditoria.getCaminhoFotos());
        stm.setString(7, auditoria.getDadosBrutosTexto());
        stm.setString(8, auditoria.getAnaliseIa());
        stm.setString(9, auditoria.getStatusAuditoria());
        stm.executeUpdate();

        ResultSet generatedKeys = stm.getGeneratedKeys();
        if (generatedKeys.next()) {
            auditoria.setId(generatedKeys.getLong(1));
        }
        
        // Fetch generated system date
        String selectSql = "SELECT DATA_AUDITORIA FROM KAIROS_AUDITORIA_IMPACTO WHERE ID = ?";
        PreparedStatement selectStm = conexao.prepareStatement(selectSql);
        selectStm.setLong(1, auditoria.getId());
        ResultSet result = selectStm.executeQuery();
        if (result.next()) {
            auditoria.setDataAuditoria(result.getDate("DATA_AUDITORIA"));
        }
    }

    public AuditoriaImpacto pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM KAIROS_AUDITORIA_IMPACTO WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        ResultSet result = stm.executeQuery();
        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Auditoria de Impacto não encontrada com ID: " + id);
        }
        return mapear(result);
    }

    public List<AuditoriaImpacto> listar() throws SQLException {
        String sql = "SELECT * FROM KAIROS_AUDITORIA_IMPACTO";
        PreparedStatement stm = conexao.prepareStatement(sql);
        ResultSet result = stm.executeQuery();
        List<AuditoriaImpacto> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(mapear(result));
        }
        return lista;
    }

    public List<AuditoriaImpacto> listarPorProjeto(long projetoSocialId) throws SQLException {
        String sql = "SELECT * FROM KAIROS_AUDITORIA_IMPACTO WHERE PROJETO_SOCIAL_ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, projetoSocialId);
        ResultSet result = stm.executeQuery();
        List<AuditoriaImpacto> lista = new ArrayList<>();
        while (result.next()) {
            lista.add(mapear(result));
        }
        return lista;
    }

    public void atualizar(AuditoriaImpacto auditoria) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE KAIROS_AUDITORIA_IMPACTO SET PROJETO_SOCIAL_ID = ?, VALOR_INVESTIDO = ?, VALOR_RETORNO_SOCIAL = ?, " +
                     "INDICE_SROI = ?, CAMINHO_RECIBOS = ?, CAMINHO_FOTOS = ?, DADOS_BRUTOS_TEXTO = ?, ANALISE_IA = ?, " +
                     "STATUS_AUDITORIA = ? WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, auditoria.getProjetoSocialId());
        stm.setDouble(2, auditoria.getValorInvestido());
        stm.setDouble(3, auditoria.getValorRetornoSocial());
        stm.setDouble(4, auditoria.getIndiceSroi());
        stm.setString(5, auditoria.getCaminhoRecibos());
        stm.setString(6, auditoria.getCaminhoFotos());
        stm.setString(7, auditoria.getDadosBrutosTexto());
        stm.setString(8, auditoria.getAnaliseIa());
        stm.setString(9, auditoria.getStatusAuditoria());
        stm.setLong(10, auditoria.getId());

        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Auditoria de Impacto não encontrada para atualização.");
        }
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM KAIROS_AUDITORIA_IMPACTO WHERE ID = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, id);
        int linhas = stm.executeUpdate();
        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Auditoria de Impacto não encontrada para exclusão.");
        }
    }

    private AuditoriaImpacto mapear(ResultSet result) throws SQLException {
        return new AuditoriaImpacto(
                result.getLong("ID"),
                result.getLong("PROJETO_SOCIAL_ID"),
                result.getDouble("VALOR_INVESTIDO"),
                result.getDouble("VALOR_RETORNO_SOCIAL"),
                result.getDouble("INDICE_SROI"),
                result.getString("CAMINHO_RECIBOS"),
                result.getString("CAMINHO_FOTOS"),
                result.getString("DADOS_BRUTOS_TEXTO"),
                result.getString("ANALISE_IA"),
                result.getString("STATUS_AUDITORIA"),
                result.getDate("DATA_AUDITORIA")
        );
    }
}
