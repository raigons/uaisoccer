package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.InvalidTeamNameException;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/teams")
@Transactional
public class TeamController extends BaseController<Team> {

    @Autowired
    private TeamRepository teamRepository;

    TeamKeyGenerator keyGenerator = new TeamKeyGenerator();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Team> read(@PathVariable("id") Long id) throws ObjectNotFoundException {
        Response<Team> response = new Response<>();

        Team team  = teamRepository.findOne(id);
        if(team == null)
            throw new ObjectNotFoundException(id);

        response.setValue(teamRepository.findOne(id));

        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<Team> create(@Valid @RequestBody Team team) throws InvalidTeamNameException {
        team.setKey(keyGenerator.generateKeyFromName(team.getName()));

        Response<Team> response = new Response<>();
        teamRepository.saveAndFlush(team);
        response.setValue(team);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Team> update(@PathVariable("id") Long id, @Valid @RequestBody Team team) throws ObjectNotFoundException,
            InvalidTeamNameException {
        if(!teamRepository.exists(id))
            throw new ObjectNotFoundException(id);

        team.setId(id);
        team.setKey(keyGenerator.generateKeyFromName(team.getName()));
        teamRepository.saveAndFlush(team);

        Response<Team> response = new Response<>();
        response.setValue(team);

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Team>> list(@RequestParam(required=false) String key) {
        List<Team> teams = new ArrayList<Team>();
        if (key == null) {
            teams = teamRepository.findAll();
        } else {
            Team team = teamRepository.findByKey(key);
            if(team != null)
                teams.add(team);
        }

        return toResponse(teams);
    }

    @ExceptionHandler(value = InvalidTeamNameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Response<Team> invalidNameHandler(InvalidTeamNameException ex) {
        Response<Team> response = new Response<>();
        response.addError(ex.getMessage(), "name");

        return response;
    }
}
