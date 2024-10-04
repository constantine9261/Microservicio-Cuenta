package com.banking.NttdataProyectoIAccountMs.util.api;

public class ApiPath {

  public static final String EMPTY_PATH = "";

  public static final String SETTING_PARAMETER_ENDPOINT = "/work-order-setting-parameters";

  //Petitions
  public static final String CUENTAS_ENDPOINT = "/cuentas";
  public static final String PRODUCTS_ENDPOINT = "/products";
  public static final String AUTHORIZE_WORK_ORDER = "/authorize";
  public static final String GET_AUTHORIZATIONS_WORK_ORDER = "/authorizations";
  public static final String GET_AUTHORIZATION_WORK_ORDER = "{workOrderId}/authorization";

  public static final String GET_WORK_ORDER = "{workOrderId}";
  public static final String GET_WORK_OR_BY_PETITION = "{workOrderId}/work-order";
  public static final String UPDATE_PETITION_STATE = "/{workOrderId}/state";

  //WORK ORDER TICKET
  public static final String WORK_ORDERS_TICKET_ENDPOINT = "/work-order-tickets";

  public static final String GET_WORK_ORDER_TICKET = "{workOrderTicketId}";
  public static final String DISPATCH_WORK_ORDER_TICKET = "/dispatch";
  public static final String VERIFY_WORK_ORDER_TICKET = "/verify";


  //WorkOrders
  public static final String GET_WORK_ORDER_DETAIL = "{workOrderDetailId}";
  public static final String WORK_ORDERS_DETAIL_ENDPOINT = "/work-order-details";
  public static final String UPDATE_WORK_ORDER_STATE = "/{workOrderId}/state";

  //Setting Parameter
  public static final String DELETE_SETTING_PARAMETER = "/{settingParameterId}";
  public static final String REPLACE_SETTING_PARAMETER = "/{settingParameterId}";


  public static final String GET_APPLICATION = "{applicationId}";
  public static final String APPLICATIONS_ENDPOINT = "/applications";

  //client
  public static final String GET_ACOUNTT_ID = "{cuentaId}";
  public static final String GET_ACOUNT_ID = "{cuentaId}/depositar";
  public static final String GET_ACOUNTR_ID = "{cuentaId}/retirar";
}
