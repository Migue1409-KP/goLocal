package co.uco.golocal.golocalapi.controllers.user;

import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/rest/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/dummy")
	public UserDomain getDummy(){
		return new UserDomain();
	}

	@PostMapping
	public ResponseEntity<Response<UserDomain>> userRegister(@RequestBody UserDomain user) {
	    try {
	        UserDomain userResponse = userService.createUser(user);

			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.CREATED);
			response.setData(List.of(userResponse));
			response.setMessage("User created successfully");
			return ResponseEntity
					.created(URI.create("/api/v1/users/" + userResponse.getId()))
					.body(response);
	    } catch (Exception e) {
	        Response<UserDomain> response = new Response<>();
	        response.setStatus(HttpStatus.BAD_REQUEST);
	        response.setMessage(e.getMessage());
	        return ResponseEntity
					.badRequest()
					.body(response);
	    }
	}

	@GetMapping
	public ResponseEntity<Response<UserDomain>> getAllUsers() {
		try {
			List<UserDomain> users = userService.getAllUsers();
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.OK);
			response.setData(users);
			response.setMessage("Users retrieved successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<UserDomain>> getUserById(@PathVariable UUID id) {
		try {
			UserDomain user = userService.getUserById(id);
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.OK);
			response.setData(List.of(user));
			response.setMessage("User retrieved successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<UserDomain>> updateUser(@PathVariable UUID id, @RequestBody UserDomain user) {
		try {
			user.setId(id);
			UserDomain updatedUser = userService.updateUser(user);
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.OK);
			response.setData(List.of(updatedUser));
			response.setMessage("User updated successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Response<UserDomain> response = new Response<>();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PutMapping("/password/{id}")
	public ResponseEntity<Response<Void>> updatePassword(@PathVariable UUID id, @RequestBody UserDomain user) {
		try {
			boolean isUpdated = userService.updatePassword(id, user.getPassword());
            Response<Void> response = new Response<>();
            if (isUpdated) {
                response.setStatus(HttpStatus.OK);
				response.setMessage("Password updated successfully");
				return ResponseEntity.ok(response);
			} else {
                response.setStatus(HttpStatus.NOT_FOUND);
				response.setMessage("User not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			Response<Void> response = new Response<>();
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Void>> deleteUser(@PathVariable UUID id) {
		try {
			userService.deleteUser(id);
			Response<Void> response = new Response<>();
			response.setStatus(HttpStatus.NO_CONTENT);
			response.setMessage("User deleted successfully");
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			Response<Void> response = new Response<>();
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
