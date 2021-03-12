package com.example.tryit.models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLDraftsDbHelper extends SQLiteOpenHelper{

    public static final String DRAFTS_TABLE = "DRAFTS_TABLE";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_REC_NAME = "RECIPE_NAME";
    public static final String INSTANCE_ID = "ID";
    public static final String COLUMN_REC_ING = "COLUMN_REC_ING";
    public static final String COLUMN_REC_DIR = "COLUMN_REC_DIR";

    // column for user will be "Anonymous" if/when we implement that
    public SQLDraftsDbHelper(@Nullable Context context) {
        super(context, "drafts.db", null, 1);
    }

    // called the first time db is accessed. code here to create the db.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + DRAFTS_TABLE + " (" + INSTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_REC_NAME + " TEXT, " + COLUMN_REC_ING + " TEXT, " + COLUMN_REC_DIR + " TEXT)";

        db.execSQL(createTableStatement);
    }

    // called when db version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Recipe recipe){

        // this comes from the extend and gets our db
        SQLiteDatabase db = this.getWritableDatabase();

        // associative array or map for column to the data
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, "Anonymous");
        cv.put(COLUMN_REC_NAME, recipe.getName());
        cv.put(COLUMN_REC_ING, recipe.getIngredients().toString());
        cv.put(COLUMN_REC_DIR, recipe.getSteps());

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
                String recUser = cursor.getString(0);
                String recName = cursor.getString(1);
                String recIng = cursor.getString(2);
                String recDir = cursor.getString(3);

                Recipe recipe = new Recipe(recName, recIng, recDir);
                returnList.add(recipe);

            }while(cursor.moveToNext());
        }else{
            // nothing in db, fail. dont add anything to return list.
        }

        // close cursor and db when done
        cursor.close();
        db.close();
        return returnList;
    }
}
