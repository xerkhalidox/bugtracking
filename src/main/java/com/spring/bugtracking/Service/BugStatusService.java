package com.spring.bugtracking.Service;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Entity.BugStatus;
import com.spring.bugtracking.Repository.BugStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BugStatusService {
    @Autowired
    private BugStatusRepository bugStatusRepository;

    public BugStatusDto add(String name) {
        if (name == null || (name.trim().isEmpty())) return null;
        BugStatus bugStatus = bugStatusRepository.getBugStatusByStatusName(name).orElse(null);
        if (bugStatus != null) {
            return mapBugStatusToDto(bugStatus);
        }
        bugStatus = new BugStatus();
        bugStatus.setStatusName(name);
        bugStatus = bugStatusRepository.save(bugStatus);
        return mapBugStatusToDto(bugStatus);
    }

    public List<BugStatusDto> getAll() {
        List<BugStatus> bugStatusesList = bugStatusRepository.findAll();
        List<BugStatusDto> bugStatusDtoList = new ArrayList<>();
        for (BugStatus bugStatus: bugStatusesList) {
            bugStatusDtoList.add(mapBugStatusToDto(bugStatus));
        }
        return bugStatusDtoList;
    }

    @Transactional
    public void deleteStatus(String name) {
        bugStatusRepository.deleteBugStatusByStatusName(name);
    }

    private BugStatusDto mapBugStatusToDto(BugStatus bugStatus) {
        BugStatusDto bugStatusDto = new BugStatusDto();
        bugStatusDto.setName(bugStatus.getStatusName());
        bugStatusDto.setId(bugStatus.getId());
        return bugStatusDto;
    }
}
