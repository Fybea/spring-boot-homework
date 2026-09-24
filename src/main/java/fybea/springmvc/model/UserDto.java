package fybea.springmvc.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty(message = "should be not empty")
    @Size(min = 3, max = 20)
    private String name;
    @Email(message = "should be email format @")
    private String email;
    @NotNull(message = "can't be null")
    @Min(value = 1, message = "can't be less than 1")
    @Max(value = 100, message = "can't be greater than 100")
    private Integer age;
    private List<PetDto> pets;
}
