package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.listener;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.exceptions.ConsumerSQSException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper.PedidoEventMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SqsPedidoAdapter {

    private static final Logger log = LoggerFactory.getLogger(SqsPedidoAdapter.class);

    private final PedidoServicePort pedidoServicePort;

    public SqsPedidoAdapter(PedidoServicePort pedidoServicePort) {
        this.pedidoServicePort = pedidoServicePort;
    }

    @SqsListener(value = "${queue.order-events}")
    public void receberMensagem(PedidoEventDTO evento) {
        try {
            log.info("Evento de pedido recebido via {} para o cliente {} em {}",
                    evento.getOrigin(), evento.getCustomerId(), evento.getOccurredAt());

            final PedidoBO pedidoBO = PedidoEventMapper.toBo(evento);
            final PedidoBO pedidoCriado = pedidoServicePort.criarPedido(pedidoBO);

            log.info("Pedido {} processado com sucesso a partir da fila SQS", pedidoCriado.getId());
        } catch (Exception e) {
            log.error("Erro ao processar evento de pedido para o cliente {}", evento.getCustomerId(), e);
            throw new ConsumerSQSException("erro ao processar evento de pedido para o cliente " + evento.getCustomerId(), e);
        }
    }
}
