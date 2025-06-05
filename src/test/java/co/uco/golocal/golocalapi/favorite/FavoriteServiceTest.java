package co.uco.golocal.golocalapi.favorite;

import co.uco.golocal.golocalapi.data.entity.user.FavoriteEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IFavoriteMapperEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.CreateFavoriteUseCase;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.DeleteFavoriteUseCase;
import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import co.uco.golocal.golocalapi.service.user.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteServiceTest {
    @Mock
    private IFavoriteRepository favoriteRepository;

    @Mock
    private IFavoriteMapperEntity favoriteMapper;

    @Mock
    private CreateFavoriteUseCase createFavoriteUseCase;

    @Mock
    private DeleteFavoriteUseCase deleteFavoriteUseCase;

    @InjectMocks
    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFavorite() {
        UUID userId = UUID.randomUUID();
        UUID experienceId = UUID.randomUUID();

        // Dominio de usuario y experiencia
        UserDomain user = new UserDomain();
        user.setId(userId);

        ExperienceDomain experience = new ExperienceDomain();
        experience.setId(experienceId);

        // Dominio a guardar
        FavoriteDomain domain = new FavoriteDomain();
        domain.setUser(user);
        domain.setExperience(experience);

        FavoriteEntity entity = new FavoriteEntity();
        entity.setId(UUID.randomUUID());

        when(favoriteMapper.toEntity(any())).thenReturn(entity);
        when(favoriteRepository.save(entity)).thenReturn(entity);
        when(favoriteMapper.toDomain(entity)).thenReturn(domain);

        FavoriteDomain result = favoriteService.createFavorite(domain);

        assertNotNull(result);
        verify(createFavoriteUseCase).execute(domain);
        verify(favoriteRepository).save(entity);
    }

    @Test
    void testGetAllFavoritesByUserId() {
        UUID userId = UUID.randomUUID();
        FavoriteEntity entity = new FavoriteEntity();
        FavoriteDomain domain = new FavoriteDomain();

        when(favoriteRepository.findAllByUserId(userId)).thenReturn(List.of(entity));
        when(favoriteMapper.toDomain(entity)).thenReturn(domain);

        List<FavoriteDomain> result = favoriteService.getAllFavoritesByUserId(userId);

        assertEquals(1, result.size());
        verify(favoriteRepository).findAllByUserId(userId);
    }

    @Test
    void testDeleteFavorite() {
        UUID favoriteId = UUID.randomUUID();

        favoriteService.deleteFavorite(favoriteId);

        verify(deleteFavoriteUseCase).execute(favoriteId);
        verify(favoriteRepository).deleteById(favoriteId);
    }
}
