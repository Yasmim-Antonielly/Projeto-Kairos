package service;

import dao.ProjetoDao;
import exception.EntidadeNaoEncontradaException;
import model.Projeto;
import model.RelatorioSroi;

import java.sql.SQLException;

public class SroiService {
    public RelatorioSroi gerarRelatorio(Long projetoId)
            throws SQLException, EntidadeNaoEncontradaException {

        ProjetoDao projetoDao = new ProjetoDao();
        try {
            Projeto projeto = projetoDao.pesquisar(projetoId);

            IaService iaService = new IaService();
            String analise = iaService.analisarRelato(projeto.getRelato());

            double fator = 3.0;
            if (projeto.getOngId() != null) {
                // ajuste por área pode ser adicionado aqui futuramente
            }

            double valorImpacto = projeto.getOrcamento() * fator;
            double indiceSroi   = projeto.getOrcamento() > 0
                    ? valorImpacto / projeto.getOrcamento() : 0;

            RelatorioSroi relatorio = new RelatorioSroi();
            relatorio.setProjetoId(projetoId);
            relatorio.setValorInvestido(projeto.getOrcamento());
            relatorio.setValorImpactoSocial(valorImpacto);
            relatorio.setIndiceSroi(indiceSroi);
            relatorio.setAnaliseIa(analise);
            return relatorio;

        } finally {
            projetoDao.fecharConexao();
        }
    }
}
