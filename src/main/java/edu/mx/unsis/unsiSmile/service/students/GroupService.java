package edu.mx.unsis.unsiSmile.service.students;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.GroupMapper;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import edu.mx.unsis.unsiSmile.repository.students.IGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.ICareerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final IGroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ICareerRepository careerRepository;
    private final SemesterService semesterService;

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

    @Transactional
    public Map<String, GroupModel> saveGroups(List<String> groups) {
        Set<String> uniqueGroupsSet = new HashSet<>(groups);
        groupRepository.disableAllGroups();
        return mapToGroupModels(uniqueGroupsSet);
    }

    private Map<String, GroupModel> mapToGroupModels(Set<String> uniqueGroups) {
        Map<String, GroupModel> groupMap = new HashMap<>();

        try {
            for (String groupFormat : uniqueGroups) {
                GroupModel groupModel = new GroupModel();

                String[] parts = groupFormat.split("-");
                String semester = parts[0].substring(0, parts[0].length() - 2);
                String career = parts[0].substring(parts[0].length() - 2);
                String group = parts[1];

                groupModel.setGroupName(group);
                groupModel.setSemesterNumber(semester);
                
                Optional<CareerModel> careerModel = careerRepository.findById(career);
                if (careerModel.isEmpty()) {
                    throw new AppException("Career not found for group: " + career, HttpStatus.NOT_FOUND);
                }
                groupModel.setCareer(careerModel.get());

                Optional<SemesterModel> semesterModel = semesterService.getActiveSemester();
                if (semesterModel.isEmpty()) {
                    throw new AppException("Semester not found for group", HttpStatus.NOT_FOUND);
                }
                groupModel.setSemester(semesterModel.get());
                Optional<GroupModel> existingGroup = groupRepository
                        .findByGroupNameAndSemesterNumberAndCareerAndSemester(group,
                                semester,
                                careerModel.get(),
                                semesterModel.get());
                if (existingGroup.isPresent()) {
                    groupMap.put(groupFormat, existingGroup.get());
                    continue;
                }
                GroupModel savedGroup = groupRepository.save(groupModel);

                groupMap.put(groupFormat, savedGroup);
            }

            return groupMap;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Error processing groups", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
