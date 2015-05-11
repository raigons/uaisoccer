package com.thoughtworks.uaisoccer.championships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/championships")
public class ChampionshipController {

    @Autowired
    private ChampionshipStore store;

    @RequestMapping(method = RequestMethod.POST)
    public Championship save(@RequestParam(value = "name") String name){
        Championship championship = new Championship();
        championship.setName(name);
        store.save(championship);
        return championship;
    }
}
