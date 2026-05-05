package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;

public class PedidoEventMapper {

    public static PedidoBO toBO(PedidoEventDTO evento) {

        PedidoBO pedido = new PedidoBO();

        
        pedido.setCep(evento.getZipCode());


        return pedido;
    }
}
