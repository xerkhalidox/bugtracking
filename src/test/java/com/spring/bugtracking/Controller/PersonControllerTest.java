package com.spring.bugtracking.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bugtracking.Dto.PersonDto;
import com.spring.bugtracking.Service.PersonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    private static PersonDto personDto;

    @BeforeAll
    static void init() {
        PodamFactory podamFactory = new PodamFactoryImpl();
        personDto = podamFactory.manufacturePojo(PersonDto.class);
    }

    @Test
    void getPerson() throws Exception {
        given(personService.getByEmail(ArgumentMatchers.eq(personDto.getEmail()))).willAnswer(invocation -> personDto);
        ResultActions resultMatcher = mockMvc.perform(get("/person/{email}", personDto.getEmail()));
        resultMatcher
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(personDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(personDto.getEmail())));
    }

    @Test
    void PersonController_AddPerson_ReturnsAddedPerson() throws Exception {
        given(personService.add(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions resultMatcher = mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(personDto)));
        resultMatcher.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(personDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(personDto.getEmail())));
    }

    @Test
    void deletePerson() throws Exception {
        doNothing().when(personService).delete(ArgumentMatchers.anyString());
        ResultActions resultMatcher = mockMvc.perform(delete("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(personDto)));
        resultMatcher.andExpect(MockMvcResultMatchers.status().isOk());
    }
}