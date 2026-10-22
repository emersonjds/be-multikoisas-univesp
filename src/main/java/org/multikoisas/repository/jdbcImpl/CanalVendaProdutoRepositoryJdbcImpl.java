
package org.multikoisas.repository.jdbcImpl;

import java.util.List;
import java.util.Map;

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
            "SELECT \"canalVendaId\", \"produtoId\" FROM public.\"canalVendaProduto\"";

    private static final String INSERT =
            "INSERT INTO public.\"canalVendaProduto\" (\"canalVendaId\", \"produtoId\") " +
                    "VALUES (:canalVendaId, :produtoId)";

    private static final String DELETE =
            "DELETE FROM public.\"canalVendaProduto\" " +
                    "WHERE \"canalVendaId\" = :canalVendaId " +
                    "AND \"produtoId\" = :produtoId";

    private static final String VERIFY =
            "SELECT COUNT(*) FROM public.\"canalVendaProduto\" " +
                    "WHERE \"canalVendaId\" = :canalVendaId " +
                    "AND \"produtoId\" = :produtoId";


    private static final RowMapper<CanalVendaProdutoEntity> ROW_MAPPER = (rs, rowNum) -> {

        CanalVendaProdutoEntity canalVendaProdutoEntity = new CanalVendaProdutoEntity();

        canalVendaProdutoEntity.setCanalVendaId(rs.getInt("canalVendaId"));
        canalVendaProdutoEntity.setProdutoId(rs.getInt("produtoId"));

        return canalVendaProdutoEntity;
    };


    @Override
    public List<CanalVendaProdutoEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }


    @Override
    public List<CanalVendaProdutoEntity> findByProdutoId(Integer produtoId) {

        String SELECT_BY_PRODUCT =
                SELECT_ALL + " WHERE \"produtoId\" IN (:produtoId);";

        return jdbcTemplate.query(
                        SELECT_BY_PRODUCT,
                        Map.of("produtoId", produtoId),
                        ROW_MAPPER
                )
                .stream()
                .map(entity -> CanalVendaProdutoEntity.builder()
                        .canalVendaId(entity.getCanalVendaId())
                        .produtoId(entity.getProdutoId())
                        .build())
                .toList();
    }


    @Override
    public List<CanalVendaProdutoEntity> findByCanalVendaId(Integer canalVendaId) {

        String SELECT_BY_CANAL_VENDA =
                SELECT_ALL + " WHERE \"canalVendaId\" IN (:canalVendaId);";

        return jdbcTemplate.query(
                        SELECT_BY_CANAL_VENDA,
                        Map.of("canalVendaId", canalVendaId),
                        ROW_MAPPER
                )
                .stream()
                .map(entity -> CanalVendaProdutoEntity.builder()
                        .canalVendaId(entity.getCanalVendaId())
                        .produtoId(entity.getProdutoId())
                        .build())
                .toList();
    }


    @Override
    public CanalVendaProdutoEntity save(CanalVendaProdutoEntity entity) {

        jdbcTemplate.update(
                INSERT,
                Map.of(
                        "canalVendaId", entity.getCanalVendaId(),
                        "produtoId", entity.getProdutoId()
                )
        );

        return entity;
    }


    @Override
    public int delete(Integer produtoId, Integer canalVendaId) {

        return jdbcTemplate.update(
                DELETE,
                Map.of(
                        "produtoId", produtoId,
                        "canalVendaId", canalVendaId
                )
        );
    }


    @Override
    public boolean exists(Integer produtoId, Integer canalVendaId) {

        Integer count = jdbcTemplate.queryForObject(
                VERIFY,
                Map.of(
                        "produtoId", produtoId,
                        "canalVendaId", canalVendaId
                ),
                Integer.class
        );
        return count != null && count > 0;
    }

}
