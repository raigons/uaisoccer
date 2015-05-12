package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.common.BaseController;
import com.thoughtworks.uaisoccer.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

}
