package co.uco.golocal.golocalapi.service.user;

import co.uco.golocal.golocalapi.data.mapper.concrete.IFavoriteMapperEntity;
import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.CreateFavoritieUseCase;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.DeleteFavoritieUseCase;
import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {
    private final IFavoriteRepository favoritieRepository;
    private final IFavoriteMapperEntity favoritieMapper;
    private final CreateFavoritieUseCase createFavoritieUseCase;
    private final DeleteFavoritieUseCase deleteFavoritieUseCase;

    public FavoriteService(IFavoriteRepository favoritieRepository,
                           IFavoriteMapperEntity favoritieMapper,
                           CreateFavoritieUseCase createFavoritieUseCase,
                           DeleteFavoritieUseCase deleteFavoritieUseCase) {
        this.favoritieRepository = favoritieRepository;
        this.favoritieMapper = favoritieMapper;
        this.createFavoritieUseCase = createFavoritieUseCase;
        this.deleteFavoritieUseCase = deleteFavoritieUseCase;
    }

    public FavoriteDomain createFavorite(FavoriteDomain favorite) {
        createFavoritieUseCase.execute(favorite);
        favorite.setId(UUID.randomUUID());

        return favoritieMapper.toDomain(favoritieRepository.save(favoritieMapper.toEntity(favorite)));
    }

    public List<FavoriteDomain> getAllFavoritesByUserId(UUID userId) {
        return favoritieRepository.findAllByUserId(userId)
                .stream()
                .map(favoritieMapper::toDomain)
                .toList();
    }

    public void deleteFavorite(UUID id) {
        deleteFavoritieUseCase.execute(id);
        favoritieRepository.deleteById(id);
    }
}
