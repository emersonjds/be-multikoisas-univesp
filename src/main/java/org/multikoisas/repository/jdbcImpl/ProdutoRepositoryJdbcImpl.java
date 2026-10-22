package org.multikoisas.repository.jdbcImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.multikoisas.entities.ProdutoEntity;
import org.multikoisas.repository.ProdutoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryJdbcImpl implements ProdutoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProdutoRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<ProdutoEntity> ROW_MAPPER = (rs, rowNum) -> {
        ProdutoEntity entity = new ProdutoEntity();

        entity.setId(rs.getInt("id"));
        entity.setNome(rs.getString("nome"));
        entity.setDescricaoProduto(rs.getString("descricaoProduto"));

        return entity;
    };

    @Override
    public List<ProdutoEntity> findAll() {
        String sql = """
            SELECT id, nome, "descricaoProduto"
            FROM produto
            """;

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Optional<ProdutoEntity> findById(Integer id) {
        String sql = """
            SELECT id, nome, "descricaoProduto"
            FROM produto
            WHERE id = :id
            """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<ProdutoEntity> result =
                jdbcTemplate.query(sql, params, ROW_MAPPER);

        return result.stream().findFirst();
    }

    @Override
    public ProdutoEntity save(ProdutoEntity entity) {
        String sql = """
            INSERT INTO produto (nome, "descricaoProduto")
            VALUES (:nome, :descricaoProduto)
            RETURNING id
            """;

        Map<String, Object> params = new HashMap<>();
        params.put("nome", entity.getNome());
        params.put("descricaoProduto", entity.getDescricaoProduto());

        Integer id = jdbcTemplate.queryForObject(
                sql,
                params,
                Integer.class
        );

        entity.setId(id);

        return entity;
    }

    @Override
    public boolean update(Integer id, ProdutoEntity entity) {
        String sql = """
            UPDATE produto
            SET nome = :nome,
                "descricaoProduto" = :descricaoProduto
            WHERE id = :id
            """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("nome", entity.getNome());
        params.put("descricaoProduto", entity.getDescricaoProduto());

        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = """
            DELETE FROM produto
            WHERE id = :id
            """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbcTemplate.update(sql, params) > 0;
    }
}
