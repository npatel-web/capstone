package com.example.getinn.utilities;

import com.example.getinn.models.User;

public class ECONSTANT {

    public static final String KEY_CAT_ID = "KEY_CAT_ID";
    public static User logedUser;
    public static final String TAG = "TAG";
    //private static final String IP = "http://10.0.0.212/";
    private static final String IP = "https://gelanihetal.altervista.org/";
    private static final String BASE_URL = IP + "movie_ticket_api/";
    private static final String BASE_IMAGE_URL = IP + "movie_ticket_api/";
    public static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
    public static final String CAT_ID_KEY = "CAT_ID_KEY";
    public static final String KEY_LOGED_USER = "KEY_LOGED_USER";
    public static final String URL_GET_BANNER = BASE_URL + "EGetBanner.php";
    public static final String URL_IMG_BANNERS = BASE_IMAGE_URL + "images/banners/";

    public static final String URL_SIGNUP = BASE_URL + "signup.php";
    public static final String URL_ADD_CARD = BASE_URL+ "addCard.php";
    public static final String URL_IMG_CATAGORY = BASE_IMAGE_URL + "images/Catagory/";
    public static final String URL_IMG_USER = BASE_IMAGE_URL + "images/user/";
    public static final String URL_LOGIN = BASE_URL + "login.php";
    public static final String URL_ADD_TO_ORDERS = BASE_URL + "EAddProductOrder.php";
    public static final String URL_MOVIES_TYPE = BASE_URL + "EGetMoviesType.php?type=";

    public static final String URL_IMG_PRODUCTS = BASE_IMAGE_URL + "images/Products/";
    public static final String URL_IMG_SUBCATAGORY = BASE_IMAGE_URL + "images/Products/";
    public static final String URL_CATAGORIES = BASE_URL + "ECatagory.php";

    public static final String URL_EPRODUCTSBYSUBCATID = BASE_URL + "EProductsBySubCatId.php?cid=";
    public static final String URL_GET_PRODUCT_ORDER = BASE_URL + "EGetProductOrders.php?user_id=";
    public static final String URL_MASTERDTAIL_PRODUCTS_ECOM = BASE_URL + "EMasterDeailProducts.php?p_id=";
    public static final String URL_ADDTOCAR = BASE_URL + "EAddToCArt.php?id=";



}
