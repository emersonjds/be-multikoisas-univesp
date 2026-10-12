package org.multikoisas.repository;

import org.multikoisas.entities.CategoriaEntity;

import java.util.List;

public interface CategoriaRepository {

    List<CategoriaEntity> findAll();

}