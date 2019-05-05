import exept.IllegalNameException;
import exept.IllegalNameExceptionMapper;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RolesAllowed("ADMIN")
@Path("/job")
@Produces({MediaType.APPLICATION_JSON})
public class JobResource {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Job> searchByArg(@QueryParam("job_tittle") String job_tittle,
                                 @QueryParam("min_salary") Integer min_salary,
                                 @QueryParam("max_salary") Integer max_salary,
                                 @QueryParam("job_language") String job_language,
                                 @QueryParam("work_exp") Integer work_exp) {
        Job nArg = new Job(job_tittle, min_salary, max_salary, job_language, work_exp);
        List<Job> jobs = new PostgreSQLDAO().searchByArg(nArg);
        return jobs;
    }


    @POST
    @Path("/addNew")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response addNew(Job nNew) throws IllegalNameException {
        if (nNew.getJob_title() == null || nNew.getJob_title().trim().isEmpty() ||
                nNew.getJob_language() == null || nNew.getJob_language().trim().isEmpty() ||
                nNew.getMax_salary() == null ||
                nNew.getMin_salary() == null ||
                nNew.getWork_exp() == null) {
            throw new IllegalNameException("Error: The value of one of the fields is empty");
        }
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return Response.status(Response.Status.CREATED).entity(dao.addNew(nNew)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecord(@PathParam("id") Integer id, Job nJob) throws IllegalNameException {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (dao.searchById(id))
            return Response.status(Response.Status.ACCEPTED).entity(dao.updateRecord(id, nJob)).build();
        else {
            String errMessage = "Error: id = " + id.toString() + " is not find";
            Response response = new IllegalNameExceptionMapper().toResponse(new IllegalNameException(errMessage));
            return response;
        }
    }


    @DELETE
    @Path("{id}")
    public Response deleteCar(@PathParam("id") Integer id) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        if (dao.searchById(id)) {
            return Response.status(Response.Status.ACCEPTED).entity(dao.dropRecord(id)).build();
        } else {
            String errMessage = "Error: id = " + id.toString() + " is not find";
            Response response = new IllegalNameExceptionMapper().toResponse(new IllegalNameException(errMessage));
            return response;
        }
    }

}

