package fybea.springmvc.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {

    private Long id;
    @NotNull(message = "should be not empty")
    @Size(min = 1, max = 50)
    private String name;
    @NotNull(message = "should be not empty")
    private Long userId;
}
