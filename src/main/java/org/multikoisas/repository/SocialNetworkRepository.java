package org.multikoisas.repository;

import org.multikoisas.entities.SocialNetworkEntity;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SocialNetworkRepository {

     List<SocialNetworkEntity> findAll();
}
