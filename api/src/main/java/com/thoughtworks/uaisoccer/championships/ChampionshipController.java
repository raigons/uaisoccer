package com.thoughtworks.uaisoccer.championships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/championships")
public class ChampionshipController {

    @Autowired
    private ChampionshipStore store;

    @RequestMapping(method = RequestMethod.POST)
    public Championship save(@RequestParam(value = "name") String name){
        Championship championship = new Championship();
        championship.setName(name);
        store.create(championship);
        return championship;
    }

    @RequestMapping(value =  "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void update(@PathVariable(value = "id") Long id, @RequestBody Championship championship) throws Exception {
//        if(id != championship.getId()) throw new ChampionshipException();

//        championship.setId(id);

        System.out.println(id);

        store.update(championship);
    }
}
