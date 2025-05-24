package co.uco.golocal.golocalapi.domain.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

    private UUID id;
    private String name;
    private String lastName;
    private String phone;
    private String taxId;
    private String role;
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;
}

