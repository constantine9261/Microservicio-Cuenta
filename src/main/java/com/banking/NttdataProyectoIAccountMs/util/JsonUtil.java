package com.banking.NttdataProyectoIAccountMs.util;

import com.banking.NttdataProyectoIAccountMs.model.api.account.AccountClientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<AccountClientDto> fromJsonToProductAttributeList(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, AccountClientDto.class));
        } catch (JsonProcessingException e) {
            // Maneja la excepción o registra un error
            e.printStackTrace();
            return null;
        }
    }
}
