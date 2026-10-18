package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT id, \"firstName\", \"lastName\", \"email\", \"password\", \"username\" FROM public.usuario";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = :id";
    private static final String UPDATE_USER = "UPDATE public.usuario SET \"firstName\" = :firstName, \"lastName\" = :lastName, \"email\" = :email, \"password\" = :password WHERE \"username\" = :username";
    private static final String SELECT_BY_USERNAME = SELECT_ALL + " WHERE \"username\" = :username";
    private static final String INSERT = "INSERT INTO public.usuario (\"firstName\", \"lastName\", \"email\", \"password\", \"username\") VALUES (:firstName, :lastName, :email, :password, :username)";
    private static final String DELETE_BY_ID = "DELETE FROM public.usuario WHERE id = :id";


    private final RowMapper<UserEntity> ROW_MAPPER = (rs, rowNum) -> {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(rs.getInt("id"));
        userEntity.setFirstName(rs.getString("firstName"));
        userEntity.setLastName(rs.getString("lastName"));
        userEntity.setEmail(rs.getString("email"));
        userEntity.setPassword(rs.getString("password"));
        userEntity.setUsername(rs.getString("username"));

        return userEntity;

    };

    @Override
    public List<UserEntity> findAll() {

        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);

    }

    @Override
    public Optional<UserEntity> findById(Integer id) {

        try {

            UserEntity result = jdbcTemplate.queryForObject(SELECT_BY_ID, Map.of("id", id), ROW_MAPPER);

            return Optional.of(result);

        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();

        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {

        String SELECT_BY_EMAIL = SELECT_ALL + " WHERE \"email\" = :email";

        List<UserEntity> result = jdbcTemplate.query(SELECT_BY_EMAIL, Map.of("email", email), ROW_MAPPER);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));

    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {

        List<UserEntity> result = jdbcTemplate.query(SELECT_BY_USERNAME, Map.of("username", username), ROW_MAPPER);

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public UserEntity save(UserEntity entity) {
        // 1. Preparamos o SQL correto para inserção
        String INSERT_USER = INSERT + " RETURNING id";

        // 2. Lemos todos os atributos do usuário (que vieram preenchidos do POST)
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);

        // 3. Executamos o INSERT de fato e aguardamos o ID gerado como retorno
        Integer generatedId = jdbcTemplate.queryForObject(INSERT_USER, params, Integer.class);

        // 4. Injetamos o ID no objeto e o devolvemos
        entity.setId(generatedId);

        return entity;
    }

    @Override
    public UserEntity delete(String username) {
        //captura o do id do usuario a ser deletado
        List<UserEntity> entity = jdbcTemplate.query(SELECT_BY_USERNAME, Map.of("username", username), ROW_MAPPER);

        //verifica se o usuario existe
        if (entity.isEmpty()) {
            return null;
        }

        UserEntity result = entity.get(0);

        //retorna o delete do usuario com base no id capturado
        jdbcTemplate.update(DELETE_BY_ID, Map.of("id", result.getId()));

        return result; // retorna o usuario deletado
    }

    @Override
    public void update(UserEntity entity) {
        // O BeanPropertySqlParameterSource mapeia automaticamente os atributos do objeto para os :parametros do SQL
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(UPDATE_USER, params);
    }
}
