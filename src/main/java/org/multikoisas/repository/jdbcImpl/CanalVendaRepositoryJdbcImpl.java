package org.multikoisas.repository.jdbcImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.multikoisas.entities.CanalVendaEntity;
import org.multikoisas.repository.CanalVendaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CanalVendaRepositoryJdbcImpl implements CanalVendaRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CanalVendaRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CanalVendaEntity> ROW_MAPPER = (rs, rowNum) -> {
        CanalVendaEntity e = new CanalVendaEntity();
        e.setId(rs.getInt("id"));
        e.setNome(rs.getString("nome"));
        return e;
    };

    @Override
    public List<CanalVendaEntity> findAll() {
        return jdbcTemplate.query("SELECT id, nome FROM \"canalVenda\"", ROW_MAPPER);
    }

    @Override
    public Optional<CanalVendaEntity> findById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<CanalVendaEntity> result = jdbcTemplate.query("SELECT id, nome FROM \"canalVenda\" WHERE id = :id", params, ROW_MAPPER);
        return result.stream().findFirst();
    }

    @Override
    public CanalVendaEntity save(CanalVendaEntity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("nome", entity.getNome());
        jdbcTemplate.update("INSERT INTO \"canalVenda\" (id, nome) VALUES (:id, :nome)", params);
                return entity;
    }

    @Override
    public boolean update(Integer id, CanalVendaEntity entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("nome", entity.getNome());
        return jdbcTemplate.update("UPDATE \"canalVenda\" SET nome = :nome WHERE id = :id", params) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.update("DELETE FROM \"canalVenda\" WHERE id = :id", params) > 0;
    }
}
