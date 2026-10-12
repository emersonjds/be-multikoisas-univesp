package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.CategoriaProdutoEntity;
import org.multikoisas.repository.CategoriaProdutoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoriaProdutoRepositoryJdbcImpl implements CategoriaProdutoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT categoriaId, \"produtoId\" FROM public.categoria_produto";

    private final RowMapper<CategoriaProdutoEntity> ROW_MAPPER = (rs, rowNum) -> {
        CategoriaProdutoEntity categoriaProdutoEntity = new CategoriaProdutoEntity();

        categoriaProdutoEntity.setCategoriaId(rs.getInt("categoria_id"));
        categoriaProdutoEntity.setProdutoId(rs.getInt("produto_id"));

        return categoriaProdutoEntity;
    };

    @Override
    public List<CategoriaProdutoEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }
}