package net.arquetipo.base.resources;

import net.arquetipo.base.api.User;
import net.arquetipo.base.api.UserName;
import net.arquetipo.base.api.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class.getName());

    @POST
    public void createUser(User createUserRequest) {
        LOGGER.info(">>createUser('{}')", createUserRequest);
    }

    /**
     * Recupera un usuario para el nombre proporcionado
     *
     * @param name Nombre del usuario a buscar
     */
    @GET
    @Path("{name}")
    public User getUser(@PathParam("name") UserName name) {
        LOGGER.info(">>getUser({})", name);
        return getSampleUser();
    }

    @GET
    public Set<User> getAllUsers() {
        LOGGER.info(">>getAllUsers ");
        return Collections.singleton(getSampleUser());
    }

    /**
     * Temporal: construir un usuario para tener algo que regresar
     * FIXME remover una vez conectado a BD
     *
     * @return dummy
     */
    private User getSampleUser() {
        return User.builder()
                .name("Juan Perez")
                .email("jperez@mail.com")
                .created(LocalDateTime.now())
                .type(UserType.ADMINISTRATOR)
                .build();
    }

    @DELETE
    @Path("{name}")
    public void deleteUser(@PathParam("name") UserName name) {
        LOGGER.info(">>deleteUser({})", name);

        // FIXME código dummy
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}
