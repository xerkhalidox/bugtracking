package com.spring.bugtracking.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Service.BugStatusService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = BugStatusController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BugStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BugStatusService bugStatusService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void BugStatusController_CreateBugStatus_ReturnsCreatedStatus() throws Exception {
        BugStatusDto bugStatusDto = new BugStatusDto();
        bugStatusDto.setName("new");
        given(bugStatusService.add("new")).willAnswer(invocation -> bugStatusDto);
        ResultActions resultActions = mockMvc.perform(put("/bugStatus/add/new"));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(bugStatusDto.getName())));
    }
}