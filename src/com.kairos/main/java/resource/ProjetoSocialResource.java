package resource;

import dao.ProjetoSocialDao;
import dto.CadastroProjetoSocialDto;
import dto.DetalhesProjetoSocialDto;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.ProjetoSocial;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("projetos-sociais")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjetoSocialResource {

    private final ModelMapper mapper = new ModelMapper();

    @POST
    public Response cadastrar(CadastroProjetoSocialDto dto, @Context UriInfo uriInfo) {
        try {
            ProjetoSocialDao dao = new ProjetoSocialDao();
            try {
                ProjetoSocial projeto = mapper.map(dto, ProjetoSocial.class);
                dao.cadastrar(projeto);
                UriBuilder uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(projeto.getId()));
                return Response.created(uri.build())
                        .entity(mapper.map(projeto, DetalhesProjetoSocialDto.class))
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
            ProjetoSocialDao dao = new ProjetoSocialDao();
            try {
                List<ProjetoSocial> lista = dao.listar();
                List<DetalhesProjetoSocialDto> dtos = new ArrayList<>();
                for (ProjetoSocial p : lista) {
                    dtos.add(mapper.map(p, DetalhesProjetoSocialDto.class));
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
            ProjetoSocialDao dao = new ProjetoSocialDao();
            try {
                ProjetoSocial projeto = dao.pesquisar(id);
                return Response.ok(mapper.map(projeto, DetalhesProjetoSocialDto.class)).build();
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

    @PUT
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, CadastroProjetoSocialDto dto) {
        try {
            ProjetoSocialDao dao = new ProjetoSocialDao();
            try {
                ProjetoSocial projeto = mapper.map(dto, ProjetoSocial.class);
                projeto.setId(id);
                dao.atualizar(projeto);
                return Response.ok(mapper.map(projeto, DetalhesProjetoSocialDto.class)).build();
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
            ProjetoSocialDao dao = new ProjetoSocialDao();
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
