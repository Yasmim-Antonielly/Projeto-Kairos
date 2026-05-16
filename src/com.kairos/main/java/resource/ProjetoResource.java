package resource;

import dao.OngDao;
import dao.ProjetoDao;
import dto.*;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.Ong;
import model.Projeto;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("projetos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjetoResource {

    @POST
    public Response cadastrar(CadastroProjetoDto projetoDto, @Context UriInfo uriInfo) throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            ModelMapper mapper = new ModelMapper();
            Projeto projeto = mapper.map(projetoDto, Projeto.class);
            projetoDao.cadastrar(projeto);
            UriBuilder uri = uriInfo.getAbsolutePathBuilder();
            uri.path(String.valueOf(projeto.getId()));
            return Response.created(uri.build())
                    .entity(mapper.map(projeto, DetalhesProjetoDto.class)).build();
        } finally {
            projetoDao.fecharConexao();
        }
    }

    @GET
    public List<Projeto> listar() throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            return projetoDao.listar();
        } finally {
            projetoDao.fecharConexao();
        }
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            Projeto projeto = projetoDao.pesquisar(id);
            ModelMapper mapper = new ModelMapper();
            return Response.ok(mapper.map(projeto, DetalhesProjetoDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            projetoDao.fecharConexao();
        }
    }

    @PUT
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, AtualizacaoProjetoDto projetoDto) throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            ModelMapper mapper = new ModelMapper();
            Projeto projeto = mapper.map(projetoDto, Projeto.class);
            projeto.setId(id);
            projetoDao.atualizar(projeto);
            return Response.ok(mapper.map(projeto, DetalhesProjetoDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            projetoDao.fecharConexao();
        }
    }

    @PATCH
    @Path("{id}")
    public Response atualizarParcial(@PathParam("id") Long id,
                                     AtualizacaoProjetoDto projetoParcialDto) throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            Projeto projetoExistente = projetoDao.pesquisar(id);
            if (projetoParcialDto.getTitulo() != null)
                projetoExistente.setTitulo(projetoParcialDto.getTitulo());
            if (projetoParcialDto.getRelato() != null)
                projetoExistente.setRelato(projetoParcialDto.getRelato());
            if (projetoParcialDto.getOrcamento() != null)
                projetoExistente.setOrcamento(projetoParcialDto.getOrcamento());
            if (projetoParcialDto.getStatus() != null)
                projetoExistente.setStatus(projetoParcialDto.getStatus());
            projetoDao.atualizar(projetoExistente);
            ModelMapper mapper = new ModelMapper();
            return Response.ok(mapper.map(projetoExistente, DetalhesProjetoDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            projetoDao.fecharConexao();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remover(@PathParam("id") Long id) throws SQLException {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            projetoDao.remover(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            projetoDao.fecharConexao();
        }
    }
}
