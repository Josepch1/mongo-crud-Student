package josehomenhuck.mongo_project.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {
    @Id
    private String id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Indexed(unique = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Valid
    private Address address;

    @Builder.Default
    private List<@NotBlank String> favoriteSubjects = new ArrayList<>();

    @PositiveOrZero(message = "Total spent must be zero or positive")
    @Builder.Default
    private BigDecimal totalSpentInBooks = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder.Default
    private boolean active = true;

    // Utility methods
    public void addFavoriteSubject(String subject) {
        if (this.favoriteSubjects == null) {
            this.favoriteSubjects = new ArrayList<>();
        }
        this.favoriteSubjects.add(subject);
    }

    public void removeFavoriteSubject(String subject) {
        if (this.favoriteSubjects != null) {
            this.favoriteSubjects.remove(subject);
        }
    }

    public void addToTotalSpent(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) >= 0) {
            this.totalSpentInBooks = this.totalSpentInBooks.add(amount);
        }
    }
}