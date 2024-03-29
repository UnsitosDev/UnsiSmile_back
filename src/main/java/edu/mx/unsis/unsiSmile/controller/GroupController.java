package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.GroupResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/groups")
@Slf4j
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest request) {
        try {
            GroupResponse createdGroup = groupService.createGroup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
        } catch (AppException ex) {
            log.error("Failed to create group: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        try {
            GroupResponse groupResponse = groupService.getGroupById(id);
            return ResponseEntity.ok(groupResponse);
        } catch (AppException ex) {
            log.error("Failed to fetch group: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        try {
            List<GroupResponse> allGroups = groupService.getAllGroups();
            return ResponseEntity.ok(allGroups);
        } catch (AppException ex) {
            log.error("Failed to fetch groups: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long id, @RequestBody GroupRequest updatedGroupRequest) {
        try {
            GroupResponse updatedGroup = groupService.updateGroup(id, updatedGroupRequest);
            return ResponseEntity.ok(updatedGroup);
        } catch (AppException ex) {
            log.error("Failed to update group: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Long id) {
        try {
            groupService.deleteGroupById(id);
            return ResponseEntity.noContent().build();
        } catch (AppException ex) {
            log.error("Failed to delete group: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }
}
