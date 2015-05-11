package com.thoughtworks.uaisoccer.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamControllerTest extends BaseWebIntegrationTest {

    public static String convertObjectToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    @Test
    public void shouldCreateTeamResource() throws Exception {
        Team team = new Team("Cruzeiro Esporte Clube", "cruzeiro");

        mockMvc.perform(
                post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(team))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(nullValue())))
                .andExpect(jsonPath("$.value.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.value.name", is(team.getName())))
                .andExpect(jsonPath("$.value.key", is(team.getKey())));
    }
}
