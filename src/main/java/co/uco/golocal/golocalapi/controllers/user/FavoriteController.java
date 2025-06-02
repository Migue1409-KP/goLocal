package co.uco.golocal.golocalapi.controllers.user;

import co.uco.golocal.golocalapi.controllers.user.support.FavoriteDTO;
import co.uco.golocal.golocalapi.controllers.user.support.Response;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.service.user.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/dummy")
    public FavoriteDomain getDummy() {
        return new FavoriteDomain();
    }

    @PostMapping
    public ResponseEntity<Response<FavoriteDomain>> createFavorite(@RequestBody FavoriteDTO favorite) {
        try {
            FavoriteDomain favoriteDomain = new FavoriteDomain();

            UserDomain user = new UserDomain();
            user.setId(favorite.getUserId());
            favoriteDomain.setUser(user);

            ExperienceDomain experience = new ExperienceDomain();
            experience.setId(favorite.getExperienceId());
            favoriteDomain.setExperience(experience);

            FavoriteDomain favoriteResponse = favoriteService.createFavorite(favoriteDomain);
            Response<FavoriteDomain> response = new Response<>();
            response.setStatus(HttpStatus.CREATED);
            response.setData(List.of(favoriteResponse));
            response.setMessage("Favorite created successfully");
            return ResponseEntity
                    .created(URI.create("/api/v1/rest/favorites/" + favoriteResponse.getId()))
                    .body(response);
        } catch (Exception e) {
            Response<FavoriteDomain> response = new Response<>();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<FavoriteDomain>> getAllFavoritesByUserId(@PathVariable UUID userId) {
        try {
            List<FavoriteDomain> favorites = favoriteService.getAllFavoritesByUserId(userId);
            Response<FavoriteDomain> response = new Response<>();
            response.setStatus(HttpStatus.OK);
            response.setData(favorites);
            response.setMessage("Favorites retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response<FavoriteDomain> response = new Response<>();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFavorite(@PathVariable UUID id) {
        try {
            favoriteService.deleteFavorite(id);
            Response<Void> response = new Response<>();
            response.setStatus(HttpStatus.NO_CONTENT);
            response.setMessage("Favorite deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Response<Void> response = new Response<>();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }
}
