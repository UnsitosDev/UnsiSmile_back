package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.GroupResponse;
import edu.mx.unsis.unsiSmile.service.GroupService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest request) {
        GroupResponse createdGroup = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);

    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@Valid @PathVariable Long id) {

        GroupResponse groupResponse = groupService.getGroupById(id);
        return ResponseEntity.ok(groupResponse);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {

        List<GroupResponse> allGroups = groupService.getAllGroups();
        return ResponseEntity.ok(allGroups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@Valid @PathVariable Long id,
    @Valid @RequestBody GroupRequest updatedGroupRequest) {

        GroupResponse updatedGroup = groupService.updateGroup(id, updatedGroupRequest);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroupById(@Valid @PathVariable Long id) {

        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }
}
