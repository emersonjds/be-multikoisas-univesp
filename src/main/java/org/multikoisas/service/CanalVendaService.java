
package org.multikoisas.service;

import java.util.List;

import org.multikoisas.dto.CanalVendaRequest;
import org.multikoisas.dto.CanalVendaResponse;

public interface CanalVendaService {

    List<CanalVendaResponse> findAll();

    CanalVendaResponse findById(Integer id);

    CanalVendaResponse save(CanalVendaRequest request);

    boolean update(Integer id, CanalVendaRequest request);

    boolean deleteById(Integer id);
}
