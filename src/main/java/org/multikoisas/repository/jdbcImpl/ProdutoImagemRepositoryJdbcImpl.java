package org.multikoisas.repository.jdbcImpl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.entities.ProdutoImagemEntity;
import org.multikoisas.repository.ProdutoImagemRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProdutoImagemRepositoryJdbcImpl implements ProdutoImagemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL = "SELECT id, \"produtoId\", \"imagemUrl\" FROM public.\"produtoImagem\"";
    private final String SELECT_BY_ID = SELECT_ALL + " WHERE id = :id";
    private final String SELECT_BY_PRODUCT = SELECT_ALL + " WHERE \"produtoId\" = :produtoId";
    private final String INSERT = "INSERT INTO public.\"produtoImagem\" (\"produtoId\", \"imagemUrl\") VALUES (:produtoId, :imagemUrl)";
    private final String DELETE_BY_PRODUCT = "DELETE FROM public.\"produtoImagem\" WHERE \"produtoId\" = :produtoId";
    private final String DELETE_BY_ID = "DELETE FROM public.\"produtoImagem\" WHERE id = :id";

    private final RowMapper<ProdutoImagemEntity> ROW_MAPPER = (rs , rowNum) -> {
        ProdutoImagemEntity produtoImagemEntity = new ProdutoImagemEntity();
        produtoImagemEntity.setId(rs.getInt("id"));
        produtoImagemEntity.setProdutoId(rs.getInt("produtoId"));
        produtoImagemEntity.setImagemUrl(rs.getString("imagemUrl"));
        return produtoImagemEntity;
    };


    //todo parei aqui
    @Override
    public List<ProdutoImagemEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL, ROW_MAPPER);
    }

    @Override
    public ProdutoImagemEntity findById(Integer productId) {
        return null;
    }

    @Override
    public List<ProdutoImagemEntity> findByProduct(Integer productId) {
        return List.of();
    }

    @Override
    public ProdutoImagemEntity save(ProdutoImagemEntity entity) {
        String INSERT_IMAGEM = INSERT + " RETURNING id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        Integer generatedId = jdbcTemplate.queryForObject(INSERT_IMAGEM, params, Integer.class);
        entity.setId(generatedId);
        return entity;
    }

    @Override
    public boolean deleteByProduct(ProdutoImagemEntity entity) {
        return false;
    }
}
