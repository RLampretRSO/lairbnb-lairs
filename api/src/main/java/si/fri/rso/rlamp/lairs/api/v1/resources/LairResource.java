package si.fri.rso.rlamp.lairs.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.rso.rlamp.lairbnb.lairs.models.Lair;
import si.fri.rso.rlamp.lairbnb.lairs.services.LairService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Log
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/lairs")
public class LairResource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private LairService lairBean;

    @GET
    @Path("/all")
    public Response getAllLairs() {
        List<Lair> lairs = lairBean.getAllLairs();
        return Response.ok(lairs).build();
    }

    @GET
    public Response getLairs() {
        List<Lair> lairs = lairBean.getLairs(createQuery());
        return Response.ok(lairs).build();
    }

    @GET
    @Path("/count")
    public Response getCount() {
        Long count = lairBean.getLairCount(createQuery());
        return Response.ok(count).build();
    }

    @GET
    @Path("/{lairId}")
    public Response getLair(@PathParam("lairId") Integer lairId) {
        Lair lair = lairBean.getLair(lairId);
        return lair != null
                ? Response.ok(lair).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addNewLair(Lair lair) {
        lairBean.createLair(lair);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{lairId}")
    public Response updateLair(@PathParam("lairId") Integer lairId, Lair lair) {
        lairBean.putLair(lairId, lair);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{lairId}")
    public Response deleteLair(@PathParam("lairId") Integer lairId) {
        lairBean.deleteLair(lairId);
        return Response.noContent().build();
    }

    /**
     * Helper method for parsing query parameters from uri.
     *
     * @return query parameters
     */
    private QueryParameters createQuery() {
        return QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).defaultLimit(10).build();
    }
}
