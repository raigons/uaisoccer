package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import com.thoughtworks.uaisoccer.teams.Team;
import com.thoughtworks.uaisoccer.teams.TeamBuilder;
import com.thoughtworks.uaisoccer.teams.TeamRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/test-application-context.xml"
})
@DirtiesContext
public class ChampionshipControllerTest extends BaseWebIntegrationTest {

    static final String EMPTY_MESSAGE = "may not be empty";
    static final String NUMBER_MESSAGE = "cannot be a number";

    @Autowired
    ChampionshipRepository championshipRepository;

    @Autowired
    TeamRepository teamRepository;

    Championship championship;
    Team atleticoMineiro;
    Team cruzeiro;
    Team america;
    Team internacional;

    @Before
    public void setUp() {
        atleticoMineiro = new TeamBuilder()
                .withName("Atlético Mineiro")
                .withKey("atletico-mineiro")
                .withEnabled(true)
                .build();
        teamRepository.save(atleticoMineiro);

        cruzeiro = new TeamBuilder()
                .withName("Cruzeiro")
                .withKey("cruzeiro")
                .withEnabled(true)
                .build();
        teamRepository.save(cruzeiro);

        america = new TeamBuilder()
                .withName("America")
                .withKey("america")
                .withEnabled(true)
                .build();
        teamRepository.save(america);

        internacional = new TeamBuilder()
                .withName("Internacional")
                .withKey("internacional")
                .withEnabled(true)
                .build();
        teamRepository.save(internacional);

        championship = new ChampionshipBuilder()
                .withName("Libertadores")
                .build();
        championshipRepository.save(championship);
    }

    @Test
    public void shouldReadChampionshipResource() throws Exception {

        mockMvc.perform(get("/championships/" + championship.getId())
            .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(championship.getId().intValue())))
                .andExpect(jsonPath("$.name", is(championship.getName())));
    }

    @Test
    public void shouldFailWith404NotFoundWhenReadingNonexistentChampionshipResource() throws Exception {
        Long fakeId = 9999999999L;

        mockMvc.perform(get("/championships/" + fakeId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message", containsString("Could not find object")));
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
            .andExpect(jsonPath("$.errors").doesNotExist())
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
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)))
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
                .andExpect(jsonPath("$.errors").doesNotExist())
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
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }


    @Test
    public void shouldUpdateExistingChampionshipResource() throws Exception {
        championship.setName("Campeonato Mineiro");

        mockMvc.perform(put("/championships/" + championship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id", Matchers.is(championship.getId().intValue())))
            .andExpect(jsonPath("$.name", Matchers.is(championship.getName())));
    }

    @Test
    public void shouldFailWithHttp404NotFoundWhenUpdatingNonExistentChampionship() throws Exception {
        Long fakeId = 999999999L;

        mockMvc.perform(put("/championships/" + fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.errors[0].message", containsString("Could not find object with id: " + fakeId)))
            .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsNullForUpdate() throws Exception {
        championship.setName(null);

        mockMvc.perform(put("/championships/" + championship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsBlankForUpdate() throws Exception {
        championship.setName("       ");

        mockMvc.perform(put("/championships/" + championship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }


    @Test
    public void shouldReturn400WhenChampionshipNameIsEmptyForUpdate() throws Exception {
        championship.setName("");

        mockMvc.perform(put("/championships/" + championship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(EMPTY_MESSAGE)));
    }

    @Test
    public void shouldReturn400WhenChampionshipNameIsANumberForUpdate() throws Exception {
        championship.setName("1234");

        mockMvc.perform(put("/championships/" + championship.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(championship))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is(NUMBER_MESSAGE)));
    }

    @Test
    public void shouldListMultipleTeamsAssociatedToChampionship() throws Exception {
        championship.addTeam(atleticoMineiro);
        championship.addTeam(cruzeiro);
        championship.addTeam(internacional);
        championship.addTeam(america);
        championshipRepository.save(championship);

        mockMvc.perform(get("/championships/" + championship.getId() + "/teams")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void shouldListOneTeamAssociatedToChampionship() throws Exception {
        championship.addTeam(atleticoMineiro);
        championshipRepository.save(championship);

        mockMvc.perform(get("/championships/" + championship.getId() + "/teams")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(atleticoMineiro.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(atleticoMineiro.getName())));
    }

    @Test
    public void shouldReturn204NoContentWhenChampionshipHasNoAssociatedTeams() throws Exception {
        championshipRepository.save(championship);

        mockMvc.perform(get("/championships/" + championship.getId() + "/teams")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(isEmptyOrNullString()));
    }
}
