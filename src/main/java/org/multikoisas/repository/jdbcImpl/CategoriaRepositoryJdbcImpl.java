package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.CategoriaEntity;
import org.multikoisas.repository.CategoriaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoriaRepositoryJdbcImpl implements CategoriaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT id, \"nome\" FROM public.categoria";

    private final RowMapper<CategoriaEntity> ROW_MAPPER = (rs, rowNum) -> {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(rs.getInt("id"));
        categoriaEntity.setNome(rs.getString("nome"));

        return categoriaEntity;
    };

    @Override
    public List<CategoriaEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}
