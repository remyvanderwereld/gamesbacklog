package com.example.test.gamesbacklog.activity.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.gamesbacklog.activity.model.Game;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Remy on 26-9-2017.
 */

public class DataSource {

    //Local variables and constants
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] GAMES_ALL_COLUMNS = {
            GamesContract.GamesEntry.COLUMN_NAME_ID,
            GamesContract.GamesEntry.COLUMN_NAME_TITLE,
            GamesContract.GamesEntry.COLUMN_NAME_PLATFORM,
            GamesContract.GamesEntry.COLUMN_NAME_DATE,
            GamesContract.GamesEntry.COLUMN_NAME_STATUS,
            GamesContract.GamesEntry.COLUMN_NAME_NOTES };


    public DataSource(Context context) {
        mDBHelper = new DBHelper(context);
    }
    // Opens the database to use it
    public void open()  {
        mDatabase = mDBHelper.getWritableDatabase();
    }
    // Closes the database when you no longer need it
    public void close() {
        mDBHelper.close();
    }

    public void saveGame(Game game) {

        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(GamesContract.GamesEntry.COLUMN_NAME_TITLE, game.getTitle());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_DATE, game.getDateAdded());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_STATUS, game.getGameStatus());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_NOTES, game.getNotes());
        // Inserting Row
        mDatabase.insert(GamesContract.GamesEntry.TABLE_NAME, null, values);
        close(); // Closing database connection
    }

    public void modifyGame(Game game) {
        // Open connection to write data
        open();
        ContentValues values = new ContentValues();
        values.put(GamesContract.GamesEntry.COLUMN_NAME_TITLE, game.getTitle());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_PLATFORM, game.getPlatform());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_DATE, game.getDateAdded());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_STATUS, game.getGameStatus());
        values.put(GamesContract.GamesEntry.COLUMN_NAME_NOTES, game.getNotes());

        mDatabase.update(GamesContract.GamesEntry.TABLE_NAME, values, GamesContract.GamesEntry.COLUMN_NAME_ID + "= ?", new String[]{String.valueOf(game.getId())});
        mDatabase.close(); // Closing database connection
    }

    public Cursor getAllGames() {
        return mDatabase.query(GamesContract.GamesEntry.TABLE_NAME, GAMES_ALL_COLUMNS, null, null, null, null, null);
    }




    public List<Game> getGames() // Get all games
    {
        //Open connection to read only
        mDatabase = mDBHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                GamesContract.GamesEntry.COLUMN_NAME_ID + ',' +
                GamesContract.GamesEntry.COLUMN_NAME_TITLE + ',' +
                GamesContract.GamesEntry.COLUMN_NAME_PLATFORM + ',' +
                GamesContract.GamesEntry.COLUMN_NAME_DATE + ',' +
                GamesContract.GamesEntry.COLUMN_NAME_STATUS + ',' +
                GamesContract.GamesEntry.COLUMN_NAME_NOTES +
                " FROM " + GamesContract.GamesEntry.TABLE_NAME;

        //User user = new User();
        List<Game> gameList = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setId(cursor.getInt(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_ID)));
                game.setTitle(cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_TITLE)));
                game.setPlatform(cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_PLATFORM)));
                game.setDateAdded(cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_DATE)));
                game.setGameStatus(cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_STATUS)));
                game.setNotes(cursor.getString(cursor.getColumnIndex(GamesContract.GamesEntry.COLUMN_NAME_NOTES)));
                gameList.add(game);

            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return gameList;
    }

    public void deleteGame(long user_Id) {
        open();
        // It's a good practice to use parameter ?, instead of concatenate string
        mDatabase.delete(GamesContract.GamesEntry.TABLE_NAME, GamesContract.GamesEntry.COLUMN_NAME_ID + " =?",
                new String[]{Long.toString(user_Id)});
        close(); // Closing database connection
    }


    public void deleteAll()
    {
        open();
        mDatabase.delete(GamesContract.GamesEntry.TABLE_NAME, null,null);
        close();
    }

}