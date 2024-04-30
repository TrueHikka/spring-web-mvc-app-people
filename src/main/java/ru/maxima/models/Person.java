package ru.maxima.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;

    @NotEmpty(message = "Name is required")
//    @Min(value=2, message="Name must be at least 2 symbols")
//    @Max(value=50, message="Name must be at most 50 symbols")
    @Size(min=2, max=50, message="Name must be between 2 and 50 symbols")
    private String name;

    @Min(value = 0, message = "Age should be min 0 years")
    private Integer age;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
