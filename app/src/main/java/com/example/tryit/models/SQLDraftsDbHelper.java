package com.example.tryit.models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tryit.MainActivity;

import java.util.ArrayList;
import java.util.List;

//IS ACTUALLY A GENERAL DB HELPER
public class SQLDraftsDbHelper extends SQLiteOpenHelper{

    //its only letting me add when i delete so the uniques value isnt incrementing proprerly

    public static final String DRAFTS_TABLE = "DRAFTS_TABLE";
    public static final String COLUMN_REC_NAME = "RECIPE_NAME";
    public static final String COLUMN_INSTANCE_ID = "ID";
    public static final String COLUMN_REC_ING = "COLUMN_REC_ING";
    public static final String COLUMN_REC_DIR = "COLUMN_REC_DIR";

    //for shopping list table
    public static final String SHOPPINGLIST_TABLE = "SHOPPINGLIST_TABLE";
    public static final String COLUMN_SL_ING = "COLUMN_SL_ING";

    String createTableStatement = "CREATE TABLE " + DRAFTS_TABLE + " (" + COLUMN_INSTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REC_NAME + " TEXT, " + COLUMN_REC_ING + " TEXT, " + COLUMN_REC_DIR + " TEXT)";
    String createTableStatement_sl = "CREATE TABLE " + SHOPPINGLIST_TABLE + " (" + COLUMN_INSTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SL_ING + " TEXT)";


    // column for user will be "Anonymous" if/when we implement that
    public SQLDraftsDbHelper(@Nullable Context context) {
        super(context, "local.db", null, 1);
    }

    // called the first time db is accessed. code here to create the db.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement_sl);
    }

    // called when db version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("sl", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DRAFTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPINGLIST_TABLE);

        onCreate(db);
    }

    /////////////////////////////////////////////DRAFTS FUNCTIONALITY/////////////////////////////////////////////
    public boolean addOne(Recipe recipe){

        // this comes from the extend and gets our db
        SQLiteDatabase db = this.getWritableDatabase();

        // associative array or map for column to the data
        ContentValues cv = new ContentValues();
        //here we can add the user name the recipes draft belongs to
        cv.put(COLUMN_REC_NAME, recipe.getName());
        cv.put(COLUMN_REC_ING, recipe.getIngredientsString());
        cv.put(COLUMN_REC_DIR, recipe.getSteps());
//        cv.put(COLUMN_INSTANCE_ID, recipe.getId());

        Log.d("help", "addOne: recipe.getName(), ing -- " + recipe.getName() + " " + recipe.getIngredientsString() + " " + recipe.getId());

        long insert = db.insert(DRAFTS_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public List<Recipe> getAll(){
        List<Recipe> returnList = new ArrayList<>();

        // get data from db
        String queryString = "SELECT * FROM " + DRAFTS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop thru cursor (result set) and create new recipe object and put into returnlist
            // here is where we can parse thru ingredients and put it into proper arraylist
            do{
                int recipeID = cursor.getInt(0);
                String recName = cursor.getString(1);
                String recIng = cursor.getString(2);
                String recDir = cursor.getString(3);

                Recipe reci = new Recipe(recName, recIng, recDir, recipeID);
                returnList.add(reci);
                Log.d("new", "getAll: recName() -- " + recName + " " + recIng + " " + recDir + " " + " " + recipeID);
                Log.d("help", "reci -- " + reci.toString());
//                Log.d("help2", "getAll: recName() -- " + recName + " " + recIng + " " + recDir + " " + test1 + " " + recipeID);

            }while(cursor.moveToNext());
        }else{
            // nothing in db, fail. dont add anything to return list.
        }

        // close cursor and db when done
        cursor.close();
        db.close();

        Log.d("help", "getAll: returnList -- " + returnList);
        return returnList;


    }

    public boolean deleteOne(Recipe recipe){
        //find recipe and return true after deleted
        //if not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + DRAFTS_TABLE + " WHERE " + COLUMN_INSTANCE_ID + " = " + recipe.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            Log.d("delete", "deleted");
            return true;
        } else {
            Log.d("delete", "not deleted" + recipe.getId());
            return false;
        }
    }
    /////////////////////////////////////////////SHOPPING LIST FUNCTIONALITY/////////////////////////////////////////////
    public boolean sl_addOne(Ingredient ingredient){
        // this comes from the extend and gets our db
        SQLiteDatabase db = this.getWritableDatabase();

        // associative array or map for column to the data
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SL_ING, ingredient.getName());

        Log.d("sl", "addOne: ingredient.getName() == " + ingredient.getName());

        long insert = db.insert(SHOPPINGLIST_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else {
            return true;
        }
    }

    public List<Ingredient> sl_getAll(){
        List<Ingredient> returnList = new ArrayList<>();

        // get data from db
        String queryString = "SELECT * FROM " + SHOPPINGLIST_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop thru cursor (result set) and create new recipe object and put into returnlist
            // here is where we can parse thru ingredients and put it into proper arraylist
            do{
                int ingID = cursor.getInt(0);
                String ing = cursor.getString(1);

                Ingredient ingredient = new Ingredient(ing, ingID);
                returnList.add(ingredient);
//                Log.d("sl", "getAll: ecName() -r- " + recName + " " + recIng + " " + recDir + " " + " " + recipeID);
            }while(cursor.moveToNext());
        }else{
            // nothing in db, fail. dont add anything to return list.
        }
        // close cursor and db when done
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean sl_deleteOne(Ingredient ing){
        //find recipe and return true after deleted
        //if not found, return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SHOPPINGLIST_TABLE + " WHERE " + COLUMN_INSTANCE_ID + " = " + ing.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            Log.d("delete", "deleted");
            return true;
        } else {
            Log.d("delete", "not deleted" + ing);
            return false;
        }
    }


}
