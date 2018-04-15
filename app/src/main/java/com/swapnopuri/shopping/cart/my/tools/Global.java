package com.swapnopuri.shopping.cart.my.tools;

import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MsubCategory;

import java.util.ArrayList;

/**
 * Created by JEWEL on 7/13/2016.
 */
public class Global {

    public static ArrayList<MRecipe> products;
    public static ArrayList<MsubCategory> subCatList;

    public static int categoriId;
    public static String subCatImageURL;
    public static String singleImageURL;
    public static String brandLogo;
    public static String bannerLink;
    public static String categoryTitle;
    public static int productId;
    public static int sizePos;
    public static int productPos;
    public static int orderLayoutDissmiss;


    public static final String APP_NAME = "Candy Party";
    public static final String APK_LINK = "https://play.google.com/store/apps/details?id=com.swapnopuri.bangla.bangladeshi.deshi.recipes";

    //main state names
    public static final String NEVER_SHOW = "never_show";
    public static final String STATE = "state";
    public static final String STATE_TUT = "tut";
    public static final String STATE_MAIN = "main";
    public static final String STATE_SPLASH = "splash";
    public static final String STATE_REGIS = "regis";
    public static final String STATE_PROFILE = "profile";
    public static final String STATE_PROFILE2 = "profile2";

    //user info
    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_TYPE = "user_type";
    //tab names
    public static final String TAB_HOME_PAGE = "Home";
    public static final String TAB_CATEGORY = "Categoría";
    public static final String TAB_FAV = "Pedido";
    public static final String TAB_ORDER = "Pedido";
    public static final String TAB_RECIPE = "Contacto";
    public static final String TAB_DETAIL = "Detail";
    public static final String TAB_SUPPLIMENT = "Suppliment";
    public static final String TAB_REVIEW = "Review";

    //menu items name
    public static final String MENU_SEARCH = "Búsqueda";
    public static final String MENU_HALNAGAD = "সর্বশেষ হালনাগাত";
    public static final String MENU_CATEGORY = "সকল ক্যাটাগরি";
    public static final String MENU_FAVORITE = "প্রিয় রেসিপি";
    public static final String MENU_ASK = "রেসেপি চায় / যোগাযোগ";
    public static final String MENU_VIDEO = "সকল ভিডিও";
    public static final String MENU_TUT = "ব্যবহার নির্দেশিকা";

    //admob & analytics
    public static final String BANNER_APP_ID_TEST = "ca-app-pub-3852049226216249~9232307213";
    public static final String BANNER_APP_ID = "ca-app-pub-1386205867680580~9210543752";
    public static final String BANNER_APP_UID = "ca-app-pub-1386205867680580/3024409355";
    public static final String INTERSTISIAL_UID = "ca-app-pub-1386205867680580/1712676150";
    public static final String BANNER_APP_UID_TEST = "ca-app-pub-3852049226216249/1709040413";
    public static final String DB_PASS = "test123";
    public static final String TITLE_VIDEO = "ভিডিও দেখে সহজে রাঁধুন ";

    public static final String ANALYTICS_ID="UA-75029153-3";
    public static final int LIMIT_VIDEO_INTERSTITAL = 1;
    public static final int LIMIT_RECIPE_INTERSTITIAL = 5;



    //urls

    public static final String BASE = "http://www.radhooni.com/";
    public static final String BASE2 = "http://www.radhooni.com/";
    public static final String API_CATEGORY_LIST = "candyparty/api/v2/category.php";
    public static final String API_HOME_LIST = "candyparty/api/v2/home.php";
    public static final String API_SUB_CAT = "candyparty/api/v2/sub_category.php";
    //    public static final String API_CATEGORY_LIST = "content/api/category.php";
    public static final String API_RECIPIES = "candyparty/api/v2/recipes_a.php";
//    public static final String API_RECIPIES = "candyparty/api/v2/recipes_a_oct-4-17.php";
    //    public static final String API_RECIPIES = "content/api/v2/recipes_a.php";
    public static final String API_VIDEOS = "content/api/v2/videos.php";
    public static final String API_SEND_EMAIL = "content/api/v2/email.php";
    public static final String API_SYNC = "candyparty/api/v2/sync_a.php";
    public static final String API_PROFILE = "candyparty/api/v2/profile.php";
    public static final String API_LOGIN = "candyparty/api/v2/login.php";
    public static final String API_REGISTRATION = "candyparty/api/v2/registration.php";

    public static final String API_BASE_BACKEND = "http://www.radhooni.com/candyparty/admin/uploads/";
    //    public static final String API_BASE_BACKEND = "http://www.radhooni.com/content/backend/uploads/";
    public static final String API_VIDEO_IMAGE = "http://www.radhooni.com/content/backend/uploads/menu/thumbnail/";
    public static final String API_NOTIFICATION = "http://www.radhooni.com/content/api/notification.php";
    public static final String URL_SOURCE = "http://www.radhooni.com/sources/";
    public static final String API_TIME_TRACKING = "content/api/v2/time_tracking.php";
    public static final String API_FAV_RECIPE = "content/api/v2/users_a.php";
    public static final String API_SEND_FAV_RECIPE = "content/api/v2/fav_json.php";
    public static final String API_REVIEW = "candyparty/api/v2/product_review.php";
    public static final String API_ADD_REVIEW = "candyparty/api/v2/review_add.php";
    public static final String API_PRODUCT_DETAIL = "candyparty/api/v2/product_detail.php";

    public static final String API_ORDER_REQ = "http://www.radhooni.com/ecom/api/v2/order_req.php";
    public static final String API_SHIPMENT = "http://www.radhooni.com/candyparty/api/v2/shipment.php";
    public static final String API_ORDER_SEND = "http://www.radhooni.com/candyparty/api/v2/order_send.php";
    public static final String API_ORDER_APPROVE = "http://www.radhooni.com/candyparty/api/v2/order_approve.php";
    public static final String API_ORDER_NOTIFICATION = "http://www.radhooni.com/candyparty/api/v2/order_notification2.php";
//    public static final String API_ORDER_NOTIFICATION = "http://www.radhooni.com/ecom/api/v2/order_notification2.php";

    public static final String API_FIREBASE_PUSH = "http://www.step2code.com/firebase_push";

    public static final String VIDEO_DOWNLOAD_DIR = "video";
    public static final String REF_INTERSTITIAL = "pref_vungle_recipe";
    //app rate
    public static final int AFTER_HOW_MANY_DAY = 5;
    public static final int HOW_MANY_LAUNCHED = 5;
    //google analytics
    public static final String GOOGLE_ANALYTICS_ID = "UA-75029153-1";
    public static final String CATEGOTY_ACTION_MASSAGE = "";
    public static final String RECIPE_ACTION_MASSAGE = "";
    public static final String VIDEO_ACTION_MASSAGE = "ভিডিও নেমেছে ...";


    //email
    public static final String EMAIL_TO = "candyparty@gmail.com";
    public static final String EMAIL_SUBJECT_TXT = "Asunto ";
    public static final String EMAIL_SUBJECT = "Contacto";
    public static final String EMAIL_FOOTER = "";
    public static final String EMAIL_SEND_TXT = "Enviar";
    public static final String EMAIL_FROM_TXT = "De";
    public static final String EMAIL_TO_TXT = "Para";
    public static final String EMAIL_DETAILS_TXT = "Detalles ";
    // message
    public static final String MSG_LIKE_RECIPE = "আপনার পছন্দের তালিকায় যোগ করা হয়েছে।";
    public static final String MSG_SEND_MAIL = "Successfully done";
    public static final String MSG_NO_INTERNET = "No Internet!!";
    public static final String MSG_SORRY = "দুঃখিত !";
    public static final String MSG_THANKS = "ধন্যবাদ";
    public static final String MSG_OK = "ঠিক আছে";
    public static final String MSG_HIDE = "আড়াল করুন ";
    public static final String MSG_CANCEL = "বাতিল করুন ";
    public static final String MSG_EXIST_REVIEW = "You already gave a review !!";
    public static final String MSG_EXIT = "Press again to exit";
    public static final String MSG_TITLE_NO_VIDEO = "ভিডিও নেই";
    public static final String MSG_NO_VIDEO = "দুঃখিত - এই রেসিপির কোন ভিডিও এখনও সংযুক্ত হয়নি।";
    public static final String SYNC = "sync_date";
    public static final String TITLE_CONFIROMATION = "Confirmation";
    public static final String MSG_CHECKOUT_CONFIRIM = "Successfully Done!!";

    public static final String MSG_NOT_TITLE_USER = "Situación del pedido";
    public static final String MSG_NOT_TITLE_ADMIN = "New Order";
    public static final String MSG_NOT_ORDER_RECEIVED = "Su pedido está en camino. Lo recibirá pronto.";

    public static final String BTN_TXT_CURRENT_ORDER = "Pedido actual";
    public static final String BTN_TXT_ALL_ORDER = "Toda la orden";
    public static final String BTN_TXT_ACCEPT_ORDER = "Aceptar pedido";
    public static final String BTN_TXT_SUBMIT_ORDER = "Hacer pedido";
    public static final String BTN_TXT_CANCEL_ORDER = "Cancelar pedido";

    public static final String COLOR_MAIN = "#6C4878";


    public static final int STATUS_APPROVED = 2;

    public static final int USER_ADMIN = 1;

    public static final int TYPE_ORDER = 1;
    public static final int NOT_FOR_ALL = 0;
    public static final int NOT_FOR_ADMIN = 1;
    public static final int NOT_FOR_ALL_EXC_ADMIN = -1;







}