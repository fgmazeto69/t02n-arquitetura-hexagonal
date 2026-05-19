package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoEventMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveDesserializarPayloadRecebidoPeloSqs() throws Exception {
        final String payload = """
                {
                    "zipCode": "80010000",
                    "customerId": 1,
                    "orderItems": [
                        {
                            "sku": 1,
                            "amount": 5
                        },
                        {
                            "sku": 2,
                            "amount": 3
                        }
                    ],
                    "origin": "SQS_QUEUE",
                    "occurredAt": "2024-05-20T14:30:00Z"
                }
                """;

        final PedidoEventDTO evento = objectMapper.readValue(payload, PedidoEventDTO.class);

        assertThat(evento.getZipCode()).isEqualTo("80010000");
        assertThat(evento.getCustomerId()).isEqualTo(1L);
        assertThat(evento.getOrderItems()).hasSize(2);
        assertThat(evento.getOrderItems().get(0).getSku()).isEqualTo(1);
        assertThat(evento.getOrderItems().get(0).getAmount()).isEqualTo(5);
        assertThat(evento.getOrigin()).isEqualTo("SQS_QUEUE");
        assertThat(evento.getOccurredAt()).isEqualTo("2024-05-20T14:30:00Z");
    }

    @Test
    void deveMapearEventoDePedidoParaBoDaService() {
        final PedidoEventDTO evento = new PedidoEventDTO();
        evento.setZipCode("80010000");
        evento.setCustomerId(1L);
        evento.setOrderItems(Arrays.asList(
                item(1, 5),
                item(2, 3)
        ));

        final PedidoBO pedidoBO = PedidoEventMapper.toBo(evento);

        assertThat(pedidoBO.getCep()).isEqualTo("80010000");
        assertThat(pedidoBO.getPessoa().getId()).isEqualTo(1);
        assertThat(pedidoBO.getItens()).hasSize(2);
        assertThat(pedidoBO.getItens().get(0).getProduto().getId()).isEqualTo(1);
        assertThat(pedidoBO.getItens().get(0).getQuantidade()).isEqualTo(5);
        assertThat(pedidoBO.getItens().get(1).getProduto().getId()).isEqualTo(2);
        assertThat(pedidoBO.getItens().get(1).getQuantidade()).isEqualTo(3);
    }

    private static PedidoEventDTO.PedidoItemEventDTO item(Integer sku, int amount) {
        final PedidoEventDTO.PedidoItemEventDTO item = new PedidoEventDTO.PedidoItemEventDTO();
        item.setSku(sku);
        item.setAmount(amount);
        return item;
    }
}
