package co.uco.golocal.golocalapi.controllers.usuario;

import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.service.usuario.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/rest/usuarios")
public class UsuarioController {

	private final UserService userService;

	public UsuarioController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/dummy")
	public UserDomain getDummy(){
		return new UserDomain();
	}

	@PostMapping
	public ResponseEntity<String> userRegister(@RequestBody UserDomain usuarioDomain) {
	    try {
	        userService.registrarUsuario(usuarioDomain);
	        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario se ha registrado exitosamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}

}
