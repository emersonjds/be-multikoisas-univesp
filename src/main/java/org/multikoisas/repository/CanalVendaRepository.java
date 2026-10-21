package org.multikoisas.repository;

import java.util.List;
import java.util.Optional;

import org.multikoisas.entities.CanalVendaEntity;

public interface CanalVendaRepository {
    List<CanalVendaEntity> findAll();
    Optional<CanalVendaEntity> findById(Integer id);
    CanalVendaEntity save(CanalVendaEntity entity);
    boolean update(Integer id, CanalVendaEntity entity);
    boolean deleteById(Integer id);
}