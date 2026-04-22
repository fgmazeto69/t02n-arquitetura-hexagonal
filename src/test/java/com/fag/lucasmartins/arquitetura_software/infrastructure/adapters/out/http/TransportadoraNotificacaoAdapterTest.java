package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.http;

import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.http.client.TransportadoraFeignClient;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.http.dto.DespachoRequestDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.http.dto.DespachoResponseDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.http.exceptions.TransportadoraClientException;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransportadoraNotificacaoAdapterTest {

    private TransportadoraFeignClient client;
    private TransportadoraNotificacaoAdapter adapter;

    @BeforeEach
    void setup() {
        client = mock(TransportadoraFeignClient.class);
        adapter = new TransportadoraNotificacaoAdapter(client);
    }

    @Test
    void notificarItem_deveRetornarBodyEmSucesso() {
        DespachoResponseDTO body = new DespachoResponseDTO();
        body.setMensagem("ok");
        when(client.despachar(any(DespachoRequestDTO.class))).thenReturn(ResponseEntity.ok(body));

        DespachoResponseDTO resp = adapter.notificarItem(1, "P", 2);
        assertEquals("ok", resp.getMensagem());
    }

    @Test
    void notificarItem_deveEncapsularFeignException() {
        FeignException fe = mock(FeignException.class);
        when(fe.getMessage()).thenReturn("erro remoto");
        when(client.despachar(any())).thenThrow(fe);

        TransportadoraClientException ex = assertThrows(TransportadoraClientException.class,
                () -> adapter.notificarItem(1, "P", 2));
        assertTrue(ex.getMessage().contains("transportadora"));
    }
}
