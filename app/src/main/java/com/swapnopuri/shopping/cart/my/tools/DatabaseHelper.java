package com.swapnopuri.shopping.cart.my.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.swapnopuri.shopping.cart.my.model.MCategory;
import com.swapnopuri.shopping.cart.my.model.MNew;
import com.swapnopuri.shopping.cart.my.model.MRecipe;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dev on 10/2/2017.
 */

public class DatabaseHelper {
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_LEVEL_TABLE = "level_db";
    private static final String DATABASE_LOCK_TABLE = "lock_tb";
    private static final String DATABASE_SUB_LEVEL_TABLE = "sub";
    private static final String KEY_C_ID = "c_id";
    private static final String KEY_C_T_RECI = "c_t_r";
    private static final String KEY_C_TYPE = "c_t";
    private static final String KEY_C_UPDATE_AVAILABLE = "c_u_a";
    private static final String KEY_C_DELETE = "c_d";
    private static final String KEY_C_TITLE = "c_tt";
    private static final String KEY_C_DETAILS = "c_dt";
    private static final String KEY_C_PHOTO = "c_p";
    private static final String KEY_C_THUMB = "c_th";
    private static final String KEY_RECIPE_ID = "r_lid";
    private static final String KEY_TYPE_ONE = "r_o";
    private static final String KEY_TYPE_TWO = "r_t";
    private static final String KEY_TYPE_THREE = "r_th";
    private static final String KEY_TYPE_FOUR = "r_f";
    private static final String KEY_TYPE_FIVE = "r_fve";
    private static final String KEY_FAV = "r_fv";
    private static final String KEY_VIEW = "r_v";
    private static final String KEY_R_TITLE = "rt";
    private static final String KEY_SERCH_TAG = "st";
    private static final String KEY_VIDEO = "vd";
    private static final String KEY_INGREDIENTS = "ig";
    private static final String KEY_PPHOTO = "pp";
    private static final String KEY_PROCESS = "pc";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DISCOUNT = "dr";
    private static final String KEY_CART_STATUS = "cs";
    private static final String KEY_ADD_CARD = "ac";
    private static final String KEY_QUANTITY = "qt";
    private static final String KEY_STATUS = "status";
    private static final String KEY_LOCK_ID = "loid";
    private static final String KEY_IS_NEW = "is_new";

    private static final String DATABASE_CREATE_LEVEL_TABLE = "create table if not exists "
            + DATABASE_LEVEL_TABLE + "("
            + KEY_C_ID + " integer primary key, "
            + KEY_C_TITLE + " text, "
            + KEY_C_DETAILS + " text, "
            + KEY_C_PHOTO + " text, "
            + KEY_C_DELETE + " integer, "
            + KEY_C_UPDATE_AVAILABLE + " integer, "
            + KEY_C_T_RECI + " integer, "
            + KEY_C_TYPE + " integer, "
            + KEY_C_THUMB + " text)";

    private static final String DATABASE_CREATE_SUB_LEVEL_TABLE = "create table if not exists "
            + DATABASE_SUB_LEVEL_TABLE + "("
            + KEY_RECIPE_ID + " integer primary key, "
            + KEY_R_TITLE + " text, "
            + KEY_TYPE_ONE + " integer, "
            + KEY_TYPE_TWO + " integer, "
            + KEY_TYPE_THREE + " integer, "
            + KEY_TYPE_FOUR + " integer, "
            + KEY_IS_NEW + " integer, "
            + KEY_TYPE_FIVE + " integer, "
            + KEY_PRICE + " float, "
            + KEY_FAV + " integer, "
            + KEY_C_ID + " integer, "
            + KEY_C_DELETE + " integer, "
            + KEY_QUANTITY + " integer, "
            + KEY_VIEW + " integer, "
            + KEY_DISCOUNT + " integer, "
            + KEY_STATUS + " integer, "
            + KEY_ADD_CARD + " integer, "
            + KEY_CART_STATUS + " integer, "
            + KEY_SERCH_TAG + " text, "
            + KEY_VIDEO + " text, "
            + KEY_C_TITLE + " text, "
            + KEY_C_THUMB + " text, "
            + KEY_C_PHOTO + " text, "
            + KEY_INGREDIENTS + " text, "
            + KEY_PROCESS + " text, "
            + KEY_PPHOTO + " text)";

    private static final String DATABASE_CREATE_LOCK_TABLE = "create table if not exists "
            + DATABASE_LOCK_TABLE + "("
            + KEY_LOCK_ID + " integer primary key autoincrement, "
            + KEY_C_ID + " integer, "
            + KEY_RECIPE_ID + " integer, "
            + KEY_IS_NEW + " integer)";

    private static DatabaseHelper instance;
    private static SQLiteDatabase db;
    private final String TAG = getClass().getSimpleName();
    private final String PASS = Utils.databasePassKey("nayan5565@gmail.com", "As us");
    private Context context;

    public DatabaseHelper(Context context) {

        this.context = context;
        instance = this;
        openDB(context);
        onCreate();
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private void openDB(Context context) {
        SQLiteDatabase.loadLibs(context);
        File databaseFile = context.getDatabasePath(DATABASE_NAME);
        if (!databaseFile.exists()) {
            databaseFile.mkdirs();
            databaseFile.delete();
        }
        if (db == null)
            db = SQLiteDatabase.openOrCreateDatabase(databaseFile, PASS, null);
    }


    public void onCreate() {
        db.execSQL(DATABASE_CREATE_LEVEL_TABLE);
        db.execSQL(DATABASE_CREATE_SUB_LEVEL_TABLE);
        db.execSQL(DATABASE_CREATE_LOCK_TABLE);


    }

    public void addLevelFromJson(MCategory mCategory) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_C_DELETE, mCategory.getCategoryDelete());
            values.put(KEY_C_DETAILS, mCategory.getCategoryDetails());
            values.put(KEY_C_ID, mCategory.getCategoryId());
            values.put(KEY_C_PHOTO, mCategory.getCategoryPhoto());
            values.put(KEY_C_THUMB, mCategory.getCategoryThumb());
            values.put(KEY_C_TITLE, mCategory.getCategoryTitle());
            values.put(KEY_C_TYPE, mCategory.getCategoryType());
            values.put(KEY_C_UPDATE_AVAILABLE, mCategory.getCategoryUpdateAvailable());
            values.put(KEY_C_T_RECI, mCategory.getCategoryTotalRecipe());

            String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_C_ID + "='" + mCategory.getCategoryId() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(DATABASE_LEVEL_TABLE, values, KEY_C_ID + "=?", new String[]{mCategory.getCategoryId() + ""});
                Log.e("level", "update : " + update);
            } else {
                long v = db.insert(DATABASE_LEVEL_TABLE, null, values);
                Log.e("level", "insert : " + mCategory.getCategoryId());

            }


        } catch (Exception e) {

        }
        if (cursor != null)
            cursor.close();
    }

    public void addSubFromJsom(MRecipe mRecipe) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_RECIPE_ID, mRecipe.getId());
            values.put(KEY_CART_STATUS, mRecipe.getCartStatus());
            values.put(KEY_DISCOUNT, mRecipe.getDiscountRate());
            values.put(KEY_PPHOTO, mRecipe.getPPhoto());
            values.put(KEY_PROCESS, mRecipe.getProcess());
            values.put(KEY_C_PHOTO, mRecipe.getPhoto());
            values.put(KEY_C_ID, mRecipe.getCategoryId());
            values.put(KEY_C_TITLE, mRecipe.getCategoryTitle());
            values.put(KEY_C_THUMB, mRecipe.getThumb());
            values.put(KEY_INGREDIENTS, mRecipe.getIngredients());
            values.put(KEY_R_TITLE, mRecipe.getTitle());
            values.put(KEY_PRICE, mRecipe.getPrice());
            values.put(KEY_ADD_CARD, mRecipe.getAddCart());
            values.put(KEY_FAV, mRecipe.getFav());
            values.put(KEY_TYPE_ONE, mRecipe.getTypeOne());
            values.put(KEY_TYPE_TWO, mRecipe.getTypeTwo());
            values.put(KEY_TYPE_THREE, mRecipe.getTypeThree());
            values.put(KEY_TYPE_FOUR, mRecipe.getTypeFour());
            values.put(KEY_TYPE_FIVE, mRecipe.getTypeFive());
            values.put(KEY_IS_NEW, mRecipe.getIsNew());
            values.put(KEY_VIDEO, mRecipe.getVideo());
            values.put(KEY_VIEW, mRecipe.getView());
            values.put(KEY_STATUS, mRecipe.getStatus());
            values.put(KEY_SERCH_TAG, mRecipe.getSearchTag());
            values.put(KEY_QUANTITY, mRecipe.getQuantity());
            values.put(KEY_C_DELETE, mRecipe.getRecipeDelete());

            String sql = "select * from " + DATABASE_SUB_LEVEL_TABLE + " where " + KEY_RECIPE_ID + "='" + mRecipe.getId() + "'";
            cursor = db.rawQuery(sql, null);
            Log.e("cu", "has" + cursor);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(DATABASE_SUB_LEVEL_TABLE, values, KEY_RECIPE_ID + "=?", new String[]{mRecipe.getId() + ""});
                Log.e("sublevel", "sub level update : " + update);
            } else {
                long v = db.insert(DATABASE_SUB_LEVEL_TABLE, null, values);
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
        MRecipe mSubLevel;
        String sql = "select a.r_lid,a.ac,a.cs,a.c_id,a.c_tt,a.dr,a.r_v,a.vd,a.r_t,a.r_fv,a.ig,a.r_th,a.r_o,a.c_p,a.pp,a.r_f,a.price,a.pc,a.qt,a.r_fve,a.c_d,a.rt,a.st,a.c_th,a.status,b.is_new from sub a left join lock_tb b on a.c_id=b.c_id AND a.r_lid=b.r_lid where a." + KEY_C_ID + "='" + id + "'";
//                " from " + DATABASE_SUB_LEVEL_TABLE + " a where " + KEY_PARENT_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("DB", "S2 :" + cursor.getCount());
            do {
                mSubLevel = new MRecipe();
                mSubLevel.setId(cursor.getInt(cursor.getColumnIndex(KEY_RECIPE_ID)));
                mSubLevel.setIsNew(cursor.getInt(cursor.getColumnIndex(KEY_IS_NEW)));
                mSubLevel.setAddCart(cursor.getInt(cursor.getColumnIndex(KEY_ADD_CARD)));
                mSubLevel.setCartStatus(cursor.getInt(cursor.getColumnIndex(KEY_CART_STATUS)));
                mSubLevel.setCategoryId(cursor.getInt(cursor.getColumnIndex(KEY_C_ID)));
                mSubLevel.setCategoryTitle(cursor.getString(cursor.getColumnIndex(KEY_C_TITLE)));
                mSubLevel.setDiscountRate(cursor.getInt(cursor.getColumnIndex(KEY_DISCOUNT)));
                mSubLevel.setFav(cursor.getInt(cursor.getColumnIndex(KEY_FAV)));
                mSubLevel.setIngredients(cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS)));
                mSubLevel.setPhoto(cursor.getString(cursor.getColumnIndex(KEY_C_PHOTO)));
                mSubLevel.setPPhoto(cursor.getString(cursor.getColumnIndex(KEY_PPHOTO)));
                mSubLevel.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRICE)));
                mSubLevel.setProcess(cursor.getString(cursor.getColumnIndex(KEY_PROCESS)));
                mSubLevel.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)));
                mSubLevel.setRecipeDelete(cursor.getInt(cursor.getColumnIndex(KEY_C_DELETE)));
                mSubLevel.setSearchTag(cursor.getString(cursor.getColumnIndex(KEY_SERCH_TAG)));
                mSubLevel.setStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));
                mSubLevel.setThumb(cursor.getString(cursor.getColumnIndex(KEY_C_THUMB)));
                mSubLevel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_R_TITLE)));
                mSubLevel.setTypeFive(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_FIVE)));
                mSubLevel.setTypeFour(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_FOUR)));
                mSubLevel.setTypeOne(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_ONE)));
                mSubLevel.setTypeThree(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_THREE)));
                mSubLevel.setTypeTwo(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_TWO)));
                mSubLevel.setView(cursor.getInt(cursor.getColumnIndex(KEY_VIEW)));
                mSubLevel.setVideo(cursor.getString(cursor.getColumnIndex(KEY_VIDEO)));
                assetArrayList.add(mSubLevel);
                Log.e("DB", "S3 :" + mSubLevel.getIsNew());

            } while (cursor.moveToNext());

        }
        cursor.close();


        return assetArrayList;
    }


    public void addLockData(MNew mNew) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_RECIPE_ID, mNew.getRecipeId());
            values.put(KEY_C_ID, mNew.getCategoryId());
            values.put(KEY_IS_NEW, mNew.getIsNew());


            String sql = "select * from " + DATABASE_LOCK_TABLE + " where " + KEY_C_ID + "='" + mNew.getCategoryId()
                    + "' AND " + KEY_RECIPE_ID + "='" + mNew.getRecipeId() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                int update = db.update(DATABASE_LOCK_TABLE, values, KEY_C_ID + "=? AND " + KEY_RECIPE_ID + "=?", new String[]{mNew.getCategoryId() + "", mNew.getRecipeId() + ""});
                Log.e("DB", "mlockUpdae:" + update);
            } else {
                long v = db.insert(DATABASE_LOCK_TABLE, null, values);
                Log.e("DB", "mlockAdded:" + v);

            }


        } catch (Exception e) {
            Log.e("ERR", "mlock:" + e.toString());
        }

        if (cursor != null)
            cursor.close();
    }


    public ArrayList<MCategory> getCategoryData(int id) {
        ArrayList<MCategory> levelArrayList = new ArrayList<>();

        MCategory mLevel;
        String sql = "select * from " + DATABASE_LEVEL_TABLE + " where " + KEY_C_ID + "='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        Log.e("cursor", "count :" + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.e("do", "start");
                mLevel = new MCategory();
                mLevel.setCategoryId(cursor.getInt(cursor.getColumnIndex(KEY_C_ID)));
                mLevel.setCategoryDelete(cursor.getInt(cursor.getColumnIndex(KEY_C_DELETE)));
                mLevel.setCategoryDetails(cursor.getString(cursor.getColumnIndex(KEY_C_DETAILS)));
                mLevel.setCategoryPhoto(cursor.getString(cursor.getColumnIndex(KEY_C_PHOTO)));
                mLevel.setCategoryThumb(cursor.getString(cursor.getColumnIndex(KEY_C_THUMB)));
                mLevel.setCategoryTitle(cursor.getString(cursor.getColumnIndex(KEY_C_TITLE)));
                mLevel.setCategoryTotalRecipe(cursor.getInt(cursor.getColumnIndex(KEY_C_T_RECI)));
                mLevel.setCategoryType(cursor.getInt(cursor.getColumnIndex(KEY_C_TYPE)));
                mLevel.setCategoryUpdateAvailable(cursor.getInt(cursor.getColumnIndex(KEY_C_UPDATE_AVAILABLE)));
                levelArrayList.add(mLevel);
                Log.e("do", "end");
            } while (cursor.moveToNext());


        }
        cursor.close();


        return levelArrayList;
    }

//    public ArrayList<MLevel> getLevelAllData() {
//        ArrayList<MLevel> levelArrayList = new ArrayList<>();
//
//        MLevel mLevel;
//        String sql = "select * from " + DATABASE_LEVEL_TABLE;
//        Cursor cursor = db.rawQuery(sql, null);
//        Log.e("cursor", "count :" + cursor.getCount());
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Log.e("do", "start");
//                mLevel = new MLevel();
//                mLevel.setLid(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_ID)));
//                mLevel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
////                mLevel.setUpdate_date(cursor.getString(cursor.getColumnIndex(KEY_UPDATE_DATE)));
//                mLevel.setTotal_slevel(cursor.getString(cursor.getColumnIndex(KEY_TOTAL_S_LEVEL)));
////                mLevel.setHowto(cursor.getString(cursor.getColumnIndex(KEY_HOW_TO)));
////                mLevel.setLevelWinCount(cursor.getInt(cursor.getColumnIndex(KEY_LEVEL_WIN_COUNT)));
//                levelArrayList.add(mLevel);
//                Log.e("do", "end");
//            } while (cursor.moveToNext());
//
//
//        }
//        cursor.close();
//
//
//        return levelArrayList;
//    }

    public MNew getLocalData(int levelId, int subLevelId) {
        ArrayList<MNew> unlocks = new ArrayList<>();
        MNew mNew = new MNew();
        String sql = "select * from " + DATABASE_LOCK_TABLE + " where " + KEY_C_ID + "='" + levelId + "' "
                + " AND " + KEY_RECIPE_ID + "='" + subLevelId + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mNew = new MNew();
                mNew.setId(cursor.getInt(cursor.getColumnIndex(KEY_LOCK_ID)));
                mNew.setCategoryId(cursor.getInt(cursor.getColumnIndex(KEY_C_ID)));
                mNew.setRecipeId(cursor.getInt(cursor.getColumnIndex(KEY_RECIPE_ID)));
                mNew.setIsNew(cursor.getInt(cursor.getColumnIndex(KEY_IS_NEW)));

                Log.e("isNew", "is :" + mNew.getIsNew());
                Log.e("unlock", "lock size" + unlocks.size());
                unlocks.add(mNew);

            } while (cursor.moveToNext());

        }
        cursor.close();

        return mNew;
    }


//    public int getLockTotalPointData(int id) {
//        String sql = "select sum(" + KEY_TOTAL_POINT + ") as q  from " + DATABASE_LOCK_TABLE + " where " + KEY_LEVEL_ID + "='" + id + "'";
//        Cursor cursor = db.rawQuery(sql, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            Log.e("DB", "data have");
//
////                mLock.setUnlockNextLevel(cursor.getInt(cursor.getColumnIndex(KEY_UNLOCK)));
////                mLock.setBestPoint(cursor.getInt(cursor.getColumnIndex(KEY_POINT)));
//            return cursor.getInt(cursor.getColumnIndex("q"));
//
//        } else {
//            Log.e("DB", "data null");
//        }
//        cursor.close();
//
//
//        return 0;
//    }



    private void close() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
    }


}
