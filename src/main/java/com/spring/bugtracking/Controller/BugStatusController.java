package com.spring.bugtracking.Controller;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Service.BugStatusService;
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
    @ResponseStatus(HttpStatus.OK)
    public List<BugStatusDto> getAllBugStatuses() {
        return bugStatusService.getAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BugStatusDto> addBugStatus(@RequestBody BugStatusDto bugStatusDto) {
        return new ResponseEntity<>(bugStatusService.add(bugStatusDto.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteStatus(@RequestBody BugStatusDto bugStatusDto) {
        bugStatusService.deleteStatus(bugStatusDto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
