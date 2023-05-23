package com.mvc.sb.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
    private long id;
	private String firstName;
	private String lastName;
	private String email;

}
