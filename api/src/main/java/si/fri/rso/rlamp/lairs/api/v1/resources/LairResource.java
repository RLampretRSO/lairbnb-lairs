package si.fri.rso.rlamp.lairs.api.v1.resources;

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
    public Response getAllCustomers() {
        List<Lair> customers = lairBean.getAllLairs();
        return Response.ok(customers).build();
    }

    @GET
    public Response getCustomers() {
        List<Lair> customers = lairBean.getLairs(createQuery());
        return Response.ok(customers).build();
    }

    @GET
    @Path("/count")
    public Response getCount() {
        Long count = lairBean.getLairCount(createQuery());
        return Response.ok(count).build();
    }

    @GET
    @Path("/{lairId}")
    public Response getCustomer(@PathParam("lairId") Integer lairId) {
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
