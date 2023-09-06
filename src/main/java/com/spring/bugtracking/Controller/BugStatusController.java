package com.spring.bugtracking.Controller;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Service.BugStatusService;
import com.spring.bugtracking.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bugStatus")
public class BugStatusController {
    @Autowired
    private BugStatusService bugStatusService;

    @GetMapping("/")
    public List<BugStatusDto> getAllBugStatuses() {
        try {
            return bugStatusService.getAll();
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<BugStatusDto> addBugStatus(@RequestBody BugStatusDto bugStatusDto) {
        try {
            return new ResponseEntity<>(bugStatusService.add(bugStatusDto.getName()), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteStatus(@RequestBody BugStatusDto bugStatusDto) {
        try {
            bugStatusService.deleteStatus(bugStatusDto.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
