package co.uco.golocal.golocalapi.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IBusinessMapperEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.CreateBusinesUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.DeleteBusinesUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.GetBusinessByIdUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.UpdatePartialBusinessUseCase;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import co.uco.golocal.golocalapi.service.business.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

 class BusinessServiceTest {
    private IBusinessRepository businessRepository;
    private IBusinessMapperEntity businessMapperEntity;
    private DeleteBusinesUseCase deleteUseCase;
    private CreateBusinesUseCase createUseCase;
    private UpdatePartialBusinessUseCase updateUseCase;
    private GetBusinessByIdUseCase getUseCase;
    private ICategoryRepository categoryRepository;

    private BusinessService businessService;

    @BeforeEach
    void setUp() {
        businessRepository = mock(IBusinessRepository.class);
        businessMapperEntity = mock(IBusinessMapperEntity.class);
        deleteUseCase = mock(DeleteBusinesUseCase.class);
        createUseCase = mock(CreateBusinesUseCase.class);
        updateUseCase = mock(UpdatePartialBusinessUseCase.class);
        getUseCase = mock(GetBusinessByIdUseCase.class);
        categoryRepository = mock(ICategoryRepository.class);

        businessService = new BusinessService(
                businessRepository, businessMapperEntity,
                deleteUseCase, createUseCase,
                updateUseCase, getUseCase,
                categoryRepository
        );
    }

    @Test
    void deleteBusiness_shouldDeleteSuccessfully() {
        UUID id = UUID.randomUUID();
        BusinessEntity mockBusiness = new BusinessEntity();
        when(businessRepository.findById(id)).thenReturn(Optional.of(mockBusiness));

        businessService.deleteBusiness(id);

        verify(deleteUseCase).execute(id);
        verify(businessRepository).delete(mockBusiness);
    }

    @Test
    void getBusinessById_shouldReturnBusiness() {
        UUID id = UUID.randomUUID();
        BusinessEntity businessEntity = new BusinessEntity();
        when(businessRepository.findById(id)).thenReturn(Optional.of(businessEntity));

        Optional<BusinessEntity> result = businessService.getBusinessById(id);

        verify(getUseCase).execute(id);
        assertTrue(result.isPresent());
        assertEquals(businessEntity, result.get());
    }

    @Test
    void getAllBusinesses_shouldReturnPagedResult() {
        Pageable pageable = PageRequest.of(0, 2);
        BusinessEntity entity1 = new BusinessEntity();
        BusinessEntity entity2 = new BusinessEntity();
        List<BusinessEntity> entities = List.of(entity1, entity2);
        Page<BusinessEntity> page = new PageImpl<>(entities);

        when(businessRepository.findAll(pageable)).thenReturn(page);
        when(businessMapperEntity.toDomain(entity1)).thenReturn(new BusinessDomain());
        when(businessMapperEntity.toDomain(entity2)).thenReturn(new BusinessDomain());

        Page<BusinessDomain> result = businessService.getAllBusinesses(pageable);

        assertEquals(2, result.getContent().size());
        verify(businessRepository).findAll(pageable);
    }
}

