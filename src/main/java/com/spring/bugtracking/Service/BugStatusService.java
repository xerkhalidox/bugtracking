package com.spring.bugtracking.Service;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Entity.BugStatus;
import com.spring.bugtracking.Repository.BugStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BugStatus> getAll() {
        try {
            return bugStatusRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    private BugStatusDto mapBugStatusToDto(BugStatus bugStatus) {
        BugStatusDto bugStatusDto = new BugStatusDto();
        bugStatusDto.setName(bugStatus.getStatusName());
        bugStatusDto.setId(bugStatus.getId());
        return bugStatusDto;
    }
}
