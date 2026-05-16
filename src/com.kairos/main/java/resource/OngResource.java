package resource;

import dao.OngDao;
import dto.AtualizacaoOngDto;
import dto.CadastroOngDto;
import dto.DetalhesOngDto;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.Ong;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("ong")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OngResource {

    private OngDao ongDao;

    public OngResource() throws SQLException {
        this.ongDao = new OngDao();
    }

    @POST
    public Response cadastrar(CadastroOngDto ongDto, @Context UriInfo uriInfo) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        Ong ong = mapper.map(ongDto, Ong.class);
        ongDao.cadastrar(ong);
        UriBuilder uri = uriInfo.getAbsolutePathBuilder();
        uri.path(String.valueOf(ong.getId()));
        return Response.created(uri.build()).entity(mapper.map(ong, DetalhesOngDto.class)).build();
    }

    @GET
    public List<Ong> listar() throws SQLException {
        return ongDao.listar();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) throws SQLException {
        try {
            Ong ong = ongDao.pesquisar(id);
            ModelMapper mapper = new ModelMapper();
            return Response.ok(mapper.map(ong, DetalhesOngDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, AtualizacaoOngDto ongDto) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        Ong ong = mapper.map(ongDto, Ong.class);
        ong.setId(id);
        try {
            ongDao.atualizar(ong);
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.map(ong, DetalhesOngDto.class)).build();
    }
    @PATCH
    @Path("{id}")
    public Response atualizarParcial(@PathParam("id") Long id, AtualizacaoOngDto ongParcialDto) throws SQLException {
        try {
            Ong ongExistente = ongDao.pesquisar(id);
            if (ongParcialDto.getNome() != null) {
                ongExistente.setNome(ongParcialDto.getNome());
            }
            if (ongParcialDto.getDescricao() != null) {
                ongExistente.setDescricao(ongParcialDto.getDescricao());
            }
            if (ongParcialDto.getDescricao() != null) {
                ongExistente.setDescricao(ongParcialDto.getDescricao());
            }
            if (ongParcialDto.getAreaAtuacao() != null) {
                ongExistente.setAreaAtuacao(ongParcialDto.getAreaAtuacao());
            }
            ongDao.atualizar(ongExistente);
            ModelMapper mapper = new ModelMapper();
            return Response.ok(mapper.map(ongExistente, DetalhesOngDto.class)).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response remover(@PathParam("id") Long id) throws SQLException {
        try {
            ongDao.remover(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
