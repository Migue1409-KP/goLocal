package co.uco.golocal.golocalapi.controllers.usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.service.usuario.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/rest/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@GetMapping("/dummy")
	public UserDomain obtenerDummy(){
		return new UserDomain();
	}

	@PostMapping
	public ResponseEntity<String> registrarUsuario(@RequestBody UserDomain usuarioDomain) {
	    try {
	        usuarioService.registrarUsuario(usuarioDomain);
	        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario se ha registrado exitosamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerUsuarioUnico(@PathVariable UUID id){
		try {
			Optional<UsuarioEntity> usuario = usuarioService.consultarUsuarioPorId(id);
			return ResponseEntity.ok(usuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
		}
	}
	
	@PutMapping
	public ResponseEntity<String>editarUsuario(@RequestBody UserDomain usuarioDomain) {
	    try {
	        usuarioService.actualizarUsuario(usuarioDomain);
	        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario se ha modificado exitosamente:\n"+ usuarioDomain.toString());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}


	@GetMapping("/obtenerlistausuario")
	public ResponseEntity<Page<UserDomain>> obtenerPaginacionUsuarios() {
		final Pageable page = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"nombre"));
		var usuariosPage= usuarioService.obtenerTodosLosUsuarios(page);
		if (usuariosPage.isEmpty()){
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuariosPage);
	}

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> eliminar(@PathVariable UUID id){
		try {
			usuarioService.delete(id);
			return ResponseEntity.ok("el usuario se ha eliminado satisfactoriamente ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/listarusarios")
	public ResponseEntity<List<UsuarioEntity>> obtenerListaUsuariosEntity(){
		var listaUsuarios= usuarioService.obtenerTodosLosUsuariosSinPaginacion();
		return ResponseEntity.ok(listaUsuarios);
	}

//	patch
	@PatchMapping("/{id}")
		public ResponseEntity<UserDomain> actualizarParcialmenteUsuario (@PathVariable UUID id, @RequestBody Map< String, Object > cambios)
		{
			try {
				UserDomain usuarioDomainActualizado = usuarioService.actualizarParcial(id, cambios);
				return ResponseEntity.ok(usuarioDomainActualizado);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}

}
