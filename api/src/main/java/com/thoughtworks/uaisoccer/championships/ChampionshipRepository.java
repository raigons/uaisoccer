package com.thoughtworks.uaisoccer.championships;


import com.thoughtworks.uaisoccer.championships.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.transaction.annotation.Transactional
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Championship findByName(String name);
}
