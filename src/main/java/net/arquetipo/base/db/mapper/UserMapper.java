package net.arquetipo.base.db.mapper;

import net.arquetipo.base.api.User;
import net.arquetipo.base.api.UserType;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .type(UserType.valueOf(resultSet.getString("user_type")))
                .build();
    }
}
