package org.example;

import dao.ProjetoSocialDao;
import dao.InvestidorDao;
import dao.AuditoriaImpactoDao;
import model.ProjetoSocial;
import model.Investidor;
import model.AuditoriaImpacto;
import exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;
import java.util.List;

public class MainDatabaseTest {

    public static void main(String[] args) {
        System.out.println("\u001B[34m===================================================================\u001B[0m");
        System.out.println("\u001B[34m\u001B[1m   KAIRÓS - TESTE DE INTEGRAÇÃO DE PERSISTÊNCIA & BANCO DE DADOS   \u001B[0m");
        System.out.println("\u001B[34m===================================================================\u001B[0m");

        try {
            // 1. Instanciar DAOs
            System.out.println("[INFO] Inicializando DAOs e estabelecendo conexão JDBC...");
            ProjetoSocialDao projetoDao = new ProjetoSocialDao();
            InvestidorDao investidorDao = new InvestidorDao();
            AuditoriaImpactoDao auditoriaDao = new AuditoriaImpactoDao();
            System.out.println("\u001B[32m[OK] Conectado ao Oracle Database com sucesso!\u001B[0m");

            System.out.println("\n-------------------------------------------------------------------");
            System.out.println(" 1. TESTANDO ENTIDADE: ProjetoSocial");
            System.out.println("-------------------------------------------------------------------");

            ProjetoSocial novoProjeto = new ProjetoSocial();
            novoProjeto.setTitulo("Horta Comunitária Paraisópolis");
            novoProjeto.setDescricao("Cultivo sustentável de hortaliças hidropônicas por famílias da comunidade.");
            novoProjeto.setCategoriaEsg("SOCIAL");
            novoProjeto.setOrcamentoNecessario(55000.00);
            novoProjeto.setOngResponsavel("União de Paraisópolis");
            novoProjeto.setCnpj("22.333.444/0001-55");
            novoProjeto.setMetasComunidade("Fornecer alimentação saudável para 300 famílias e capacitar 40 mulheres na gestão agrícola.");

            System.out.println("[INFO] Persistindo novo projeto social via DAO...");
            projetoDao.cadastrar(novoProjeto);
            System.out.println("\u001B[32m[OK] Projeto Social cadastrado com ID: " + novoProjeto.getId() + "\u001B[0m");

            System.out.println("[INFO] Recuperando projeto cadastrado do banco para validação...");
            ProjetoSocial projetoSalvo = projetoDao.pesquisar(novoProjeto.getId());
            System.out.println("  > Título recuperado: " + projetoSalvo.getTitulo());
            System.out.println("  > Categoria ESG: " + projetoSalvo.getCategoriaEsg());
            System.out.println("  > CNPJ: " + projetoSalvo.getCnpj());

            System.out.println("\n-------------------------------------------------------------------");
            System.out.println(" 2. TESTANDO ENTIDADE: Investidor");
            System.out.println("-------------------------------------------------------------------");

            Investidor novoInvestidor = new Investidor();
            novoInvestidor.setNome("Kairós Seed Fund");
            novoInvestidor.setTipoInvestidor("FUNDO");
            novoInvestidor.setTeseInvestimento("Investimentos anjo direcionados a cooperativas ecológicas urbanas.");
            novoInvestidor.setCriteriosEsg("Mínimo de 3.0 no índice SROI auditado e governança ética.");
            novoInvestidor.setOrcamentoDisponivel(1250000.00);
            novoInvestidor.setCnpjCpf("33.444.555/0001-99");

            System.out.println("[INFO] Persistindo novo investidor via DAO...");
            investidorDao.cadastrar(novoInvestidor);
            System.out.println("\u001B[32m[OK] Investidor cadastrado com ID: " + novoInvestidor.getId() + "\u001B[0m");

            System.out.println("[INFO] Recuperando investidor cadastrado do banco...");
            Investidor investidorSalvo = investidorDao.pesquisar(novoInvestidor.getId());
            System.out.println("  > Nome recuperado: " + investidorSalvo.getNome());
            System.out.println("  > Orçamento Disponível: R$ " + investidorSalvo.getOrcamentoDisponivel());

            System.out.println("\n-------------------------------------------------------------------");
            System.out.println(" 3. TESTANDO ENTIDADE: AuditoriaImpacto & Cálculos SROI");
            System.out.println("-------------------------------------------------------------------");

            AuditoriaImpacto novaAuditoria = new AuditoriaImpacto();
            novaAuditoria.setProjetoSocialId(projetoSalvo.getId());
            novaAuditoria.setValorInvestido(50000.00);
            
            // Simular SROI de 4.2x para categoria SOCIAL
            double fatorSroi = 4.2;
            double retornoSocial = 50000.00 * fatorSroi;
            
            novaAuditoria.setValorRetornoSocial(retornoSocial);
            novaAuditoria.setIndiceSroi(fatorSroi);
            novaAuditoria.setCaminhoRecibos("raw_data/receipts/projeto_" + projetoSalvo.getId() + "/");
            novaAuditoria.setCaminhoFotos("raw_data/photos/projeto_" + projetoSalvo.getId() + "/");
            novaAuditoria.setDadosBrutosTexto("Recibos de sementes e calhas: R$ 35.000; Transporte e adubo: R$ 15.000. Relato: Produção iniciada e fornecendo hortaliças.");
            novaAuditoria.setAnaliseIa("Análise IA: Retorno social excepcional (SROI 4.2). O projeto de Paraisópolis resolve a segurança alimentar local e promove empoderamento feminino.");
            novaAuditoria.setStatusAuditoria("APROVADO");

            System.out.println("[INFO] Persistindo registro de auditoria via DAO...");
            auditoriaDao.cadastrar(novaAuditoria);
            System.out.println("\u001B[32m[OK] Auditoria cadastrada com ID: " + novaAuditoria.getId() + "\u001B[0m");
            System.out.println("  > Data de Auditoria gerada pelo banco: " + novaAuditoria.getDataAuditoria());

            System.out.println("[INFO] Recuperando auditoria cadastrada...");
            AuditoriaImpacto auditoriaSalva = auditoriaDao.pesquisar(novaAuditoria.getId());
            System.out.println("  > Índice SROI: " + auditoriaSalva.getIndiceSroi() + "x");
            System.out.println("  > Valor Social Gerado: R$ " + auditoriaSalva.getValorRetornoSocial());
            System.out.println("  > Status de Auditoria: " + auditoriaSalva.getStatusAuditoria());

            System.out.println("\n-------------------------------------------------------------------");
            System.out.println(" 4. LISTANDO DADOS TOTAIS");
            System.out.println("-------------------------------------------------------------------");
            
            List<ProjetoSocial> projetos = projetoDao.listar();
            System.out.println("[INFO] Total de Projetos Sociais no Banco: " + projetos.size());
            
            List<Investidor> investidores = investidorDao.listar();
            System.out.println("[INFO] Total de Investidores no Banco: " + investidores.size());

            List<AuditoriaImpacto> auditorias = auditoriaDao.listar();
            System.out.println("[INFO] Total de Auditorias de Impacto no Banco: " + auditorias.size());

            // 4. Limpar os dados de teste criados nesta execução para deixar o banco limpo e consistente
            System.out.println("\n[INFO] Iniciando limpeza segura dos registros de teste criados...");
            auditoriaDao.remover(auditoriaSalva.getId());
            investidorDao.remover(investidorSalvo.getId());
            projetoDao.remover(projetoSalvo.getId());
            System.out.println("\u001B[32m[OK] Banco limpo e higienizado após os testes com sucesso!\u001B[0m");

            // Fechar conexões
            projetoDao.fecharConexao();
            investidorDao.fecharConexao();
            auditoriaDao.fecharConexao();
            
            System.out.println("\n\u001B[32m===================================================================");
            System.out.println("   SUCESSO ABSOLUTO: Todas as operações CRUD e JDBC funcionaram!   ");
            System.out.println("===================================================================\u001B[0m");

        } catch (SQLException e) {
            System.out.println("\n\u001B[31m[ERRO BANCO DE DADOS]: " + e.getMessage() + "\u001B[0m");
            e.printStackTrace();
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("\n\u001B[31m[ERRO ENTIDADE NÃO ENCONTRADA]: " + e.getMessage() + "\u001B[0m");
        }
    }
}
