package com.example.lap6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;


@AllArgsConstructor
@Data
public class Employee {

@NotNull(message = "ID should be Not Null!")
@Size(min = 3 )
private int id;

@NotEmpty(message = "Name should be Not Empty!")
@Size(min = 5)
@Pattern(regexp="^[A-Za-z]*$" , message = " Name Must contain only characters no numbers")
private String name;

@Email
private String email;

@Pattern(regexp = "(\05|0)[0-9]{9}",message = "Phone Number must start with '05' and have exactly 10 digits")
private String phoneNumber;

@NotNull(message = "Age should be Not Null !")
@Positive(message = "Age must be a number")
@Min(value = 26, message = "Age must be more than 25")
private int age;

@NotEmpty(message = "Position should be Not Empty!")
@Pattern(regexp="^(supervisor|coordinator)$",message = "")
private String position;

@AssertFalse
private boolean onLeave;

@NotNull(message = "HireDate should be Not Null!")
@JsonFormat(pattern ="yyyy-MM-dd")
@PastOrPresent
private LocalDate hireDate;


@NotNull(message = "AnnualLeave should be Not Null!")
@Positive
private int annualLeave;


}
