package com.ewallet.auth_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent implements Serializable {
    private Long userId;
    private String email;
    private String fullName;
    private String phoneNumber;

}
