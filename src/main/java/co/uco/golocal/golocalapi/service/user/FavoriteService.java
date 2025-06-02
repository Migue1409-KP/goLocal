package co.uco.golocal.golocalapi.service.user;

import co.uco.golocal.golocalapi.data.mapper.concrete.IFavoriteMapperEntity;
import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.CreateFavoriteUseCase;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl.DeleteFavoriteUseCase;
import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {
    private final IFavoriteRepository favoritieRepository;
    private final IFavoriteMapperEntity favoritieMapper;
    private final CreateFavoriteUseCase createFavoriteUseCase;
    private final DeleteFavoriteUseCase deleteFavoriteUseCase;

    public FavoriteService(IFavoriteRepository favoritieRepository,
                           IFavoriteMapperEntity favoritieMapper,
                           CreateFavoriteUseCase createFavoriteUseCase,
                           DeleteFavoriteUseCase deleteFavoriteUseCase) {
        this.favoritieRepository = favoritieRepository;
        this.favoritieMapper = favoritieMapper;
        this.createFavoriteUseCase = createFavoriteUseCase;
        this.deleteFavoriteUseCase = deleteFavoriteUseCase;
    }

    public FavoriteDomain createFavorite(FavoriteDomain favorite) {
        createFavoriteUseCase.execute(favorite);
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
        deleteFavoriteUseCase.execute(id);
        favoritieRepository.deleteById(id);
    }
}
