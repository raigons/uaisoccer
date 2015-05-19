package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseStore;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

@Service
public class TeamStore extends BaseStore<Team> {

    public Team findByKey(String key) {
        Criteria criteria = getSession().createCriteria(Team.class);
        criteria.add(Restrictions.eq("key", key));
        Team foundTeam = (Team) criteria.uniqueResult();

        return foundTeam;
    }
}
