package com.banking.NttdataProyectoIAccountMs.model.api.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListAccountResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("clientes")
  @Valid
  private List<AccountListDto> accounts = null;

  @JsonIgnore
  private Long totalRecords;

  private String errorMessage; // Campo para el mensaje de error


  public void buildDtoList(List<AccountListDto> productDtoList) {
    this.accounts = productDtoList;
    this.totalRecords = productDtoList != null && productDtoList.size() > 0
            ? productDtoList.get(0).getTotalRecords() : 0;
  }


}

