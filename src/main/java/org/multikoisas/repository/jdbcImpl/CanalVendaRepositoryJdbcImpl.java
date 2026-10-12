package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.CanalVendaEntity;
import org.multikoisas.repository.CanalVendaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CanalVendaRepositoryJdbcImpl implements CanalVendaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT id, \"nome\" FROM public.canal_venda";

    private final RowMapper<CanalVendaEntity> ROW_MAPPER = (rs, rowNum) -> {
        CanalVendaEntity canalVendaEntity = new CanalVendaEntity();

        canalVendaEntity.setId(rs.getInt("id"));
        canalVendaEntity.setNome(rs.getString("nome"));

        return canalVendaEntity;
    };

    @Override
    public List<CanalVendaEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}
