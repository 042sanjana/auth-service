package com.ewallet.auth_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent{
    private Long userId;
    private String email;
    private String fullName;
    private BigDecimal phoneNumber;
    private Date dateOfBirth;

}
