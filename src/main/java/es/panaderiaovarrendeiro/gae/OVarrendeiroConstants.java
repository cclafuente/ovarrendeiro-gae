package es.panaderiaovarrendeiro.gae;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;


public class OVarrendeiroConstants {

	public static final String SESSION_INFO_KEY = "sessionInfo";
	public static final Long menuInicio = 1L;
	public static final Long menuLocalizacion = 5L;
	public static final Long menuAdmin = 6L;
	public static final Long menuPedidos = 7L;
	
	public static final Long submenuProductos = 1L;
	public static final Long submenuNoticias = 2L;
	public static final Long submenuClientes = 3L;
	public static final Long submenuFacturas = 4L;
	public static final Long submenuPedidos = 7L;
	
	
	public static final NumberFormat defaultNumberFormat;
	public static final DateFormat dateFormatGQL;
	
	static{
		defaultNumberFormat = new DecimalFormat("#.##");
		dateFormatGQL = new SimpleDateFormat("yyyy-MM-dd");
	}

	
  public static final String CAR_ENTITY = "Car";
  public static final String CAR_YEAR = "year";
  public static final String CAR_DATE = "date";
  public static final String CAR_TYPE = "type";
  public static final String CAR_OWNER = "owner";
  public static final String CAR_MODEL = "model";
  public static final String CAR_MAKE = "make";
  public static final String CAR_MILEAGE = "mileage";
  public static final String CAR_STATUS = "status";
  public static final String CAR_STATUS_SOLD = "sold";
  public static final String CAR_STATUS_AVL = "avl";
  public static final String CAR_PRICE = "expectedPrice";
  public static final String CAR_EXPRICE = "price";
  public static final String CAR_BUYER = "buyer";
  public static final String CAR_COLOR = "color";
  public static final String CAR_IMAGE = "image";
  public static final String JSON_MODEL = "responseJson";
  public static final String JSON_VIEW = "AutoShoppeJson";
  public static final String AUTH_CONTINUE_URL = "/home.do";
  public static final String AUTH_LOGIN = "loginUrl";
  public static final String AUTH_LOGOUT = "logoutUrl";
  public static final String AUTH_USER = "user";
  public static final String CAR_KEY = "key";
  public static final String GQL_YEAROP = "yearOp";
  public static final String GQL_PRICEOP = "priceOp";
  public static final String BEAN_USERSERVICE = "userService";
  public static final String BEAN_DSSERVICE = "datastoreService";
  public static final String AUDIT_ENTITY = "Audit";
  public static final int PAGE_SIZE = 20;
  public static final String PAGE_PARAM = "page";
  public static final String MSG_BEAN_INIT_FAILURE = "One of the dependencies" +
      " wasn't set for ";
  public static final String CAR_KEY_PATTERN = "Car:";
  public static final String MSG_INVALID_NUMBER = "Please enter valid " +
      "numeric data";
  public static final String MSG_MISSING_PARAM = "Please enter all required " +
      "values";
  public static final String MSG_INVALID_SEARCH = "Please enter search criteria ";

  public static final int PAGE_INDEX = 1;
  public static final int PAGE_VEHICLE_LIST = 2;
  public static final int PAGE_EDIT_VEHICLE = 3;
  
  public enum Operator {
    EQUALS,
    LESS_THAN_EQUALS,
    GREATER_THAN_EQUALS
  }

  ;

}




