package net.arquetipo.base.resources;

import net.arquetipo.base.api.User;
import net.arquetipo.base.api.UserName;
import net.arquetipo.base.api.UserType;
import net.arquetipo.base.db.UserDAO;
import net.arquetipo.base.resources.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class.getName());
    private static final String ENTITY_NAME = "Usuario";

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * {@code POST  /users} : Crea un nuevo usuario
     *
     * @param createUserRequest nuevo usuario
     */
    @POST
    public Response createUser(User createUserRequest) throws URISyntaxException {
        LOGGER.info(">>createUser('{}')", createUserRequest);

        if (createUserRequest.getId() != null) {
            throw new BadRequestException("Un nuevo usuario no puede tener ID ya asignado", ENTITY_NAME, "idexists");
        }

        User result = userDAO.insertUser(createUserRequest);

        return Response.created(new URI("/api/users/" + result.getId())).build();
    }

    @PUT
    public Response updateUser(User updateUserRequest) {
        LOGGER.info(">>updateUser('{}')", updateUserRequest);

        if (updateUserRequest.getId() == null) {
            throw new BadRequestException("ID incorrecto", ENTITY_NAME, "idnull");
        }

        User result = userDAO.updateUser(updateUserRequest);

        return Response.accepted(result).build();
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
        return userDAO.listUsers();
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
