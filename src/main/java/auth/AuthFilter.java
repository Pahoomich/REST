package auth;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AuthFilter implements ContainerRequestFilter {

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getPath(true);

        //We do allow wadl to be retrieve
        if (method.equals("GET") && (path.equals("application.wadl") || path.equals("application.wadl/xsd0.xsd"))){
            return containerRequest;
        }

        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderValue("authorization");

        //If the user does not have the right (does not provide any HTTP Basic Auth)
        if (auth == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        //lap : loginAndPassword
        String[] lap = BasicAuth.decode(auth);

        //If login or password fail
        if (lap == null || lap.length != 2) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        //Our system refuse login and password
        if (!lap[0].equals("labuser") && !lap[1].equals("12345678")) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }


        return containerRequest;
    }
}

