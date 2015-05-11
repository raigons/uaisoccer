package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    TeamStore store;

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public Response<Team> create(@RequestBody Team team) {
        Response<Team> response = new Response<>();

        store.create(team);

        response.setSuccess(true);
        response.setValue(team);
        return response;
    }
}
