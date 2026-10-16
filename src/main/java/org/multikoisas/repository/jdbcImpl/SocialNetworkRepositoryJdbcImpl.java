package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.SocialNetworkRequest;
import org.multikoisas.entities.SocialNetworkEntity;
import org.multikoisas.entities.UserEntity;
import org.multikoisas.repository.SocialNetworkRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialNetworkRepositoryJdbcImpl implements SocialNetworkRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL = "SELECT id, descricao, link FROM public.\"redesSociais\"";
    private final static String SELECT_BY_DESCRIPTION = SELECT_ALL + " WHERE descricao = :descricao";
    private final static String INSERT = "INSERT INTO public.\"redesSociais\" (descricao, link) VALUES (:descricao, :link)";
    private final static String DELETE = "DELETE FROM public.\"redesSociais\" WHERE descricao = :descricao";
    private final static String UPDATE = "UPDATE public.\"redesSociais\" SET descricao = :descricao, link = :link WHERE id = :id";

    private final RowMapper<SocialNetworkEntity> ROW_MAPPER = (rs, rowNum) -> {
        SocialNetworkEntity socialNetworkEntity = new SocialNetworkEntity();
        socialNetworkEntity.setId(rs.getInt("id"));
        socialNetworkEntity.setDescricao(rs.getString("descricao"));
        socialNetworkEntity.setLink(rs.getString("link"));
        return socialNetworkEntity;
    };

    @Override
    public List<SocialNetworkEntity> findAll(){

        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);

    }

    @Override
    public Optional<SocialNetworkEntity> findByName(String descricao) {
        try{
            SocialNetworkEntity socialNetworkEntity = jdbcTemplate.queryForObject(SELECT_BY_DESCRIPTION, java.util.Map.of("descricao", descricao), ROW_MAPPER);
            return Optional.of(socialNetworkEntity);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public SocialNetworkEntity save(SocialNetworkEntity entity) {
        // 1. Preparamos o SQL correto para inserção
        String INSERT_SQL = INSERT + " RETURNING id";

        // 2. Lemos todos os atributos do usuário (que vieram preenchidos do POST)
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);

        // 3. Executamos o INSERT de fato e aguardamos o ID gerado como retorno
        Integer generatedId = jdbcTemplate.queryForObject(INSERT_SQL, params, Integer.class);

        // 4. Injetamos o ID no objeto e o devolvemos
        entity.setId(generatedId);

        return entity;
    }

    @Override
    public SocialNetworkEntity delete(String descricao) {

        List<SocialNetworkEntity> entity = jdbcTemplate.query(SELECT_ALL + " WHERE descricao = :descricao", java.util.Map.of("descricao", descricao), ROW_MAPPER);

        if (entity.isEmpty()) {
            return null;
        }

        SocialNetworkEntity result = entity.get(0);

        jdbcTemplate.update(DELETE, java.util.Map.of("descricao", descricao));

        return result;
    }

    @Override
    public void update(SocialNetworkEntity entity) {
        // O BeanPropertySqlParameterSource mapeia automaticamente os atributos do objeto para os :parametros do SQL
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        jdbcTemplate.update(UPDATE, params);
    }
}
