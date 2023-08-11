package com.spring.bugtracking.Controller;

import com.spring.bugtracking.Dto.BugStatusDto;
import com.spring.bugtracking.Entity.BugStatus;
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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BugStatus> getAllBugStatuses() {
        return bugStatusService.getAll();
    }

    @PutMapping("/add/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BugStatusDto> addBugStatus(@PathVariable String name) {
        return new ResponseEntity<>(bugStatusService.add(name), HttpStatus.CREATED);
    }
}
