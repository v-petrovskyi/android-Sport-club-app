package training.android.sportclub.data;

import static training.android.sportclub.data.ClubOlympusContract.DATABASE_NAME;
import static training.android.sportclub.data.ClubOlympusContract.DATABASE_VERSION;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_FIRST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_GENDER;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_SPORT;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry._ID;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_LAST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OlympusDbOpenHelper extends SQLiteOpenHelper {

    public OlympusDbOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY," +
                KEY_FIRST_NAME + " TEXT," +
                KEY_LAST_NAME + " TEXT," +
                KEY_GENDER + " INTEGER NOT NULL," +
                KEY_SPORT + " TEXT" + ")";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
