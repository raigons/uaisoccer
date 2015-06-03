package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import com.thoughtworks.uaisoccer.teams.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/championships")
@Transactional
public class ChampionshipController extends BaseController<Championship> {

    @Autowired
    private ChampionshipRepository repository;

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
        repository.save(championship);
        response.setValue(championship);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Championship> update(@PathVariable("id") Long id, @Valid @RequestBody Championship championship) throws ObjectNotFoundException {
        if (!repository.exists(id))
            throw new ObjectNotFoundException(id);

        championship.setId(id);

        Response<Championship> response = new Response<>();
        repository.save(championship);
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
    public void associateTeams(@PathVariable("id") Long id, @RequestBody List<Team> teams) throws NonexistentTeamsException,
            ObjectNotFoundException {
        Championship championship = repository.findOne(id);
        if (championship == null) {
            throw new ObjectNotFoundException(id);
        }

        repository.associateTeamsToChampionship(teams, championship);
    }

    @ExceptionHandler(value = NonexistentTeamsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected Response<Championship> nonexistentTeamshandler(NonexistentTeamsException ex) {
        Response<Championship> response = new Response<>();

        for (Long teamId : ex.getIds()) {
            response.addError(String.format("Could not find team with id $d", teamId));
        }

        return response;
    }
}
