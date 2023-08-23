package com.spring.bugtracking.Service;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Entity.BugStatus;
import com.spring.bugtracking.Repository.BugStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BugStatusServiceTest {
    @Mock
    private BugStatusRepository bugStatusRepository;

    @InjectMocks
    private BugStatusService bugStatusService;

    private static PodamFactory podamFactory;

    @BeforeAll
    static void init() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void BugStatusService_AddEmptyBugStatusName_ReturnsNull() {
        BugStatusDto bugStatusDto = bugStatusService.add(" ");
        Assertions.assertNull(bugStatusDto);
    }

    @Test
    public void BugStatusService_AddStatus_ReturnsAddedStatus() {
        BugStatus bugStatus1 = podamFactory.manufacturePojo(BugStatus.class);
        BugStatus bugStatus2 = podamFactory.manufacturePojo(BugStatus.class);

        when(bugStatusRepository.getBugStatusByStatusName(Mockito.anyString())).thenReturn(Optional.empty());
        when(bugStatusRepository.save(Mockito.any(BugStatus.class))).thenReturn(bugStatus1);
        BugStatusDto savedBugStatus1 = bugStatusService.add(bugStatus1.getStatusName());

        when(bugStatusRepository.getBugStatusByStatusName(Mockito.anyString())).thenReturn(Optional.empty());
        when(bugStatusRepository.save(Mockito.any(BugStatus.class))).thenReturn(bugStatus2);
        BugStatusDto savedBugStatus2 = bugStatusService.add(bugStatus2.getStatusName());

        Assertions.assertNotNull(savedBugStatus1);
        Assertions.assertNotNull(savedBugStatus2);

        Assertions.assertEquals(savedBugStatus1.getName(), bugStatus1.getStatusName());
        Assertions.assertEquals(savedBugStatus2.getName(), bugStatus2.getStatusName());
    }

    @Test
    public void BugStatusService_AddExistedStatus_NotAddingIt() {
        BugStatus bugStatus1 = podamFactory.manufacturePojo(BugStatus.class);
        BugStatus bugStatus2 = podamFactory.manufacturePojo(BugStatus.class);

        when(bugStatusRepository.getBugStatusByStatusName(Mockito.anyString())).thenReturn(Optional.empty());
        when(bugStatusRepository.save(Mockito.any(BugStatus.class))).thenReturn(bugStatus1);
        BugStatusDto savedBugStatus1 = bugStatusService.add(bugStatus1.getStatusName());

        when(bugStatusRepository.getBugStatusByStatusName(Mockito.anyString())).thenReturn(Optional.of(bugStatus1));
        BugStatusDto savedBugStatus2 = bugStatusService.add(bugStatus1.getStatusName());

        when(bugStatusRepository.getBugStatusByStatusName(Mockito.anyString())).thenReturn(Optional.empty());
        when(bugStatusRepository.save(Mockito.any(BugStatus.class))).thenReturn(bugStatus2);
        BugStatusDto savedBugStatus3 = bugStatusService.add(bugStatus2.getStatusName());

        Assertions.assertNotNull(savedBugStatus1);
        Assertions.assertNotNull(savedBugStatus2);
        Assertions.assertNotNull(savedBugStatus3);

        Assertions.assertEquals(savedBugStatus1.getName(), bugStatus1.getStatusName());
        Assertions.assertEquals(savedBugStatus2.getName(), bugStatus1.getStatusName());
        Assertions.assertEquals(savedBugStatus3.getName(), bugStatus2.getStatusName());
    }
}