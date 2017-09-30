package com.example.test.gamesbacklog.activity.utility;

import android.provider.BaseColumns;

/**
 * Created by Remy on 28-9-2017.
 */

public final class GamesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GamesContract() {}

    /* Inner class that defines the table contents */
    public static class GamesEntry implements BaseColumns {
        // Labels table name
        public static final String TABLE_NAME = "Games";
        // Labels Table Columns names
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PLATFORM = "platform";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_NOTES = "notes";
    }
}
