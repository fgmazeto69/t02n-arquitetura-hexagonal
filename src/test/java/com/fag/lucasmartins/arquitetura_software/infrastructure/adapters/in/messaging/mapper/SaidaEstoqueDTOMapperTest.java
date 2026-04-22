package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.commands.DiminuirEstoqueCommand;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.saidaestoque.dto.SaidaEstoqueEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.saidaestoque.mapper.SaidaEstoqueDTOMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaidaEstoqueDTOMapperTest {

    @Test
    void toCommand_deveConverterCampos() {
        SaidaEstoqueEventDTO dto = new SaidaEstoqueEventDTO();
        dto.setProdutoId(3);
        dto.setQuantidadeAdicionada(4);

        DiminuirEstoqueCommand cmd = SaidaEstoqueDTOMapper.toCommand(dto);
        assertEquals(3, cmd.getProdutoId());
        assertEquals(4, cmd.getQuantidade());
    }
}
