package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/teams")
public class TeamController extends BaseController<Team> {

    @Autowired
    TeamStore store;

    @RequestMapping(method = RequestMethod.POST)
    public Response<Team> create(@RequestBody Team team) {
        Response<Team> response = new Response<>();
        store.create(team);
        response.setSuccess(true);
        response.setValue(team);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Team> read(@PathVariable("id") Long id) throws ObjectNotFoundException {
        Response<Team> response = new Response<>();
        response.setValue(store.read(id));
        response.setSuccess(true);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Team> update(@PathVariable("id") Long id, @RequestBody Team team) throws ObjectNotFoundException {
        team.setId(id);

        Response<Team> response = new Response<>();
        store.update(team);
        response.setValue(team);
        response.setSuccess(true);

        return response;
    }
}