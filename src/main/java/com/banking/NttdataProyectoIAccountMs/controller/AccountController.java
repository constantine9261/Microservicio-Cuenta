package com.banking.NttdataProyectoIAccountMs.controller;

import com.banking.NttdataProyectoIAccountMs.business.service.IAccountService;
import com.banking.NttdataProyectoIAccountMs.model.api.account.*;
import com.banking.NttdataProyectoIAccountMs.model.api.shared.ApiException;
import com.banking.NttdataProyectoIAccountMs.model.api.shared.ResponseDto;
import com.banking.NttdataProyectoIAccountMs.util.ResponseUtil;
import com.banking.NttdataProyectoIAccountMs.util.api.ApiConstants;
import com.banking.NttdataProyectoIAccountMs.util.api.ApiHttpMethod;
import com.banking.NttdataProyectoIAccountMs.util.api.ApiPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping(ApiPath.CUENTAS_ENDPOINT)
public class AccountController {

    @Autowired
    private IAccountService accountService;


    @PostMapping(
            value = ApiPath.EMPTY_PATH,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            tags = ApiConstants.TAG_WORK_ORDER,
            summary = ApiConstants.POST_ACCOUNT_ORDER_SUMMARY,
           description = ApiConstants.POST_WORK_ORDER_DESC,
            method = ApiHttpMethod.POST_METHOD, responses = {
           @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400",
                   description = "Los datos proporcionados no son válidos.",
                   content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                   }),
           @ApiResponse(responseCode = "401",
                    description = "No esta autorizado correctamente para ejecutar esta operación.",
                   content = {
                           @Content(
                                  mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                           )
                   }),
           @ApiResponse(responseCode = "403",
                   description = "No tienes permiso para acceder al servidor.",
                   content = {
                           @Content(
                                   mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                           )
                    }),
           @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                    content = {
                           @Content(
                                   mediaType = "application/json",
                                   schema = @Schema(implementation = ApiException.class)
                           )
                   }),
            @ApiResponse(responseCode = "500",
                    description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                    content = {
                           @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "503",
                    description = "Servicio no disponible.",
                   content = {
                           @Content(
                                    mediaType = "application/json",
                                   schema = @Schema(implementation = ApiException.class)
                           )
                   })
   })
    public Mono<ResponseDto<AccountDto>> createAccount(
          @RequestBody CreateAccountRequest request
       ) {

       return accountService.createAcountbyClient(request)
               .map(ResponseUtil::getSuccessfulResponse);
   }


    @GetMapping(
            value = ApiPath.EMPTY_PATH,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            tags = ApiConstants.TAG_WORK_ORDER,
            summary = ApiConstants.GET_ACCOUNT_SUMMARY,
            description = ApiConstants.GET_WORK_ORDER_LIST_DESC,
            method = ApiHttpMethod.GET_METHOD, responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400",
                    description = "Los datos proporcionados no son válidos.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "401",
                    description = "No esta autorizado correctamente para ejecutar esta operación.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para acceder al servidor.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "500",
                    description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "503",
                    description = "Servicio no disponible.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    })
    })
    public Mono<ResponseDto<ListAccountResponse>> listAccount(
            @ParameterObject ListAccountRequest request
    ){
        return accountService.listAccountPaginated(request)
                .map(response -> ResponseUtil.getSuccessfulResponseWithPagination(response,response.getTotalRecords(),
                        request.getPageSize(), request.getOffset() /request.getPageSize() +1));
    }

    @GetMapping(
            value = ApiPath.GET_ACOUNTT_ID,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            tags = ApiConstants.TAG_WORK_ORDER,
            summary = ApiConstants.GET_ACCOUNT_ID_SUMMARY,
            description = ApiConstants.POST_WORK_ORDER_DESC,
            method = ApiHttpMethod.POST_METHOD, responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400",
                    description = "Los datos proporcionados no son válidos.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "401",
                    description = "No esta autorizado correctamente para ejecutar esta operación.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para acceder al servidor.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "500",
                    description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "503",
                    description = "Servicio no disponible.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    })
    })
    public Mono<ResponseDto<AccountDetailDto>> getAccountByid(
            @PathVariable Long cuentaId) {
        return accountService.getAccountDetailById(cuentaId)
                .map(ResponseUtil::getSuccessfulResponse);
    }




    @DeleteMapping(
            value = ApiPath.GET_ACOUNTT_ID,
          produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
           tags = ApiConstants.TAG_WORK_ORDER,
           summary = ApiConstants.DELETE_ACCOUNT_SUMMARY,
          description = ApiConstants.POST_WORK_ORDER_DESC,
           method = ApiHttpMethod.POST_METHOD, responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa."),
          @ApiResponse(responseCode = "400",
                   description = "Los datos proporcionados no son válidos.",
                   content = {
                            @Content(
                                  mediaType = "application/json",
                                 schema = @Schema(implementation = ApiException.class)
                            )
                   }),
           @ApiResponse(responseCode = "401",
                   description = "No esta autorizado correctamente para ejecutar esta operación.",
                   content = {
                           @Content(
                                 mediaType = "application/json",
                                  schema = @Schema(implementation = ApiException.class)
                           )
                    }),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para acceder al servidor.",
                   content = {
                           @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
           @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                          )
                   }),
           @ApiResponse(responseCode = "500",
                   description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                   content = {
                            @Content(
                                   mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                           )
                    }),
           @ApiResponse(responseCode = "503",
                   description = "Servicio no disponible.",
                    content = {
                           @Content(
                                    mediaType = "application/json",
                                   schema = @Schema(implementation = ApiException.class)
                            )
                    })
   })
    public Mono<ResponseDto<AccountDto>> DeleteAccount(
           @PathVariable Long cuentaId) {

        return accountService.deleteAcount(cuentaId)
               .map(ResponseUtil::getSuccessfulResponse);
    }


    @PutMapping(
            value = ApiPath.GET_ACOUNT_ID,
            produces = {MediaType.APPLICATION_JSON_VALUE})
        @ResponseStatus(HttpStatus.OK)
     @Operation(
            tags = ApiConstants.TAG_WORK_ORDER,
            summary = ApiConstants.PUT_ACCOUNT_DEPOSITAR_SUMMARY,
            description = ApiConstants.POST_WORK_ORDER_DESC,
            method = ApiHttpMethod.POST_METHOD, responses = {
           @ApiResponse(responseCode = "200", description = "Operación exitosa."),
           @ApiResponse(responseCode = "400",
                   description = "Los datos proporcionados no son válidos.",
                   content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "401",
                    description = "No esta autorizado correctamente para ejecutar esta operación.",
                    content = {
                            @Content(
                                   mediaType = "application/json",
                                   schema = @Schema(implementation = ApiException.class)
                           )
                   }),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para acceder al servidor.",
                    content = {
                            @Content(
                                   mediaType = "application/json",
                                   schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                   content = {
                           @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "500",
                    description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "503",
                    description = "Servicio no disponible.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    })
    })
    public Mono<ResponseDto<AccountDto>> depositaryClient(
            @PathVariable Long cuentaId,
            @RequestParam Double monto) {
        return accountService.depositoryByClient(cuentaId,monto)
                .map(ResponseUtil::getSuccessfulResponse);
    }

    @PutMapping(
            value = ApiPath.GET_ACOUNTR_ID,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            tags = ApiConstants.TAG_WORK_ORDER,
            summary = ApiConstants.PUT_ACCOUNT_RETIRAR_SUMMARY,
            description = ApiConstants.POST_WORK_ORDER_DESC,
            method = ApiHttpMethod.POST_METHOD, responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "400",
                    description = "Los datos proporcionados no son válidos.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "401",
                    description = "No esta autorizado correctamente para ejecutar esta operación.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para acceder al servidor.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Recurso inválido.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "500",
                    description = "Ocurrio un error inesperado. "
                            + "Por favor contactarse con el Soporte Técnico.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    }),
            @ApiResponse(responseCode = "503",
                    description = "Servicio no disponible.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiException.class)
                            )
                    })
    })
    public Mono<ResponseDto<AccountDto>> withdrawClient(
            @PathVariable Long cuentaId,
            @RequestParam Double monto) {
        return accountService.withdrawByClient(cuentaId,monto)
                .map(ResponseUtil::getSuccessfulResponse);
    }


    @PutMapping("/{id}/saldo")
    public Mono<ResponseDto<AccountDto>> actualizarSaldo(@PathVariable Integer id, @RequestBody BigDecimal nuevoSaldo) {
        return accountService.actualizarSaldo(id,nuevoSaldo)
                .map(ResponseUtil::getSuccessfulResponse);
    }

}
