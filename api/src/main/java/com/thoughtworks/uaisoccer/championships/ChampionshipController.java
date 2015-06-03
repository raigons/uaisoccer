package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.Error;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/championships")
@Transactional
public class ChampionshipController extends BaseController<Championship> {

    @Autowired
    private ChampionshipRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Response<Championship> read(@PathVariable("id") Long id) throws ObjectNotFoundException {
        Championship championship = repository.findOne(id);
        if (championship == null)  {
            throw new ObjectNotFoundException(id);
        }
        Response<Championship> response = new Response<>();
        response.setValue(championship);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<Championship> create(@Valid @RequestBody Championship championship) {
        Response<Championship> response = new Response<>();
        repository.saveAndFlush(championship);
        response.setValue(championship);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Championship> update(@PathVariable("id") Long id, @Valid @RequestBody Championship championship) throws ObjectNotFoundException {
        if (!repository.exists(id))
            throw new ObjectNotFoundException(id);

        championship.setId(id);

        Response<Championship> response = new Response<>();
        repository.saveAndFlush(championship);
        response.setValue(championship);

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Championship>> list() throws ObjectNotFoundException {
        List<Championship> championships = repository.findAll();

        return toResponse(championships);
    }

    @RequestMapping(value = "/{id}/teams", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> listTeams(@PathVariable("id") Long id) throws ObjectNotFoundException {
        Championship championship = repository.findOne(id);
        if (championship == null) {
            throw new ObjectNotFoundException(id);
        }

        return toResponse(championship.getTeams());
    }

    @RequestMapping(value = "/{id}/teams", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void associateTeams(@PathVariable("id") Long id, @RequestBody List<Team> teams) throws InvalidTeamsException,
            ObjectNotFoundException {
        Championship championship = repository.findOne(id);
        if (championship == null) {
            throw new ObjectNotFoundException(id);
        }

        validateTeams(teams);

        championship.setTeams(teams);
        repository.saveAndFlush(championship);
    }

    private void validateTeams(List<Team> teams) throws InvalidTeamsException {
        List<Error> errors = new ArrayList<>();

        for (Team team : teams) {
            if (!teamRepository.exists(team.getId())) {
                errors.add(new Error("team.id", String.format("Could not find object with id %d", team.getId())));
                continue;
            }

            Team persistedTeam = teamRepository.findOne(team.getId());
            if (!persistedTeam.isEnabled()) {
                errors.add(new Error("team.enabled", String.format("Could not assign disabled team with id %d to championship", team.getId())));
            }
        }

        if (!errors.isEmpty())
            throw new InvalidTeamsException(errors);
    }

    @ExceptionHandler(value = InvalidTeamsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Response<Championship> nonexistentTeamsHandler(InvalidTeamsException ex) {
        Response<Championship> response = new Response<>();

        for (Error error : ex.getErrors()) {
            response.addError(error.getMessage());
        }

        return response;
    }
}
