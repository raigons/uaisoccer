package com.thoughtworks.uaisoccer.teams;


import com.thoughtworks.uaisoccer.teams.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.transaction.annotation.Transactional
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
    Team findByKey(String key);
}
