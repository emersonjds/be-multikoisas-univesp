package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.CanalVendaProdutoEntity;
import org.multikoisas.repository.CanalVendaProdutoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CanalVendaProdutoRepositoryJdbcImpl implements CanalVendaProdutoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT produtoId, \"canalVendaId\", \"link\" FROM public.canal_venda_produto";

    private final RowMapper<CanalVendaProdutoEntity> ROW_MAPPER = (rs, rowNum) -> {
        CanalVendaProdutoEntity canalVendaProdutoEntity = new CanalVendaProdutoEntity();

        canalVendaProdutoEntity.setProdutoId(rs.getInt("produto_id"));
        canalVendaProdutoEntity.setCanalVendaId(rs.getInt("canal_venda_id"));
        canalVendaProdutoEntity.setLink(rs.getString("link"));

        return canalVendaProdutoEntity;
    };

    @Override
    public List<CanalVendaProdutoEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}