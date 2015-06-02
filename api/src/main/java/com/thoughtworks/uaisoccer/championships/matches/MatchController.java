package com.thoughtworks.uaisoccer.championships.matches;

import com.thoughtworks.uaisoccer.championships.Championship;
import com.thoughtworks.uaisoccer.championships.ChampionshipRepository;
import com.thoughtworks.uaisoccer.championships.matches.generation.AllAgainstAllMatchGenerator;
import com.thoughtworks.uaisoccer.championships.matches.generation.MatchGenerationException;
import com.thoughtworks.uaisoccer.championships.matches.generation.MatchGenerator;
import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/championships/{championshipId}/matches")
@Transactional
public class MatchController extends BaseController<Match> {

    @Autowired
    ChampionshipRepository championshipRepository;

    MatchGenerator matchGenerator = new AllAgainstAllMatchGenerator();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Match>> list(@PathVariable("championshipId") Long championshipId) throws MatchGenerationException, ObjectNotFoundException {
        Championship championship = championshipRepository.findOne(championshipId);
        if (championship == null) {
            throw new ObjectNotFoundException("Could not find object");
        }

        return toResponse(matchGenerator.generateMatches(championship));
    }

    @ExceptionHandler(value = MatchGenerationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Response<Match> matchExceptionHandler(MatchGenerationException ex) {
        Response<Match> response = new Response<>();
        response.addError(ex.getMessage());

        return response;
    }

}