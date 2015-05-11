package com.thoughtworks.uaisoccer.championships;

import com.thoughtworks.uaisoccer.BaseWebIntegrationTest;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChampionshipControllerTest extends BaseWebIntegrationTest {

    @Test
    public void shouldCreateANewChampionship() throws Exception {
        mockMvc.perform(post("/championships")
            .param("name", "Brasileirao"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Brasileirao")))
            .andExpect(jsonPath("$.id", is(not(Null.NULL))));
    }
}