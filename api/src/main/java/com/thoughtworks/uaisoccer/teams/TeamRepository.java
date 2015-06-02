package com.thoughtworks.uaisoccer.teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
    Team findByKey(String key);
}
