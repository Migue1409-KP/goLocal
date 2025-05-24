package co.uco.golocal.golocalapi.notificaciones.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.uco.golocal.golocalapi.controllers.notificaciones.NotificacionController;
import co.uco.golocal.golocalapi.domain.notificaciones.NotificacionDomain;
import co.uco.golocal.golocalapi.service.notificaciones.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private NotificacionController notificacionController;

    @Test
    void testGetNotificacion_Success() {
        UUID idNotificacion = UUID.randomUUID();
        NotificacionDomain notificacion = new NotificacionDomain();
        when(notificacionService.getNotificacionById(idNotificacion)).thenReturn(Optional.of(notificacion));

        ResponseEntity<NotificacionDomain> response = notificacionController.getNotificacion(idNotificacion);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificacion, response.getBody());
    }

    @Test
    void testGetNotificacion_NotFound() {
        UUID idNotificacion = UUID.randomUUID();
        when(notificacionService.getNotificacionById(idNotificacion)).thenReturn(Optional.empty());

        ResponseEntity<NotificacionDomain> response = notificacionController.getNotificacion(idNotificacion);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetNotificacion_InternalServerError() {
        UUID idNotificacion = UUID.randomUUID();
        when(notificacionService.getNotificacionById(idNotificacion)).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<NotificacionDomain> response = notificacionController.getNotificacion(idNotificacion);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCrearNotificacionDesdePagos_Success() {
        UUID usuarioId = UUID.randomUUID();
        List<NotificacionDomain> notificaciones = List.of(new NotificacionDomain(), new NotificacionDomain());
        when(notificacionService.crearNotificacionesDesdePagos(usuarioId)).thenReturn(notificaciones);

        ResponseEntity<List<NotificacionDomain>> response = notificacionController.crearNotificacionDesdePagosDeUsuario(usuarioId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificaciones, response.getBody());
    }


    @Test
    void testCrearNotificacionDesdeCotizaciones_Success() {
        UUID usuarioId = UUID.randomUUID();
        List<NotificacionDomain> notificaciones = List.of(new NotificacionDomain(), new NotificacionDomain());
        when(notificacionService.crearNotificacionesDesdeCotizaciones(usuarioId)).thenReturn(notificaciones);
        ResponseEntity<List<NotificacionDomain>> response = notificacionController.crearNotificacionDesdeCotizacionesDeUsuario(usuarioId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificaciones, response.getBody());
    }
}

