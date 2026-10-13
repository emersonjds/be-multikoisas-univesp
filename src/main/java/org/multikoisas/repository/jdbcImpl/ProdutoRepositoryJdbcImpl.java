package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.ProdutoEntity;
import org.multikoisas.repository.ProdutoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProdutoRepositoryJdbcImpl implements ProdutoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT id, \"nome\", \"descricaoProduto\" FROM public.produto";

    private final RowMapper<ProdutoEntity> ROW_MAPPER = (rs, rowNum) -> {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(rs.getInt("id"));
        produtoEntity.setNome(rs.getString("nome"));
        produtoEntity.setDescricaoProduto(rs.getString("descricao_produto"));

        return produtoEntity;
    };

    @Override
    public List<ProdutoEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}
