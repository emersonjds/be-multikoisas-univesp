
package org.multikoisas.service;

import java.util.List;

import org.multikoisas.dto.ProdutoRequest;
import org.multikoisas.dto.ProdutoResponse;

public interface ProdutoService {

    List<ProdutoResponse> findAll();

    ProdutoResponse findById(Integer id);

    ProdutoResponse save(ProdutoRequest request);

    boolean update(Integer id, ProdutoRequest request);

    boolean deleteById(Integer id);
}
