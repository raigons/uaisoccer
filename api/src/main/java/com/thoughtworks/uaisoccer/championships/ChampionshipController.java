package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/championships")
public class ChampionshipController extends BaseController<Championship> {

    @Autowired
    private ChampionshipStore store;

    @RequestMapping(method = RequestMethod.POST)
    public Response<Championship> create(@RequestBody Championship championship) {
        Response<Championship> response = new Response<>();
        store.create(championship);
        response.setValue(championship);
        response.setSuccess(true);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Championship> update(@PathVariable("id") Long id, @RequestBody Championship championship) throws ObjectNotFoundException {
        championship.setId(id);

        Response<Championship> response = new Response<>();
        store.update(championship);
        response.setValue(championship);
        response.setSuccess(true);

        return response;
    }
}
