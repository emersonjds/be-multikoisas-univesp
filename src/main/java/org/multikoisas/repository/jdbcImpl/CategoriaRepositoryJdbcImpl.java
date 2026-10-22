package org.multikoisas.repository.jdbcImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.multikoisas.entities.CategoriaEntity;
import org.multikoisas.repository.CategoriaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepositoryJdbcImpl implements CategoriaRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoriaRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CategoriaEntity> ROW_MAPPER = (rs, rowNum) -> {
        CategoriaEntity e = new CategoriaEntity();
        e.setId(rs.getInt("id"));
        e.setNome(rs.getString("nome"));
        return e;
    };

    @Override
    public List<CategoriaEntity> findAll() {
        return jdbcTemplate.query("SELECT id, nome FROM categoria", ROW_MAPPER);
    }

    @Override
    public Optional<CategoriaEntity> findById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<CategoriaEntity> result = jdbcTemplate.query("SELECT id, nome FROM categoria WHERE id = :id", params, ROW_MAPPER);
        return result.stream().findFirst();
    }

    @Override
    public CategoriaEntity save(CategoriaEntity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("nome", entity.getNome());
        Integer id = jdbcTemplate.queryForObject("INSERT INTO categoria (nome) VALUES (:nome) RETURNING id", params, Integer.class);
                entity.setId(id);
                return entity;
    }

    @Override
    public boolean update(Integer id, CategoriaEntity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("nome", entity.getNome());
        return jdbcTemplate.update("UPDATE categoria SET nome = :nome WHERE id = :id", params) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.update("DELETE FROM categoria WHERE id = :id", params) > 0;
    }
}