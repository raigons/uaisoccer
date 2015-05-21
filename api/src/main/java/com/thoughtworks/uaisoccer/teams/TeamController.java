package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/teams")
@Transactional
public class TeamController extends BaseController<Team> {

    @Autowired
    TeamStore store;

    TeamKeyGenerator keyGenerator = new TeamKeyGenerator();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Team> read(@PathVariable("id") Long id) throws ObjectNotFoundException {
        Response<Team> response = new Response<>();
        response.setValue(store.read(id));

        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<Team> create(@RequestBody Team team) {
        team.setKey(keyGenerator.generateKeyFromName(team.getName()));

        Response<Team> response = new Response<>();
        store.create(team);
        response.setValue(team);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Team> update(@PathVariable("id") Long id, @RequestBody Team team) throws ObjectNotFoundException {
        team.setId(id);

        Response<Team> response = new Response<>();
        store.update(team);
        response.setValue(team);

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Team>> list(@RequestParam(required=false) String key) {
        List<Team> teams;
        if (key == null) {
            teams = store.list();
        } else {
            teams = findByKey(key);
        }
        return toResponse(teams);
    }

    private List<Team> findByKey(@RequestParam String key) {
        List<Team> teams = new ArrayList<>();
        Team team = store.findByKey(key);
        if (team != null) {
            teams.add(team);
        }
        return teams;
    }
}
