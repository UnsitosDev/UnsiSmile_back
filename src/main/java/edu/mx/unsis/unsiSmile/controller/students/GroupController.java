package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.service.students.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Valid> createGroup(@Valid @RequestBody GroupRequest request) {
        groupService.createGroup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<Void> updateGroup(@Valid @PathVariable Long id,
    @Valid @RequestBody GroupRequest updatedGroupRequest) {
        groupService.updateGroup(id, updatedGroupRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroupById(@Valid @PathVariable Long id) {

        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/career/{careerId}")
    public ResponseEntity<List<GroupResponse>> getGroupsByCareer(@PathVariable String careerId) {
        List<GroupResponse> groups = groupService.getGroupsByCareer(careerId);
        return ResponseEntity.ok(groups);
    }
}