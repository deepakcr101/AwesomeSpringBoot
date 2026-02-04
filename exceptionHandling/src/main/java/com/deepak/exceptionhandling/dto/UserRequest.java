package com.deepak.exceptionhandling.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "username can't be null")
    private String username;

    @Email(message = "invalid mail address")
    private String email;

    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile no.")
    private String mobile;

    private String gender;
    @Min(12)
    @Max(130)
    private int age;
    @NotBlank(message = "nationality shouldn't be blank")
    private String nationality;

}
