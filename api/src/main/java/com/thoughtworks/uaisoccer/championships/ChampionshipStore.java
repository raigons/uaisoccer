package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseStore;
import org.springframework.stereotype.Service;

@Service
public class ChampionshipStore extends BaseStore {

    public Long save(Championship championship) {
        Long id = (Long) getSession().save(championship);
        return id;
    }

    public void update(Championship championship) {
        getSession().update(championship);
    }

}
