package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class ChampionshipControllerTest extends BaseWebIntegrationTest {

    @Autowired
    ChampionshipStore store;

    Championship fixtureChampionship;

    static final String EMPTY_MESSAGE= "may not be empty";
    static final String NUMBER_MESSAGE= "cannot be a number";


    @Before
    public void setUp() {
        fixtureChampionship = new Championship();
        fixtureChampionship.setName("Libertadores");
        store.create(fixtureChampionship);
    }

    @Test
    public void shouldCreateChampionshipResource() throws Exception {
        Championship championship = new Championship();
        championship.setName("Brasileirão");

        mockMvc.perform(post("/championships")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(convertObjectToJson(championship))
                )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.id", Matchers.is(greaterThan(0))))
            .andExpect(jsonPath("$.name", Matchers.is(championship.getName())));
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsBlank() throws Exception {
        Championship championship = new Championship();
        championship.setName("   ");

        mockMvc.perform(post("/championships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist());
    }


    @Test
    public void shouldReturn400WhenChampionshipNameIsNull() throws Exception {
        Championship championship = new Championship();

        mockMvc.perform(post("/championships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }


    @Test
    public void shouldReturn400ChampionshipNameIsANumber() throws Exception {
        Championship championship = new Championship();
        championship.setName("1234");

        mockMvc.perform(post("/championships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(NUMBER_MESSAGE)));
    }


    @Test
    public void shouldReturn201WhenChampionshipNameIsNotANumber() throws Exception {
        Championship championship = new Championship();
        championship.setName("2na2me2");

        mockMvc.perform(post("/championships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").doesNotExist())
                .andExpect(jsonPath("$.id", Matchers.is(greaterThan(0))))
                .andExpect(jsonPath("$.name", Matchers.is(championship.getName())));

    }


    @Test
    public void shouldReturn400WhenChampionshipNameIsEmpty() throws Exception {
        Championship championship = new Championship();
        championship.setName("");

        mockMvc.perform(post("/championships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }


    @Test
    public void shouldUpdateExistingChampionshipResource() throws Exception {
        fixtureChampionship.setName("Campeonato Mineiro");

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.id", Matchers.is(fixtureChampionship.getId().intValue())))
            .andExpect(jsonPath("$.name", Matchers.is(fixtureChampionship.getName())));
    }

    @Test
    public void shouldFailWithHttp404NotFoundWhenUpdatingNonexistentChampionship() throws Exception {
        Long fakeId = 999999999L;

        mockMvc.perform(put("/championships/" + fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", containsString("Could not find object")))
            .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsNullForUpdate() throws Exception {
        fixtureChampionship.setName(null);

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsBlankForUpdate() throws Exception {
        fixtureChampionship.setName("       ");

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }


    @Test
    public void shouldReturn400WhenChampionshipNameIsEmptyForUpdate() throws Exception {
        fixtureChampionship.setName("");

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsANumberForUpdate() throws Exception {
        fixtureChampionship.setName("1234");

        mockMvc.perform(put("/championships/" + fixtureChampionship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(fixtureChampionship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Could not execute request due to validation errors")))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(NUMBER_MESSAGE)));
    }
}
