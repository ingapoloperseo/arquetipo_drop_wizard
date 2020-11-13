package net.arquetipo.base.db;

import net.arquetipo.base.api.User;
import net.arquetipo.base.db.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("INSERT INTO app_user(name, email, created, user_type) VALUES(:name, :email, :created, :type) RETURNING *")
    User insertUser(@BindBean User newUser);

    // FIXME probar
    @SqlQuery("SELECT name FROM app_user WHERE name = :name")
    User findByName(@Bind("name") String name);

    @SqlQuery("SELECT * FROM app_user ORDER BY name")
    Set<User> listUsers();

    /**
     * close with no args is used to close the connection
     */
    void close();

}
