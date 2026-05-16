package resource;

import dto.DetalhesSroiDto;
import exception.EntidadeNaoEncontradaException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.RelatorioSroi;
import org.modelmapper.ModelMapper;
import service.SroiService;

import java.sql.SQLException;
import java.util.Map;

@Path("projetos/{id}/sroi")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SroiResource {

    private final SroiService sroiService = new SroiService();

    @POST
    public Response gerarSroi(@PathParam("id") Long projetoId,
                              @Context UriInfo uriInfo) {
        try {
            RelatorioSroi relatorio = sroiService.gerarRelatorio(projetoId);
            ModelMapper mapper = new ModelMapper();
            DetalhesSroiDto dto = mapper.map(relatorio, DetalhesSroiDto.class);
            UriBuilder uri = uriInfo.getAbsolutePathBuilder();
            return Response.created(uri.build()).entity(dto).build();

        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response buscarSroi(@PathParam("id") Long projetoId) {
        try {
            RelatorioSroi relatorio = sroiService.gerarRelatorio(projetoId);
            ModelMapper mapper = new ModelMapper();
            DetalhesSroiDto dto = mapper.map(relatorio, DetalhesSroiDto.class);
            return Response.ok(dto).build();

        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }
}
