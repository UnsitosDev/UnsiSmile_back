package edu.mx.unsis.unsiSmile.service.students;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.GroupMapper;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.repository.students.IGroupRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final IGroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Transactional
    public void createGroup(@NonNull GroupRequest request) {
        try {
            Assert.notNull(request, "GroupRequest cannot be null");

            GroupModel groupModel = groupMapper.toEntity(request);
            groupRepository.save(groupModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GroupResponse getGroupById(@NonNull Long id) {
        try {
            GroupModel groupModel = groupRepository.findById(id)
                    .orElseThrow(() -> new AppException("Group not found with ID: " + id, HttpStatus.NOT_FOUND));
            return groupMapper.toDto(groupModel);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Group not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> getAllGroups() {
        try {
            List<GroupModel> allGroups = groupRepository.findAll();
            return groupMapper.toDtos(allGroups);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch groups", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateGroup(@NonNull Long id, @NonNull GroupRequest updatedGroupRequest) {
        try {
            Assert.notNull(updatedGroupRequest, "Updated GroupRequest cannot be null");

            GroupModel groupModel = groupRepository.findById(id)
                    .orElseThrow(() -> new AppException("Group not found with ID: " + id, HttpStatus.NOT_FOUND));

            groupMapper.updateEntity(updatedGroupRequest, groupModel);

            groupRepository.save(groupModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteGroupById(@NonNull Long id) {
        try {
            if (!groupRepository.existsById(id)) {
                throw new AppException("Group not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            groupRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Group not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete group", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
