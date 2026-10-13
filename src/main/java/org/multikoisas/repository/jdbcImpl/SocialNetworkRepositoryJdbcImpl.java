package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.SocialNetworkEntity;
import org.multikoisas.repository.SocialNetworkRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SocialNetworkRepositoryJdbcImpl implements SocialNetworkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT id, \"descricao\", \"link\" FROM public.redesSociais";

    private final RowMapper<SocialNetworkEntity> ROW_MAPPER = (rs, rowNum) -> {
        SocialNetworkEntity socialNetworkEntity = new SocialNetworkEntity();
        socialNetworkEntity.setId(rs.getInt("id"));
        socialNetworkEntity.setDescricao(rs.getString("descricao"));
        socialNetworkEntity.setLink(rs.getString("link"));

        return socialNetworkEntity;
    };

    @Override
    public List<SocialNetworkEntity> findAll() {

        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);

    }
}
