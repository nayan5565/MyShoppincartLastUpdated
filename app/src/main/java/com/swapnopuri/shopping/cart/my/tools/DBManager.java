package com.swapnopuri.shopping.cart.my.tools;

import android.content.ContentValues;
import android.util.Log;

import com.google.gson.Gson;
import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MColor;
import com.swapnopuri.shopping.cart.my.model.MFOrder;
import com.swapnopuri.shopping.cart.my.model.MNew;
import com.swapnopuri.shopping.cart.my.model.MOrder;
import com.swapnopuri.shopping.cart.my.model.MOrderedItem;
import com.swapnopuri.shopping.cart.my.model.MRecipe;
import com.swapnopuri.shopping.cart.my.model.MSize;
import com.swapnopuri.shopping.cart.my.model.MsubCategory;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JEWEL on 7/27/2016.
 */
public class DBManager {
    public static final String TABLE_CATEGORY = "tbl_category";
    public static final String TABLE_DATE = "tbl_date";
    public static final String TABLE_SUB_CATEGORY = "tbl_sub_category";
    public static final String TABLE_CATEGORY2 = "tbl_category2";
    public static final String TABLE_RECEIPE = "tbl_recipe";
    public static final String TABLE_TRENDY = "tbl_trendy";
    public static final String TABLE_FLASH = "tbl_flash";
    public static final String TABLE_TOP_WEEK = "tbl_top_week";
    public static final String TABLE_BANNER = "tbl_banner";
    public static final String TABLE_BRAND = "tbl_brand";
    public static final String TABLE_NEW = "tbl_new";
    public static final String TABLE_VIDEO = "tbl_videos";
    public static final String TABLE_ADS = "tbl_ads";
    public static final String TABLE_SHIPMENT = "tbl_shipment";
    public static final String TABLE_TIME_TRACKER = "tbl_time_tracker";
    public static final String TABLE_ORDER = "tbl_order";
    public static final String TABLE_PROFILE = "tbl_profile";
    public static final String TABLE_GALLERY = "tbl_gallery";
    public static final String TABLE_REVIEW = "tbl_review";
    public static final String TABLE_ORDERED_ITEM = "tbl_ordered_item";
    public static final String TABLE_FAV_RECIPE = "tbl_fav_recipe";

    public static final String KEY_ID = "id";
    public static final String KEY_FAV = "fav";
    public static final String KEY_MOD_TIME = "lastMod";
    public static final String KEY_STATUS = "status";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_LOGOUT = "logout";


    private static final String DB_NAME = "candyParty.db";


    private static final String CREATE_TABLE_CATEGORY = DBQuery.init()
            .newTable(TABLE_CATEGORY)
            .addField("categoryId", DBQuery.INTEGER_PRI)
            .addField("categoryTotalRecipe", DBQuery.INTEGER)
            .addField("categoryType", DBQuery.INTEGER)
            .addField("categoryDelete", DBQuery.INTEGER)
            .addField("categoryUpdateAvailable", DBQuery.INTEGER)

            .addField("categoryTitle", DBQuery.TEXT)
            .addField("categoryDetails", DBQuery.TEXT)
            .addField("categoryPhoto", DBQuery.TEXT)
            .addField("categoryThumb", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_SUB_CATEGORY = DBQuery.init()
            .newTable(TABLE_SUB_CATEGORY)
            .addField("categoryId", DBQuery.INTEGER_PRI)
            .addField("categoryTotalRecipe", DBQuery.INTEGER)
            .addField("categoryType", DBQuery.INTEGER)
            .addField("categoryDelete", DBQuery.INTEGER)
            .addField("cat_id", DBQuery.INTEGER)
            .addField("categoryUpdateAvailable", DBQuery.INTEGER)

            .addField("categoryTitle", DBQuery.TEXT)
            .addField("categoryDetails", DBQuery.TEXT)
            .addField("categoryPhoto", DBQuery.TEXT)
            .addField("categoryThumb", DBQuery.TEXT)
            .getTable();


    private static final String CREATE_TABLE_CATEGORY2 = DBQuery.init()
            .newTable(TABLE_CATEGORY2)
            .addField("categoryId", DBQuery.INTEGER_PRI)
            .addField("categoryTotalRecipe", DBQuery.INTEGER)
            .addField("categoryType", DBQuery.INTEGER)
            .addField("categoryDelete", DBQuery.INTEGER)
            .addField("categoryUpdateAvailable", DBQuery.INTEGER)

            .addField("categoryTitle", DBQuery.TEXT)
            .addField("categoryDetails", DBQuery.TEXT)
            .addField("categoryPhoto", DBQuery.TEXT)
            .addField("categoryThumb", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_NEW = DBQuery.init()
            .newTable(TABLE_NEW)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("isNew", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("productId", DBQuery.INTEGER)
            .getTable();
    private static final String CREATE_TABLE_RECIPE = DBQuery.init()
            .newTable(TABLE_RECEIPE)
            .addField("Id", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("Title", DBQuery.INTEGER)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Photo", DBQuery.TEXT)
            .addField("Video", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)
            .addField("recipeDelete", DBQuery.INTEGER)
            .addField("selectedPos", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .addField("price", DBQuery.TEXT)
            .addField("DiscountRate", DBQuery.TEXT)
            .addField("Description", DBQuery.TEXT)
            .addField("cartStatus", DBQuery.INTEGER)
            .addField("addCart", DBQuery.INTEGER)
            .addField("quantity", DBQuery.INTEGER)
            .addField("status", DBQuery.INTEGER)
            .addField("selectedColor", DBQuery.TEXT)
            .addField("colors", DBQuery.TEXT)
            .addField("selectedSize", DBQuery.TEXT)
            .addField("isNew", DBQuery.INTEGER)
            .addField("sizes", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_FLASH = DBQuery.init()
            .newTable(TABLE_FLASH)
            .addField("Id", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("Title", DBQuery.INTEGER)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Photo", DBQuery.TEXT)
            .addField("Video", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)
            .addField("recipeDelete", DBQuery.INTEGER)
            .addField("selectedPos", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .addField("price", DBQuery.TEXT)
            .addField("DiscountRate", DBQuery.TEXT)
            .addField("Description", DBQuery.TEXT)
            .addField("cartStatus", DBQuery.INTEGER)
            .addField("addCart", DBQuery.INTEGER)
            .addField("quantity", DBQuery.INTEGER)
            .addField("status", DBQuery.INTEGER)
            .addField("selectedColor", DBQuery.TEXT)
            .addField("colors", DBQuery.TEXT)
            .addField("selectedSize", DBQuery.TEXT)
            .addField("isNew", DBQuery.INTEGER)
            .addField("sizes", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_TOP_WEEK = DBQuery.init()
            .newTable(TABLE_TOP_WEEK)
            .addField("Id", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("Title", DBQuery.INTEGER)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Photo", DBQuery.TEXT)
            .addField("Video", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)
            .addField("recipeDelete", DBQuery.INTEGER)
            .addField("selectedPos", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .addField("price", DBQuery.TEXT)
            .addField("DiscountRate", DBQuery.TEXT)
            .addField("Description", DBQuery.TEXT)
            .addField("cartStatus", DBQuery.INTEGER)
            .addField("addCart", DBQuery.INTEGER)
            .addField("quantity", DBQuery.INTEGER)
            .addField("status", DBQuery.INTEGER)
            .addField("selectedColor", DBQuery.TEXT)
            .addField("colors", DBQuery.TEXT)
            .addField("selectedSize", DBQuery.TEXT)
            .addField("isNew", DBQuery.INTEGER)
            .addField("sizes", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_TRENDY = DBQuery.init()
            .newTable(TABLE_TRENDY)
            .addField("Id", DBQuery.INTEGER)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("Title", DBQuery.INTEGER)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Photo", DBQuery.TEXT)
            .addField("Video", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)
            .addField("recipeDelete", DBQuery.INTEGER)
            .addField("selectedPos", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .addField("price", DBQuery.TEXT)
            .addField("DiscountRate", DBQuery.TEXT)
            .addField("Description", DBQuery.TEXT)
            .addField("cartStatus", DBQuery.INTEGER)
            .addField("addCart", DBQuery.INTEGER)
            .addField("quantity", DBQuery.INTEGER)
            .addField("status", DBQuery.INTEGER)
            .addField("selectedColor", DBQuery.TEXT)
            .addField("colors", DBQuery.TEXT)
            .addField("selectedSize", DBQuery.TEXT)
            .addField("isNew", DBQuery.INTEGER)
            .addField("sizes", DBQuery.TEXT)
            .getTable();

    private static final String CREATE_TABLE_VIDEO = DBQuery.init()
            .newTable(TABLE_VIDEO)
            .addField("Id", DBQuery.INTEGER_PRI)
            .addField("CategoryId", DBQuery.INTEGER)
            .addField("menuTitle", DBQuery.TEXT)
            .addField("menuFile_name", DBQuery.TEXT)
            .addField("CategoryTitle", DBQuery.TEXT)
            .addField("Thumb", DBQuery.TEXT)
            .addField("Ingredients", DBQuery.TEXT)
            .addField("Process", DBQuery.TEXT)
            .addField("PPhoto", DBQuery.TEXT)
            .addField("menuVideo", DBQuery.TEXT)
            .addField("SearchTag", DBQuery.TEXT)
            .addField("TypeOne", DBQuery.INTEGER)
            .addField("TypeTwo", DBQuery.INTEGER)
            .addField("TypeThree", DBQuery.INTEGER)
            .addField("TypeFour", DBQuery.INTEGER)
            .addField("TypeFive", DBQuery.INTEGER)

            .addField("fav", DBQuery.INTEGER)
            .addField("view", DBQuery.INTEGER)
            .getTable();

    private static final String CREATE_TABLE_ADS = DBQuery.init()
            .newTable(TABLE_ADS)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("productId", DBQuery.INTEGER)
            .getTable();
    private static final String CREATE_TABLE_DATE = DBQuery.init()
            .newTable(TABLE_DATE)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("date", DBQuery.TEXT)
            .getTable();

    private static final String CREATE_TABLE_TIME_TRACKER = DBQuery.init()
            .newTable(TABLE_TIME_TRACKER)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("login", DBQuery.TEXT)
            .addField("logout", DBQuery.TEXT)
            .addField("status", DBQuery.INTEGER)
            .getTable();

    private static final String CREATE_TABLE_ORDER = DBQuery.init()
            .newTable(TABLE_ORDER)
            .addField("_id", DBQuery.INTEGER_PRI_AUTO)
            .addField("id", DBQuery.INTEGER)
            .addField("orderId", DBQuery.INTEGER)
            .addField("userId", DBQuery.INTEGER)
            .addField("totalQuantity", DBQuery.TEXT)
            .addField("totalAmount", DBQuery.TEXT)
            .addField("userName", DBQuery.TEXT)
            .addField("dateTime", DBQuery.TEXT)
            .addField("userPic", DBQuery.TEXT)
            .addField("status", DBQuery.INTEGER)
            .getTable();
    private static final String CREATE_TABLE_BANNER = DBQuery.init()
            .newTable(TABLE_BANNER)
            .addField("id", DBQuery.INTEGER_PRI)
            .addField("title", DBQuery.TEXT)
            .addField("linkType", DBQuery.TEXT)
            .addField("link", DBQuery.TEXT)
            .addField("imageUrl", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_BRAND = DBQuery.init()
            .newTable(TABLE_BRAND)
            .addField("id", DBQuery.INTEGER_PRI)
            .addField("title", DBQuery.TEXT)
            .addField("linkType", DBQuery.TEXT)
            .addField("link", DBQuery.TEXT)
            .addField("imageUrl", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_ORDERED_ITEMS = DBQuery.init()
            .newTable(TABLE_ORDERED_ITEM)
            .addField("_id", DBQuery.INTEGER_PRI_AUTO)
            .addField("id", DBQuery.INTEGER)
            .addField("orderId", DBQuery.INTEGER)
            .addField("userId", DBQuery.INTEGER)
            .addField("amount", DBQuery.TEXT)
            .addField("title", DBQuery.TEXT)
            .addField("thumb", DBQuery.TEXT)
            .addField("date", DBQuery.TEXT)
            .addField("quantity", DBQuery.INTEGER)
            .addField("status", DBQuery.INTEGER)
            .getTable();

    private static final String CREATE_TABLE_SHIPMENT = DBQuery.init()
            .newTable(TABLE_SHIPMENT)
            .addField("_id", DBQuery.INTEGER_PRI_AUTO)
            .addField("phone", DBQuery.TEXT)
            .addField("streetAddress1", DBQuery.TEXT)
            .addField("streetAddress2", DBQuery.TEXT)
            .addField("state", DBQuery.TEXT)
            .addField("country", DBQuery.TEXT)
            .addField("city", DBQuery.TEXT)
            .addField("zipCode", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_REVIEW = DBQuery.init()
            .newTable(TABLE_REVIEW)
            .addField("Id", DBQuery.INTEGER_PRI_AUTO)
            .addField("title", DBQuery.TEXT)
            .addField("fullname", DBQuery.TEXT)
            .addField("date", DBQuery.TEXT)
            .addField("productId", DBQuery.INTEGER)
            .addField("star", DBQuery.TEXT)
            .getTable();

    private static final String CREATE_TABLE_PROFILE = DBQuery.init()
            .newTable(TABLE_PROFILE)
            .addField("id", DBQuery.INTEGER_PRI_AUTO)
            .addField("message", DBQuery.TEXT)
            .addField("fullname", DBQuery.TEXT)
            .addField("email", DBQuery.TEXT)
            .addField("phone", DBQuery.TEXT)
            .addField("birthDate", DBQuery.TEXT)
            .addField("gender", DBQuery.TEXT)
            .addField("country", DBQuery.TEXT)
            .addField("image", DBQuery.TEXT)
            .getTable();
    private static final String CREATE_TABLE_GALLERY = DBQuery.init()
            .newTable(TABLE_GALLERY)
            .addField("Id", DBQuery.INTEGER)
            .addField("Photo", DBQuery.TEXT)
            .addField("ProductId", DBQuery.INTEGER)
            .getTable();
    private static DBManager instance;
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase db;

    private DBManager() {
        openDB();
        createTable();
    }

    public static DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    public static String getQueryAll(String table, String primaryKey, String value) {
        return "select * from " + table + " where " + primaryKey + "='" + value + "'";
    }

    public static String getQueryDate(String table, String primaryKey) {
        return "select * from " + table + " where " + primaryKey + "='";
    }

    public static String getQueryAll(String table) {
        return "select * from " + table;
    }

    public static String getRecipeQuery(int id) {
        return "select * from " + TABLE_RECEIPE + " where CategoryId='" + id + "' OR TypeOne='" + id + "' " +
                " OR TypeTwo='" + id + "' OR TypeThree='" + id + "' OR TypeFour='" + id + "' OR TypeFive='" + id + "' order by Id asc";
    }

    public static String getSubCatQuery(int id) {
        return "select * from " + TABLE_SUB_CATEGORY + " where CategoryId='" + id + "' ";
    }

    public static String getTopWeek(int id) {
        return "select * from " + TABLE_TOP_WEEK + " where CategoryId='" + id + "' OR TypeOne='" + id + "' " +
                " OR TypeTwo='" + id + "' OR TypeThree='" + id + "' OR TypeFour='" + id + "' OR TypeFive='" + id + "' order by Id asc";
    }

    public static String getTrendyQuery(int id) {
        return "select * from " + TABLE_TRENDY + " where CategoryId='" + id + "' OR TypeOne='" + id + "' " +
                " OR TypeTwo='" + id + "' OR TypeThree='" + id + "' OR TypeFour='" + id + "' OR TypeFive='" + id + "' order by Id asc";
    }

    public static String getFlashQuery(int id) {
        return "select * from " + TABLE_FLASH + " where CategoryId='" + id + "' OR TypeOne='" + id + "' " +
                " OR TypeTwo='" + id + "' OR TypeThree='" + id + "' OR TypeFour='" + id + "' OR TypeFive='" + id + "' order by Id asc";
    }

    public static String getBrandQuery(int id) {
        return "select * from " + TABLE_BRAND + " where id='" + id + "' ";
    }

    public static String getBannerQuery(int id) {
        return "select * from " + TABLE_BANNER + " where id='" + id + "' ";
    }

    public static String getRecipeQueryJointTable(int id) {
        return "select a.Id,a.CategoryId,a.TypeOne,a.TypeTwo,a.TypeThree,a.TypeFour,a.TypeFive,a.recipeDelete,a.fav,a.view,a.Title,a.Thumb,a.Photo,a.Ingredients,a.Process,a.PPhoto,a.CategoryTitle,a.SearchTag,a.Video,a.price,a.DiscountRate,a.cartStatus,a.addCart,a.quantity,a.status,a.sizeView,a.color,b.isNew from tbl_recipe a left join tbl_new b on a.CategoryId=b.CategoryId AND a.Id=b.productId where a.CategoryId='" + id + "'";
    }


    public static String getCartRecipe() {
        return "select * from " + TABLE_RECEIPE + " where quantity > '0'";
    }

    public static String getQueryFav(String table) {
        return "select * from " + table + " where " + KEY_FAV + "='1'";
    }

    public static String getQueryTimeTracker() {
        return "select * from " + TABLE_TIME_TRACKER + " where " + KEY_STATUS + "='0'";
    }

    public static String getCurrentOrderedItem(int orderId, int userId) {
        return "select * from " + TABLE_ORDERED_ITEM + " where orderId='" + orderId + "' AND userId='" + userId + "'";
    }

    public static String getNewQuery(int categoryId, int recipeId) {
        return "select * from " + TABLE_NEW + " where CategoryId='" + categoryId + "' AND productId='" + recipeId + "'";
    }

    public static String getQueryAllOrder() {
        return "select * from " + TABLE_ORDER + " where status >'0' ORDER BY datetime(dateTime) desc";
    }

    public static String getQueryReview(int productId) {
        return "select * from " + TABLE_REVIEW + " where productId='" + productId + "'";//+ "ORDER BY datetime(date) desc";
    }

    public static String getQueryGallery(int productId) {
        return "select * from " + TABLE_GALLERY + " where productId='" + productId + "'";//+ "ORDER BY datetime(date) desc";
    }

    public static String getQueryAllOrderAdmin() {
        return "select * from " + TABLE_ORDER + " where status ='2' ORDER BY datetime(dateTime) desc";
//        return "select * from " + TABLE_ORDER + " where status ='2' ORDER BY _id desc";
    }

    public static String getQueryCurrentOrderAdmin() {
        return "select * from " + TABLE_ORDER + " where status ='1' ORDER BY datetime(dateTime) desc";
//        return "select * from " + TABLE_ORDER + " where status ='1' ORDER BY _id desc";
    }


    private void openDB() {
        SQLiteDatabase.loadLibs(MyApp.getInstance().getContext());
        File databaseFile = MyApp.getInstance().getContext().getDatabasePath(DB_NAME);
        if (!databaseFile.exists()) {
            databaseFile.mkdirs();
            databaseFile.delete();
        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile, Global.DB_PASS, null);
    }

    private void createTable() {
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_SUB_CATEGORY);
        db.execSQL(CREATE_TABLE_CATEGORY2);
        db.execSQL(CREATE_TABLE_NEW);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_VIDEO);
        db.execSQL(CREATE_TABLE_ADS);
        db.execSQL(CREATE_TABLE_DATE);
        db.execSQL(CREATE_TABLE_TIME_TRACKER);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_ORDERED_ITEMS);
        db.execSQL(CREATE_TABLE_SHIPMENT);
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_GALLERY);
        db.execSQL(CREATE_TABLE_BANNER);
        db.execSQL(CREATE_TABLE_BRAND);
        db.execSQL(CREATE_TABLE_FLASH);
        db.execSQL(CREATE_TABLE_TRENDY);
        db.execSQL(CREATE_TABLE_TOP_WEEK);
    }


    public long addData(String tableName, Object dataModelClass, String primaryKey) {
        long result = -1;
        String valueOfKey = "";
        try {
            Class myClass = dataModelClass.getClass();
            Field[] fields = myClass.getDeclaredFields();
            ContentValues contentValues = new ContentValues();

            for (Field field : fields) {
                //for getting access of private field


                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(dataModelClass);

                if (name.equalsIgnoreCase("serialVersionUID")
                        || name.equalsIgnoreCase("$change")
                        ) {

                } else {
                    contentValues.put(name, value + "");
                    if (name.equalsIgnoreCase(primaryKey)) {

                        valueOfKey = value + "";
                    }

                }


            }
            if (!valueOfKey.equals("") && Integer.parseInt(valueOfKey) < 1)
                contentValues.remove(primaryKey);
            if (!isExist(tableName, primaryKey, valueOfKey)) {
                result = db.insert(tableName, null, contentValues);
                MyLog.e("DB", "add:" + valueOfKey);
            } else {

                result = db.update(tableName, contentValues, primaryKey + "=?", new String[]{valueOfKey + ""});
                MyLog.e("DB", "update:" + valueOfKey + ":" + dataModelClass.getClass().getSimpleName());
            }


        } catch (Exception e) {
            MyLog.e("DB_ERR", e.toString());
        } finally {

        }
        return result;
    }

    public void addSubCatJson(MsubCategory msubCategory) {
        android.database.Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("categoryDelete", msubCategory.getCategoryDelete());
            values.put("cat_id", msubCategory.getCat_id());
            values.put("categoryDetails", msubCategory.getCategoryDetails());
            values.put("categoryId", msubCategory.getCategoryId());
            values.put("categoryPhoto", msubCategory.getCategoryPhoto());
            values.put("categoryThumb", msubCategory.getCategoryThumb());
            values.put("categoryTitle", msubCategory.getCategoryTitle());
            values.put("categoryType", msubCategory.getCategoryType());
            values.put("categoryUpdateAvailable", msubCategory.getCategoryUpdateAvailable());
            values.put("categoryTotalRecipe", msubCategory.getCategoryTotalRecipe());

            String sql = "select * from " + TABLE_SUB_CATEGORY + " where categoryId" + "='" + msubCategory.getCategoryId() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(TABLE_SUB_CATEGORY, values, "categoryId=?", new String[]{msubCategory.getCategoryId() + ""});
                Log.e("subCat", "update : " + update);
            } else {
                long v = db.insert(TABLE_SUB_CATEGORY, null, values);
//                Log.e("subCat", "insert : " + mCategory.getCategoryId() + " sub_catId " + mCategory.getCat_id());

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }

    public long addData(String tableName, Object dataModelClass, String primaryKey, boolean isAutoInc) {
        long result = -1;
        String valueOfKey = "";
        try {
            Class myClass = dataModelClass.getClass();
            Field[] fields = myClass.getDeclaredFields();
            ContentValues contentValues = new ContentValues();

            for (Field field : fields) {
                //for getting access of private field


                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(dataModelClass);

                if (name.equalsIgnoreCase("serialVersionUID")
                        || name.equalsIgnoreCase("$change")
                        ) {

                } else {
                    contentValues.put(name, value + "");
                    if (name.equalsIgnoreCase(primaryKey)) {

                        valueOfKey = value + "";
                    }

                }


            }
            if (isAutoInc)
                contentValues.remove(primaryKey);

            if (!isExist(tableName, primaryKey, valueOfKey)) {
                result = db.insert(tableName, null, contentValues);
            } else {

                result = db.update(tableName, contentValues, primaryKey + "=?", new String[]{valueOfKey + ""});
            }


        } catch (Exception e) {
        } finally {

        }
        return result;
    }

    private boolean isExist(String table, String searchField, String value) {
        if (value.equals("") || Integer.valueOf(value) <= 0)
            return false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + table + " where " + searchField + "='" + value + "'", null);
            if (cursor != null && cursor.getCount() > 0)
                return true;
        } catch (Exception e) {

        } finally {
            if (cursor != null)
                cursor.close();

        }


        return false;
    }

    private String getStringValue(Cursor cursor, String key) {

        if (cursor.getColumnIndex(key) == -1)
            return "na";
        else
            return cursor.getString(cursor.getColumnIndex(key));
    }

    private int getIntValue(Cursor cursor, String key) {
        if (cursor.getColumnIndex(key) == -1)
            return 0;
        else
            return cursor.getInt(cursor.getColumnIndex(key));
    }

    public boolean isFav(String table, int id) {

        String sql = "select * from " + table + " where " + KEY_ID + "='" + id + "' and " + KEY_FAV + "='1'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;
    }

    public <T> long addAllData(String tableName, ArrayList<T> dataModelClass, String primaryKey) {
        long result = -1;
        for (Object model : dataModelClass)
            result = addData(tableName, model, primaryKey);
        return result;
    }

    public long addOrderAdmin(MOrder order) {
        String sql = "select * from " + TABLE_ORDER + " where orderId='" + order.getOrderId() + "'";

        ContentValues values = new ContentValues();
        values.put("id", order.getId());
        values.put("orderId", order.getOrderId());
        values.put("userId", order.getUserId());
        values.put("totalQuantity", order.getTotalQuantity());
        values.put("status", order.getStatus());
        values.put("totalAmount", order.getTotalAmount());
        values.put("dateTime", order.getDateTime());
        values.put("userName", order.getUserName());
        values.put("userPic", order.getUserPic());
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                //update
                int r = db.update(TABLE_ORDER, values, "orderId=?", new String[]{order.getOrderId() + ""});
                MyLog.e("ADMIN", r + ":" + order.getUserId() + ":" + order.getOrderId());
                return r;
            } else {
                //insert
                long r = db.insert(TABLE_ORDER, null, values);
                MyLog.e("ADMIN", "up:" + r + ":" + order.getUserId() + ":" + order.getOrderId());
                return r;
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
        }
        return 0;
    }

    public long addOrder(MOrder order) {

        ContentValues values = new ContentValues();
        values.put("orderId", order.getOrderId());
        values.put("userId", order.getUserId());
        values.put("totalQuantity", order.getTotalQuantity());
        values.put("status", order.getStatus());
        values.put("totalAmount", order.getTotalAmount());
        values.put("dateTime", order.getDateTime());
        values.put("userName", order.getUserName());
        values.put("userPic", order.getUserPic());
        try {
            if (order.getOrderId() > 0) {
                //update
                MyLog.e("ORDER_UPDATE", "updated:" + order.getOrderId());
                return db.update(TABLE_ORDER, values, "_id=? AND userId=?", new String[]{order.getOrderId() + "", order.getUserId() + ""});
            } else {
                //insert
                return db.insert(TABLE_ORDER, null, values);
            }
        } catch (Exception e) {

        } finally {
        }
        return 0;
    }

    public void addOrderedItems(MOrderedItem item) {
        String sql = "select * from " + TABLE_ORDERED_ITEM + " where userId='" + item.getUserId() + "' AND orderId='" + item.getOrderId() + "'"
                + " AND id='" + item.getId() + "'";

        ContentValues values = new ContentValues();
        values.put("id", item.getId());
        values.put("userId", item.getUserId());
        values.put("title", item.getTitle());
        values.put("date", item.getDate());
        values.put("thumb", item.getThumb());
        values.put("orderId", item.getOrderId());
        values.put("quantity", item.getQuantity());
        values.put("amount", item.getAmount());
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                //update
                int l = db.update(TABLE_ORDERED_ITEM, values, "userId=? AND orderId=?", new String[]{item.getUserId() + "", item.getOrderId() + ""});
                MyLog.e("DB", "ordered item updated:" + l + item.getOrderId() + ":" + item.getId() + ":" + item.getUserId());
            } else {
                //insert
                long i = db.insert(TABLE_ORDERED_ITEM, null, values);
                MyLog.e("DB", "ordered item updated:" + i + ":" + item.getOrderId() + ":" + item.getId() + ":" + item.getUserId());
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
        }

    }

    public ArrayList<MOrderedItem> getDate() {
        ArrayList<MOrderedItem> assetArrayList = new ArrayList<>();
        Log.e("DB", "S1");
        Gson gson = new Gson();
        MOrderedItem mRecipe;
        String sql = "select * from " + TABLE_ORDERED_ITEM;
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mRecipe = new MOrderedItem();
                mRecipe.setDate(cursor.getString(cursor.getColumnIndex("date")));

                assetArrayList.add(mRecipe);

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }

    public void addNewData(MNew mNew) {
        String sql = "select * from " + TABLE_NEW + " where CategoryId='" + "='" + mNew.getCategoryId()
                + "' AND productId='" + "='" + mNew.getRecipeId() + "'";
        ContentValues values = new ContentValues();
        values.put("productId", mNew.getRecipeId());
        values.put("CategoryId", mNew.getCategoryId());
        values.put("isNew", mNew.getIsNew());
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(TABLE_NEW, values, "CategoryId=? AND productId=?", new String[]{mNew.getCategoryId() + "", mNew.getRecipeId() + ""});
                Log.e("DB", "mlockUpdae:" + update);
            } else {
                long v = db.insert(TABLE_NEW, null, values);
                Log.e("DB", "mlockAdded:" + v);

            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void deleteData(String table, String primaryKey, String value) {
        if (!isExist(table, primaryKey, value))
            return;

        int r = db.delete(table, primaryKey + "=?", new String[]{value});
    }

    public <T> ArrayList<T> getData(String tableName, Object dataModelClass) {

        String sql = "select * from " + tableName;
        Cursor cursor = db.rawQuery(sql, null);
        JSONObject jsonObject = new JSONObject();
        final ArrayList<JSONObject> data = new ArrayList<JSONObject>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                jsonObject = new JSONObject();
                try {
                    Class myClass = dataModelClass.getClass();
                    Field[] fields = myClass.getDeclaredFields();

                    for (Field field : fields) {
                        //for getting access of private field
                        field.setAccessible(true);
                        String name = field.getName();

                        jsonObject.put(name, getStringValue(cursor, name));

                    }
                    data.add(jsonObject);

                } catch (SecurityException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        Gson gson = new Gson();
        ArrayList<T> output = new ArrayList<T>();
        for (int i = 0; i < data.size(); i++) {
            dataModelClass = gson.fromJson(data.get(i).toString(), dataModelClass.getClass());
            output.add((T) dataModelClass);
        }


        return output;
    }

    public MNew getNewData(int categoryId, int recipeId) {
        ArrayList<MNew> unlocks = new ArrayList<>();
        MNew mNew = new MNew();
        String sql = "select * from " + TABLE_NEW + " where CategoryId='" + categoryId + "' AND productId='" + recipeId + "'";
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mNew = new MNew();
                mNew.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mNew.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                mNew.setRecipeId(cursor.getInt(cursor.getColumnIndex("productId")));
                mNew.setIsNew(cursor.getInt(cursor.getColumnIndex("isNew")));

                Log.e("isNew", "is :" + mNew.getIsNew());
                Log.e("unlock", "lock size" + unlocks.size());
                unlocks.add(mNew);

            } while (cursor.moveToNext());

        }
        cursor.close();

        return mNew;
    }


    public <T> ArrayList<T> getList(String sql, Object myInstance) {

        ArrayList<T> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        Gson gson = new Gson();
        if (cursor != null && cursor.moveToFirst()) {
            JSONObject jsonObject = new JSONObject();
            do {

                try {
                    Class myClass = myInstance.getClass();
                    Field[] fields = myClass.getDeclaredFields();
                    for (Field field : fields) {
                        //for getting access of private field
                        field.setAccessible(true);
                        String name = field.getName();
                        if (!name.equalsIgnoreCase("type"))
                            jsonObject.put(name, getStringValue(cursor, name));

                    }
                } catch (SecurityException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myInstance = gson.fromJson(jsonObject.toString(), myInstance.getClass());
                list.add((T) myInstance);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;
    }


    public long updateOrderStatus(int id, int value) {
        ContentValues values = new ContentValues();
        values.put("status", value);
        return db.update(DBManager.TABLE_ORDER, values, "orderId=?", new String[]{id + ""});
    }

    public ArrayList<MFOrder> getOrders() {
        Gson gson = new Gson();
        String sql = "";
        String data = "";
        MFOrder mfOrder = new MFOrder();
        ArrayList<MFOrder> orders = new ArrayList<>();
        int id = 0;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                data = cursor.getString(cursor.getColumnIndex("data"));

                mfOrder = gson.fromJson(data, MFOrder.class);
                mfOrder.setOrderId(id);
                orders.add(mfOrder);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return orders;
    }

    public void close() {
        try {
            if (db.isOpen())
                db.close();
        } catch (Exception e) {
        }

    }

    public void addRecipeData(MRecipe mRecipe, boolean isNew, String tableName) {
        Gson gson = new Gson();
        android.database.Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put("Id", mRecipe.getId());
            values.put("cartStatus", mRecipe.getCartStatus());
            values.put("DiscountRate", mRecipe.getDiscountRate());
            values.put("Description", mRecipe.getDescription());
            values.put("PPhoto", mRecipe.getPPhoto());
            values.put("Process", mRecipe.getProcess());
            values.put("Photo", mRecipe.getPhoto());
            values.put("CategoryId", mRecipe.getCategoryId());
            values.put("selectedPos", mRecipe.getSelectedPos());
            values.put("CategoryTitle", mRecipe.getCategoryTitle());
            values.put("Thumb", mRecipe.getThumb());
            values.put("Ingredients", mRecipe.getIngredients());
            values.put("Title", mRecipe.getTitle());
            values.put("price", mRecipe.getPrice());
            values.put("addCart", mRecipe.getAddCart());
            values.put("fav", mRecipe.getFav());
            values.put("TypeOne", mRecipe.getTypeOne());
            values.put("TypeTwo", mRecipe.getTypeTwo());
            values.put("TypeThree", mRecipe.getTypeThree());
            values.put("TypeFour", mRecipe.getTypeFour());
            values.put("TypeFive", mRecipe.getTypeFive());
            if (!isNew)
                values.put("isNew", mRecipe.getIsNew());
            values.put("Video", mRecipe.getVideo());
            values.put("view", mRecipe.getView());
            values.put("status", mRecipe.getStatus());
            values.put("SearchTag", mRecipe.getSearchTag());
            values.put("quantity", mRecipe.getQuantity());
            values.put("recipeDelete", mRecipe.getRecipeDelete());
            values.put("selectedColor", mRecipe.getSelectedColor());
            values.put("selectedSize", mRecipe.getSelectedSize());

            String colors = gson.toJson(mRecipe.getColors());
            values.put("colors", colors);

            String sizes = gson.toJson(mRecipe.getSize());
            values.put("sizes", sizes);

            String sql = "select * from " + tableName + " where Id='" + mRecipe.getId() + "'";
            cursor = db.rawQuery(sql, null);
            Log.e("cu", "has" + cursor);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(tableName, values, "Id=?", new String[]{mRecipe.getId() + ""});
                Log.e("sublevel", "sub level update : " + update);
            } else {
                long v = db.insert(tableName, null, values);
                Log.e("sublevel", "sub level insert : " + v);

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }

    public ArrayList<MRecipe> getRecipeData(int id) {
        ArrayList<MRecipe> assetArrayList = new ArrayList<>();
        Log.e("DB", "S1");
        Gson gson = new Gson();
        MRecipe mRecipe;
        String sql = "select * from " + TABLE_RECEIPE + " where CategoryId='" + id + "' OR TypeOne='" + id + "' " +
                " OR TypeTwo='" + id + "' OR TypeThree='" + id + "' OR TypeFour='" + id + "' OR TypeFive='" + id + "' order by Id asc";
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mRecipe = new MRecipe();
                mRecipe.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                mRecipe.setIsNew(cursor.getInt(cursor.getColumnIndex("isNew")));
                mRecipe.setAddCart(cursor.getInt(cursor.getColumnIndex("addCart")));
                mRecipe.setCartStatus(cursor.getInt(cursor.getColumnIndex("cartStatus")));
                mRecipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                mRecipe.setSelectedPos(cursor.getInt(cursor.getColumnIndex("selectedPos")));
                mRecipe.setCategoryTitle(cursor.getString(cursor.getColumnIndex("CategoryTitle")));
                mRecipe.setDiscountRate(cursor.getInt(cursor.getColumnIndex("DiscountRate")));
                mRecipe.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                mRecipe.setFav(cursor.getInt(cursor.getColumnIndex("fav")));
                mRecipe.setIngredients(cursor.getString(cursor.getColumnIndex("Ingredients")));
                mRecipe.setPhoto(cursor.getString(cursor.getColumnIndex("Photo")));
                mRecipe.setPPhoto(cursor.getString(cursor.getColumnIndex("PPhoto")));
                mRecipe.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                mRecipe.setProcess(cursor.getString(cursor.getColumnIndex("Process")));
                mRecipe.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                mRecipe.setRecipeDelete(cursor.getInt(cursor.getColumnIndex("recipeDelete")));
                mRecipe.setSearchTag(cursor.getString(cursor.getColumnIndex("SearchTag")));
                mRecipe.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                mRecipe.setThumb(cursor.getString(cursor.getColumnIndex("Thumb")));
                mRecipe.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                mRecipe.setTypeFive(cursor.getInt(cursor.getColumnIndex("TypeFive")));
                mRecipe.setTypeFour(cursor.getInt(cursor.getColumnIndex("TypeFour")));
                mRecipe.setTypeOne(cursor.getInt(cursor.getColumnIndex("TypeOne")));
                mRecipe.setTypeThree(cursor.getInt(cursor.getColumnIndex("TypeThree")));
                mRecipe.setTypeTwo(cursor.getInt(cursor.getColumnIndex("TypeTwo")));
                mRecipe.setView(cursor.getInt(cursor.getColumnIndex("view")));
                mRecipe.setVideo(cursor.getString(cursor.getColumnIndex("Video")));
                mRecipe.setSelectedColor(cursor.getString(cursor.getColumnIndex("selectedColor")));
                mRecipe.setSelectedSize(cursor.getString(cursor.getColumnIndex("selectedSize")));

                String colors = cursor.getString(cursor.getColumnIndex("colors"));
                String sizes = cursor.getString(cursor.getColumnIndex("sizes"));

                MColor[] coloArray = gson.fromJson(colors, MColor[].class);
                mRecipe.setColors(new ArrayList<MColor>(Arrays.asList(coloArray)));

                MSize[] sizeArray = gson.fromJson(sizes, MSize[].class);
                mRecipe.setSize(new ArrayList<MSize>(Arrays.asList(sizeArray)));


                assetArrayList.add(mRecipe);
                Log.e("DB", "S3 :" + mRecipe.getIsNew());

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }


    public ArrayList<MRecipe> getFavData() {
        ArrayList<MRecipe> assetArrayList = new ArrayList<>();
        Log.e("DB", "S1");
        Gson gson = new Gson();
        MRecipe mRecipe;
        String sql = "select * from " + TABLE_RECEIPE + " where " + KEY_FAV + "='1'";
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mRecipe = new MRecipe();
                mRecipe.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                mRecipe.setIsNew(cursor.getInt(cursor.getColumnIndex("isNew")));
                mRecipe.setAddCart(cursor.getInt(cursor.getColumnIndex("addCart")));
                mRecipe.setCartStatus(cursor.getInt(cursor.getColumnIndex("cartStatus")));
                mRecipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                mRecipe.setSelectedPos(cursor.getInt(cursor.getColumnIndex("selectedPos")));
                mRecipe.setCategoryTitle(cursor.getString(cursor.getColumnIndex("CategoryTitle")));
                mRecipe.setDiscountRate(cursor.getInt(cursor.getColumnIndex("DiscountRate")));
                mRecipe.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                mRecipe.setFav(cursor.getInt(cursor.getColumnIndex("fav")));
                mRecipe.setIngredients(cursor.getString(cursor.getColumnIndex("Ingredients")));
                mRecipe.setPhoto(cursor.getString(cursor.getColumnIndex("Photo")));
                mRecipe.setPPhoto(cursor.getString(cursor.getColumnIndex("PPhoto")));
                mRecipe.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                mRecipe.setProcess(cursor.getString(cursor.getColumnIndex("Process")));
                mRecipe.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                mRecipe.setRecipeDelete(cursor.getInt(cursor.getColumnIndex("recipeDelete")));
                mRecipe.setSearchTag(cursor.getString(cursor.getColumnIndex("SearchTag")));
                mRecipe.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                mRecipe.setThumb(cursor.getString(cursor.getColumnIndex("Thumb")));
                mRecipe.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                mRecipe.setTypeFive(cursor.getInt(cursor.getColumnIndex("TypeFive")));
                mRecipe.setTypeFour(cursor.getInt(cursor.getColumnIndex("TypeFour")));
                mRecipe.setTypeOne(cursor.getInt(cursor.getColumnIndex("TypeOne")));
                mRecipe.setTypeThree(cursor.getInt(cursor.getColumnIndex("TypeThree")));
                mRecipe.setTypeTwo(cursor.getInt(cursor.getColumnIndex("TypeTwo")));
                mRecipe.setView(cursor.getInt(cursor.getColumnIndex("view")));
                mRecipe.setVideo(cursor.getString(cursor.getColumnIndex("Video")));
                mRecipe.setSelectedColor(cursor.getString(cursor.getColumnIndex("selectedColor")));
                mRecipe.setSelectedSize(cursor.getString(cursor.getColumnIndex("selectedSize")));

                String colors = cursor.getString(cursor.getColumnIndex("colors"));
                String sizes = cursor.getString(cursor.getColumnIndex("sizes"));

                MColor[] coloArray = gson.fromJson(colors, MColor[].class);
                mRecipe.setColors(new ArrayList<MColor>(Arrays.asList(coloArray)));

                MSize[] sizeArray = gson.fromJson(sizes, MSize[].class);
                mRecipe.setSize(new ArrayList<MSize>(Arrays.asList(sizeArray)));


                assetArrayList.add(mRecipe);
                Log.e("DB", "S3 :" + mRecipe.getIsNew());

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }

    public ArrayList<MRecipe> getRecipe(String tableName) {
        ArrayList<MRecipe> assetArrayList = new ArrayList<>();
        Log.e("DB", "S1");
        Gson gson = new Gson();
        MRecipe mRecipe;
        String sql = "select * from " + tableName;
        android.database.Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mRecipe = new MRecipe();
                mRecipe.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                mRecipe.setIsNew(cursor.getInt(cursor.getColumnIndex("isNew")));
                mRecipe.setAddCart(cursor.getInt(cursor.getColumnIndex("addCart")));
                mRecipe.setCartStatus(cursor.getInt(cursor.getColumnIndex("cartStatus")));
                mRecipe.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryId")));
                mRecipe.setSelectedPos(cursor.getInt(cursor.getColumnIndex("selectedPos")));
                mRecipe.setCategoryTitle(cursor.getString(cursor.getColumnIndex("CategoryTitle")));
                mRecipe.setDiscountRate(cursor.getInt(cursor.getColumnIndex("DiscountRate")));
                mRecipe.setFav(cursor.getInt(cursor.getColumnIndex("fav")));
                mRecipe.setIngredients(cursor.getString(cursor.getColumnIndex("Ingredients")));
                mRecipe.setPhoto(cursor.getString(cursor.getColumnIndex("Photo")));
                mRecipe.setPPhoto(cursor.getString(cursor.getColumnIndex("PPhoto")));
                mRecipe.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                mRecipe.setProcess(cursor.getString(cursor.getColumnIndex("Process")));
                mRecipe.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                mRecipe.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                mRecipe.setRecipeDelete(cursor.getInt(cursor.getColumnIndex("recipeDelete")));
                mRecipe.setSearchTag(cursor.getString(cursor.getColumnIndex("SearchTag")));
                mRecipe.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                mRecipe.setThumb(cursor.getString(cursor.getColumnIndex("Thumb")));
                mRecipe.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                mRecipe.setTypeFive(cursor.getInt(cursor.getColumnIndex("TypeFive")));
                mRecipe.setTypeFour(cursor.getInt(cursor.getColumnIndex("TypeFour")));
                mRecipe.setTypeOne(cursor.getInt(cursor.getColumnIndex("TypeOne")));
                mRecipe.setTypeThree(cursor.getInt(cursor.getColumnIndex("TypeThree")));
                mRecipe.setTypeTwo(cursor.getInt(cursor.getColumnIndex("TypeTwo")));
                mRecipe.setView(cursor.getInt(cursor.getColumnIndex("view")));
                mRecipe.setVideo(cursor.getString(cursor.getColumnIndex("Video")));
                mRecipe.setSelectedColor(cursor.getString(cursor.getColumnIndex("selectedColor")));
                mRecipe.setSelectedSize(cursor.getString(cursor.getColumnIndex("selectedSize")));

                String colors = cursor.getString(cursor.getColumnIndex("colors"));
                String sizes = cursor.getString(cursor.getColumnIndex("sizes"));

                MColor[] coloArray = gson.fromJson(colors, MColor[].class);
                mRecipe.setColors(new ArrayList<MColor>(Arrays.asList(coloArray)));

                MSize[] sizeArray = gson.fromJson(sizes, MSize[].class);
                mRecipe.setSize(new ArrayList<MSize>(Arrays.asList(sizeArray)));


                assetArrayList.add(mRecipe);
                Log.e("DB", "S3 :" + mRecipe.getIsNew());

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }
}
