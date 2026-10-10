package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM public.\"usuario\"";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = :id";
    private static final String INSERT = "INSERT INTO public.\"usuario\" (id, firstName, lastName, email, password, userName) VALUES (:id, :firstName, :lastName, :email, :password, :userName)";
    private static final String DELETE = "DELETE FROM public.\"usuario\" WHERE id = :id";

    private final RowMapper<UserEntity> ROW_MAPPER = (rs, rowNum) -> {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(rs.getInt("id"));
        userEntity.setFirstName(rs.getString("firstName"));
        userEntity.setLastName(rs.getString("lastName"));
        userEntity.setEmail(rs.getString("email"));
        userEntity.setPassword(rs.getString("password"));
        userEntity.setUsername(rs.getString("userName"));

        return userEntity;

    };

    @Override
    public List<UserEntity> findAll() {

        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);

    }

    @Override
    public Optional<UserEntity> findById(Integer id) {

        try {

            UserEntity user = jdbcTemplate.queryForObject(SELECT_BY_ID,Map.of("id", id), ROW_MAPPER);

            return Optional.of(user);

        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();

        }
    }

    @Override
    public UserEntity save(String email) {
        return null;
    }

    @Override
    public UserEntity delete(Integer id) {
        return null;
    }
}
