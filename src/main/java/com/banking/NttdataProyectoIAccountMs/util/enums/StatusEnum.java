package com.banking.NttdataProyectoIAccountMs.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum StatusEnum {
  PENDING,
  IN_PROCESS,
  ERROR,
  DONE,
  DELETED
}
