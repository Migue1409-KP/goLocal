package co.uco.golocal.golocalapi.service.location.state;

import co.uco.golocal.golocalapi.data.entity.location.StateEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IStateMapperEntity;
import co.uco.golocal.golocalapi.domain.location.StateDomain;
import co.uco.golocal.golocalapi.repository.location.IStateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final IStateRepository stateRepository;
    private final IStateMapperEntity stateMapperEntity;

    public StateService(IStateRepository stateRepository, IStateMapperEntity stateMapperEntity) {
        this.stateRepository = stateRepository;
        this.stateMapperEntity = stateMapperEntity;
    }

    public List<StateDomain> getAllStates() {
        List<StateEntity> entities = stateRepository.findAll();
        return entities.stream()
                .map(stateMapperEntity::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<StateDomain> getStateById(UUID id) {
        return stateRepository.findById(id)
                .map(stateMapperEntity::toDomain);
    }
}