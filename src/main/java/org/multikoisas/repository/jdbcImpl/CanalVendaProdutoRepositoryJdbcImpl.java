
package org.multikoisas.repository.jdbcImpl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.multikoisas.entities.CanalVendaProdutoEntity;
import org.multikoisas.repository.CanalVendaProdutoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CanalVendaProdutoRepositoryJdbcImpl implements CanalVendaProdutoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL =
            "SELECT \"produtoId\", \"canalVendaId\", link " +
            "FROM public.\"canalVendaProduto\"";

    private static final RowMapper<CanalVendaProdutoEntity> ROW_MAPPER =
            (rs, rowNum) -> {
                CanalVendaProdutoEntity entity = new CanalVendaProdutoEntity();

                entity.setProdutoId(rs.getInt("produtoId"));
                entity.setCanalVendaId(rs.getInt("canalVendaId"));
                entity.setLink(rs.getString("link"));

                return entity;
            };

    @Override
    public List<CanalVendaProdutoEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}
