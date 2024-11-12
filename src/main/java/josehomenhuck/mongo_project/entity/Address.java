package josehomenhuck.mongo_project.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Post code is required")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Invalid post code format")
    private String postCode;

    @NotBlank(message = "City is required")
    private String city;
}