package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    private Long id;
    @NotNull(message = "The field group can't be null")
    @NotBlank(message = "The field group can't be blank")
    private String groupName;
    @NotNull(message = "The field semester number can't be null")
    @NotBlank(message = "The field semester number can't be blank")
    private String semesterNumber;
    @NotNull(message = "The field Career can't be null")
    @Valid
    private CareerRequest career;
}
