import java.util.List;
import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/job")
@Produces({MediaType.APPLICATION_JSON})
public class JobResource {

    @GET
    public List<Job> searchByArg(@QueryParam("name") String name,
                                 @QueryParam("min_salary") Integer min_salary,
                                 @QueryParam("max_salary") Integer max_salary,
                                 @QueryParam("job_language") String job_language,
                                 @QueryParam("work_exp") Integer work_exp) {
        Job nArg = new Job(name, min_salary, max_salary, job_language, work_exp);
        List<Job> jobs = new PostgreSQLDAO().searchByArg(nArg);
        return jobs;
    }
}

