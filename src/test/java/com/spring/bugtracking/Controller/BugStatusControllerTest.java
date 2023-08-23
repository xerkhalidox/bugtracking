package com.spring.bugtracking.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Service.BugStatusService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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

    private static BugStatusDto bugStatusDto;

    @BeforeAll
    static void init() {
        PodamFactory podamFactory = new PodamFactoryImpl();
        bugStatusDto = podamFactory.manufacturePojo(BugStatusDto.class);
    }

    @Test
    public void BugStatusController_GetAllBugStatuses_ReturnsBugStatuses() throws Exception {
        List<BugStatusDto> bugStatusDtoList = new ArrayList<>();
        bugStatusDtoList.add(bugStatusDto);
        given(bugStatusService.getAll()).willAnswer(invocation -> bugStatusDtoList);
        ResultActions resultActions = mockMvc.perform(get("/bugStatus/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bugStatusDtoList)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(bugStatusDto.getName())));
    }

    @Test
    public void BugStatusController_CreateBugStatus_ReturnsCreatedStatus() throws Exception {
        given(bugStatusService.add(ArgumentMatchers.anyString())).willAnswer(invocation -> bugStatusDto);
        ResultActions resultActions = mockMvc.perform(post("/bugStatus/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bugStatusDto)));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(bugStatusDto.getName())));
    }

    @Test
    public void BugStatusController_DeleteBugStatus_ReturnsOkHttpStatus() throws Exception {
        doNothing().when(bugStatusService).deleteStatus(ArgumentMatchers.anyString());
        ResultActions resultActions = mockMvc.perform(delete("/bugStatus/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bugStatusDto)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}