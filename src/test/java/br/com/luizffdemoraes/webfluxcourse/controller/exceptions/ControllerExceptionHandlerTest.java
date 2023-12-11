package br.com.luizffdemoraes.webfluxcourse.controller.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Test
    void duplicateKeyException() {
        // Mock da exceção DuplicateKeyException
        DuplicateKeyException duplicateKeyException = new DuplicateKeyException("E-mail already registered");

        // Configurar o mock do ServerHttpRequest
        RequestPath requestPath = mock(RequestPath.class);
        lenient().when(requestPath.value()).thenReturn("/users");
        when(serverHttpRequest.getPath()).thenReturn(requestPath);

        // Chamar o método do controlador de exceções
        ResponseEntity<Mono<StandardError>> responseEntity = controllerExceptionHandler.duplicateKeyException(duplicateKeyException, serverHttpRequest);

        // Verificar se a resposta contém o status HTTP correto
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Verificar se o corpo da resposta contém os detalhes corretos do erro padrão
        Mono<StandardError> body = responseEntity.getBody();
        assert body != null;
        body.subscribe(standardError -> {
            assertEquals(HttpStatus.BAD_REQUEST.value(), standardError.getStatus());
            assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), standardError.getError());
            assertEquals("E-mail already registered", standardError.getMessage());
            assertEquals("/users", standardError.getPath());
        });
    }

    @Test
    void objectNotFoundException() {
        // Mock da exceção ObjectNotFoundException
        ObjectNotFoundException objectNotFoundException = new ObjectNotFoundException("Object not found");

        // Configurar o mock do ServerHttpRequest
        RequestPath requestPath = mock(RequestPath.class);
        lenient().when(requestPath.value()).thenReturn("/users");
        when(serverHttpRequest.getPath()).thenReturn(requestPath);

        // Chamar o método do controlador de exceções
        ResponseEntity<Mono<StandardError>> responseEntity =
                controllerExceptionHandler.objectNotFoundException(objectNotFoundException, serverHttpRequest);

        // Verificar se a resposta contém o status HTTP correto
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // Verificar se o corpo da resposta contém os detalhes corretos do erro padrão
        Mono<StandardError> body = responseEntity.getBody();
        assert body != null;
        body.subscribe(standardError -> {
            assertEquals(HttpStatus.NOT_FOUND.value(), standardError.getStatus());
            assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), standardError.getError());
            assertEquals("Object not found", standardError.getMessage());
            assertEquals("/objects", standardError.getPath());
        });
    }
}