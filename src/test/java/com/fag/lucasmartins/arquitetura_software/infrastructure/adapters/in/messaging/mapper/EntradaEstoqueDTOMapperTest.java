package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.commands.AdicionarEstoqueCommand;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.entradaestoque.dto.EntradaEstoqueDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.entradaestoque.mapper.EntradaEstoqueDTOMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntradaEstoqueDTOMapperTest {

    @Test
    void toCommand_deveConverterCampos() {
        EntradaEstoqueDTO dto = new EntradaEstoqueDTO();
        dto.setProdutoId(7);
        dto.setQuantidadeAdicionada(9);

        AdicionarEstoqueCommand cmd = EntradaEstoqueDTOMapper.toCommand(dto);
        assertEquals(7, cmd.getProdutoId());
        assertEquals(9, cmd.getQuantidade());
    }
}
