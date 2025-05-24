package co.uco.golocal.golocalapi;

import co.uco.golocal.golocalapi.controllers.productos.support.PreguntasCotizacion;
import co.uco.golocal.golocalapi.controllers.productos.support.SolicitarCotizacion;
import co.uco.golocal.golocalapi.data.entity.productos.CotizacionEntity;
import co.uco.golocal.golocalapi.data.entity.productos.ReglasEntity;
import co.uco.golocal.golocalapi.data.entity.productos.SeguroEntity;
import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;
import co.uco.golocal.golocalapi.domain.productos.CotizacionDomain;
import co.uco.golocal.golocalapi.domain.productos.SeguroDomain;
import co.uco.golocal.golocalapi.domain.productos.reglasdomain.CotizacionExistente;
import co.uco.golocal.golocalapi.domain.productos.reglasdomain.impl.EditarCotizacionDomain;
import co.uco.golocal.golocalapi.domain.productos.reglasdomain.impl.RegistrarCotizacionDomain;
import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.repository.productos.CotizacionRepository;
import co.uco.golocal.golocalapi.repository.productos.ReglasRepository;
import co.uco.golocal.golocalapi.repository.productos.SeguroRepository;
import co.uco.golocal.golocalapi.service.productos.CotizacionService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.ArgumentCaptor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TestCotizacionService {

    @Mock
    private CotizacionRepository cotizacionRepository;
    @Mock
    private SeguroRepository seguroRepository;
    @Mock
    private ReglasRepository reglasRepository;
    @Mock
    private CotizacionMapperEntity cotizacionMapperEntity;
    @Mock
    private EditarCotizacionDomain editarCotizacionDomain;
    @Mock
    private RegistrarCotizacionDomain registrarCotizacionDomain;
    @Mock
    private CotizacionExistente cotizacionExistente;
    @InjectMocks
    private CotizacionService cotizacionService;

    @Test
    void testActualizarEstadoCotizacion() {
        UUID id = UUID.randomUUID();
        CotizacionEntity cotizacionEntity = new CotizacionEntity(id, 1000, new Date(), new UsuarioEntity(), new SeguroEntity(), 1000 / 12, false);

        when(cotizacionRepository.getReferenceById(id)).thenReturn(cotizacionEntity);
        doNothing().when(editarCotizacionDomain).validacionReglasDominio(id);

        ArgumentCaptor<CotizacionEntity> cotizacionCaptor = ArgumentCaptor.forClass(CotizacionEntity.class);

        cotizacionService.actualizarEstadoCotizacion(id);

        verify(cotizacionRepository).getReferenceById(id);
        verify(editarCotizacionDomain).validacionReglasDominio(id);
        verify(cotizacionRepository).save(cotizacionCaptor.capture());

        CotizacionEntity cotizacionGuardada = cotizacionCaptor.getValue();
        assertTrue(cotizacionGuardada.isAdquirido());
    }

    @Test
    void testEliminarCotizacion() {
        UUID id = UUID.randomUUID();

        doNothing().when(cotizacionExistente).validacionReglaId(id);

        cotizacionService.eliminarCotizacion(id);

        verify(cotizacionExistente).validacionReglaId(id);
        verify(cotizacionRepository).deleteById(id);
    }

    @Test
    void testConsultarCotizaciones() {
        CotizacionEntity cotizacion1 = new CotizacionEntity(UUID.randomUUID(), 1000, new Date(), new UsuarioEntity(), new SeguroEntity(), 1000 / 12, false);
        CotizacionEntity cotizacion2 = new CotizacionEntity(UUID.randomUUID(), 2000, new Date(), new UsuarioEntity(), new SeguroEntity(), 2000 / 12, true);
        List<CotizacionEntity> cotizacionesMock = List.of(cotizacion1, cotizacion2);


        when(cotizacionRepository.findAll()).thenReturn(cotizacionesMock);

        CotizacionDomain cotizacionDomain1 = new CotizacionDomain();
        CotizacionDomain cotizacionDomain2 = new CotizacionDomain();
        when(cotizacionMapperEntity.toDomain(cotizacion1)).thenReturn(cotizacionDomain1);
        when(cotizacionMapperEntity.toDomain(cotizacion2)).thenReturn(cotizacionDomain2);

        List<CotizacionDomain> resultado = cotizacionService.consultarCotizaciones();


        verify(cotizacionRepository).findAll();
        verify(cotizacionMapperEntity).toDomain(cotizacion1);
        verify(cotizacionMapperEntity).toDomain(cotizacion2);


        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(cotizacionDomain1));
        assertTrue(resultado.contains(cotizacionDomain2));
    }

    @Test
    void testConsultarCotizacionPorId() {
        UUID id = UUID.randomUUID();
        CotizacionEntity cotizacionEntity = new CotizacionEntity(id, 1000, new Date(), new UsuarioEntity(), new SeguroEntity(), 1000 / 12, false);
        CotizacionDomain cotizacionDomain = new CotizacionDomain(id, 1000, new Date(), new UserDomain(), new SeguroDomain(), 1000 / 12, false);


        doNothing().when(cotizacionExistente).validacionReglaId(id);
        when(cotizacionRepository.getReferenceById(id)).thenReturn(cotizacionEntity);
        when(cotizacionMapperEntity.toDomain(cotizacionEntity)).thenReturn(cotizacionDomain);

        CotizacionDomain resultado = cotizacionService.consultarCotizacionPorId(id);

        verify(cotizacionExistente).validacionReglaId(id);
        verify(cotizacionRepository).getReferenceById(id);
        verify(cotizacionMapperEntity).toDomain(cotizacionEntity);
        assertEquals(cotizacionDomain, resultado);
    }

    @Test
    void testGenerarCotizacion() {
        UUID seguroId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();
        double valorBase = 100000;

        SolicitarCotizacion solicitarCotizacion = new SolicitarCotizacion();
        solicitarCotizacion.setSeguroId(seguroId);
        solicitarCotizacion.setUsuarioId(usuarioId);
        solicitarCotizacion.setValorBase(valorBase);
        PreguntasCotizacion pregunta = new PreguntasCotizacion();
        pregunta.setCampo("campo1");
        pregunta.setValor("100");
        solicitarCotizacion.setPreguntas(List.of(pregunta));

        SeguroEntity seguro = new SeguroEntity();
        seguro.setTipo("vehiculo");

        when(seguroRepository.getReferenceById(seguroId)).thenReturn(seguro);

        ReglasEntity regla = new ReglasEntity();
        regla.setCampo("campo1");
        regla.setValor(">50");
        regla.setTipo("Int");
        regla.setPorcentaje(0.10);

        when(reglasRepository.findByCampo("campo1")).thenReturn(regla);
        doNothing().when(registrarCotizacionDomain).validacionReglasDominio(seguroId, usuarioId);
        CotizacionDomain cotizacionDomainEsperada = new CotizacionDomain();
        when(cotizacionMapperEntity.toDomain(any(CotizacionEntity.class))).thenReturn(cotizacionDomainEsperada);

        CotizacionDomain resultado = cotizacionService.generarCotizacion(solicitarCotizacion);

        verify(registrarCotizacionDomain).validacionReglasDominio(seguroId, usuarioId);
        verify(cotizacionRepository).save(any(CotizacionEntity.class));
        ArgumentCaptor<CotizacionEntity> captor = ArgumentCaptor.forClass(CotizacionEntity.class);
        verify(cotizacionRepository).save(captor.capture());

        CotizacionEntity cotizacionGuardada = captor.getValue();
        assertEquals(5500.0, cotizacionGuardada.getValor_anual(), 0.01);
        assertEquals(cotizacionDomainEsperada, resultado);
    }
}
