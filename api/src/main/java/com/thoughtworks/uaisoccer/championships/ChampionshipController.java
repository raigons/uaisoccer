package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/championships")
@Transactional
public class ChampionshipController extends BaseController<Championship> {

    @Autowired
    private ChampionshipRepository championshipRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Response<Championship> create(@Valid @RequestBody Championship championship) {
        Response<Championship> response = new Response<>();
        championshipRepository.save(championship);
        response.setValue(championship);

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Championship> update(@PathVariable("id") Long id, @Valid @RequestBody Championship championship) throws ObjectNotFoundException {
        if (!championshipRepository.exists(id))
            throw new ObjectNotFoundException("Could not find object");

        championship.setId(id);

        Response<Championship> response = new Response<>();
        championshipRepository.save(championship);
        response.setValue(championship);

        return response;
    }
}
