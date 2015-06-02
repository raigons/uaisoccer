package com.thoughtworks.uaisoccer.championships;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Championship findByName(String name);
}
