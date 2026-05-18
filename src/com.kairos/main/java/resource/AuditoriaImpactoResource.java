package resource;

import dao.AuditoriaImpactoDao;
import dao.ProjetoSocialDao;
import dto.CadastroAuditoriaImpactoDto;
import dto.DetalhesAuditoriaImpactoDto;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.AuditoriaImpacto;
import model.ProjetoSocial;
import org.modelmapper.ModelMapper;
import service.IaService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("auditorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuditoriaImpactoResource {

    private final ModelMapper mapper = new ModelMapper();
    private final IaService iaService = new IaService();

    @POST
    public Response cadastrar(CadastroAuditoriaImpactoDto dto, @Context UriInfo uriInfo) {
        try {
            ProjetoSocialDao projetoDao = new ProjetoSocialDao();
            ProjetoSocial projeto;
            try {
                projeto = projetoDao.pesquisar(dto.getProjetoSocialId());
            } catch (EntidadeNaoEncontradaException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("erro", "Projeto Social com ID " + dto.getProjetoSocialId() + " não existe."))
                        .build();
            } finally {
                projetoDao.fecharConexao();
            }

            AuditoriaImpacto auditoria = mapper.map(dto, AuditoriaImpacto.class);

            // 1. Calculate SROI values dynamically based on Category
            double fator = 3.0; // default multiplier
            if (projeto.getCategoriaEsg() != null) {
                String cat = projeto.getCategoriaEsg().toUpperCase();
                if (cat.contains("AMBIENTAL")) {
                    fator = 3.5;
                } else if (cat.contains("SOCIAL")) {
                    fator = 4.2;
                } else if (cat.contains("GOVERNANCA") || cat.contains("GOVERNANÇA")) {
                    fator = 3.0;
                }
            }

            double valorInvestido = auditoria.getValorInvestido() != null ? auditoria.getValorInvestido() : 0.0;
            double valorRetornoSocial = valorInvestido * fator;
            
            auditoria.setValorRetornoSocial(valorRetornoSocial);
            auditoria.setIndiceSroi(fator);
            auditoria.setStatusAuditoria("APROVADO");

            // 2. Call AI Service to perform deep NLP analysis on raw/unstructured logs
            String relato = auditoria.getDadosBrutosTexto();
            if (relato != null && !relato.isBlank()) {
                String analiseIa = iaService.analisarRelato(relato);
                auditoria.setAnaliseIa(analiseIa);
            } else {
                auditoria.setAnaliseIa("Nenhum dado textual bruto foi informado para auditoria por IA.");
            }

            // 3. Persist in Oracle Database
            AuditoriaImpactoDao dao = new AuditoriaImpactoDao();
            try {
                dao.cadastrar(auditoria);
                UriBuilder uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(auditoria.getId()));
                return Response.created(uri.build())
                        .entity(mapper.map(auditoria, DetalhesAuditoriaImpactoDto.class))
                        .build();
            } finally {
                dao.fecharConexao();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro de banco de dados: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response listar() {
        try {
            AuditoriaImpactoDao dao = new AuditoriaImpactoDao();
            try {
                List<AuditoriaImpacto> lista = dao.listar();
                List<DetalhesAuditoriaImpactoDto> dtos = new ArrayList<>();
                for (AuditoriaImpacto a : lista) {
                    dtos.add(mapper.map(a, DetalhesAuditoriaImpactoDto.class));
                }
                return Response.ok(dtos).build();
            } finally {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro de banco de dados: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("projeto/{projetoId}")
    public Response listarPorProjeto(@PathParam("projetoId") Long projetoId) {
        try {
            AuditoriaImpactoDao dao = new AuditoriaImpactoDao();
            try {
                List<AuditoriaImpacto> lista = dao.listarPorProjeto(projetoId);
                List<DetalhesAuditoriaImpactoDto> dtos = new ArrayList<>();
                for (AuditoriaImpacto a : lista) {
                    dtos.add(mapper.map(a, DetalhesAuditoriaImpactoDto.class));
                }
                return Response.ok(dtos).build();
            } finally {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro de banco de dados: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            AuditoriaImpactoDao dao = new AuditoriaImpactoDao();
            try {
                AuditoriaImpacto auditoria = dao.pesquisar(id);
                return Response.ok(mapper.map(auditoria, DetalhesAuditoriaImpactoDto.class)).build();
            } catch (EntidadeNaoEncontradaException e) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("erro", e.getMessage()))
                        .build();
            } finally {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro de banco de dados: " + e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remover(@PathParam("id") Long id) {
        try {
            AuditoriaImpactoDao dao = new AuditoriaImpactoDao();
            try {
                dao.remover(id);
                return Response.noContent().build();
            } catch (EntidadeNaoEncontradaException e) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("erro", e.getMessage()))
                        .build();
            } finally {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro de banco de dados: " + e.getMessage()))
                    .build();
        }
    }
}
