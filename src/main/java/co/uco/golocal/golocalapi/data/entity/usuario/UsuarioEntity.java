package co.uco.golocal.golocalapi.data.entity.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.UUID;

@Table(name="Users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

	@Id
	private UUID id;
    private String name;
    private String lastName;
    private String phone;
    private String taxId;
    private String role;
    private String email;
    private String password;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
