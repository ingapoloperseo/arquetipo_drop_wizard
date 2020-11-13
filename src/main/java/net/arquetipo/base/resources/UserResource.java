package net.arquetipo.base.resources;

import net.arquetipo.base.api.User;
import net.arquetipo.base.db.UserDAO;
import net.arquetipo.base.resources.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
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
     * @return Response
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

    /**
     * {@code PUT  /users} : Actualiza un usuario
     *
     * @param updateUserRequest Usuario a actualizar
     * @return Response
     */
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
     * {@code GET  /users/:id} : Recupera un usuario para el id proporcionado
     *
     * @param id Identificador del usuario a buscar
     */
    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") Long id) {
        LOGGER.info(">>getUser({})", id);

        User result = userDAO.findById(id);
        if (result != null) {
            return Response.ok(result).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    // TODO paginación, posiblemente https://github.com/aruld/dropwizard-pagination
    public Set<User> getAllUsers() {
        LOGGER.info(">>getAllUsers ");
        return userDAO.listUsers();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        LOGGER.info(">>deleteUser({})", id);

        userDAO.deleteUser(id);

        return Response.noContent().build();

    }
}
