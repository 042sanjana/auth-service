package com.ewallet.auth_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private BigDecimal phoneNumber;
    private Date dateOfBirth;

}
