package resource;

import dao.InvestidorDao;
import dto.CadastroInvestidorDto;
import dto.DetalhesInvestidorDto;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.Investidor;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("investidores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestidorResource {

    private final ModelMapper mapper = new ModelMapper();

    @POST
    public Response cadastrar(CadastroInvestidorDto dto, @Context UriInfo uriInfo) {
        try {
            InvestidorDao dao = new InvestidorDao();
            try {
                Investidor investidor = mapper.map(dto, Investidor.class);
                dao.cadastrar(investidor);
                UriBuilder uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(investidor.getId()));
                return Response.created(uri.build())
                        .entity(mapper.map(investidor, DetalhesInvestidorDto.class))
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
            InvestidorDao dao = new InvestidorDao();
            try {
                List<Investidor> lista = dao.listar();
                List<DetalhesInvestidorDto> dtos = new ArrayList<>();
                for (Investidor i : lista) {
                    dtos.add(mapper.map(i, DetalhesInvestidorDto.class));
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
            InvestidorDao dao = new InvestidorDao();
            try {
                Investidor investidor = dao.pesquisar(id);
                return Response.ok(mapper.map(investidor, DetalhesInvestidorDto.class)).build();
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
    public Response atualizar(@PathParam("id") Long id, CadastroInvestidorDto dto) {
        try {
            InvestidorDao dao = new InvestidorDao();
            try {
                Investidor investidor = mapper.map(dto, Investidor.class);
                investidor.setId(id);
                dao.atualizar(investidor);
                return Response.ok(mapper.map(investidor, DetalhesInvestidorDto.class)).build();
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
            InvestidorDao dao = new InvestidorDao();
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
