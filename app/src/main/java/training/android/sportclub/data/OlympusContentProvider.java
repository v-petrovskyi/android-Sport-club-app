package training.android.sportclub.data;

import static training.android.sportclub.data.ClubOlympusContract.AUTHORITY;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.CONTENT_MULTIPLE_ITEMS;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.CONTENT_SINGLE_ITEM;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.GENDER_FEMALE;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.GENDER_MALE;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.GENDER_UNKNOWN;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_FIRST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_GENDER;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_LAST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_SPORT;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.TABLE_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry._ID;
import static training.android.sportclub.data.ClubOlympusContract.PATH_MEMBERS;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OlympusContentProvider extends ContentProvider {
    OlympusDbOpenHelper dbOpenHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    static {
        uriMatcher.addURI(AUTHORITY, PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(AUTHORITY, PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new OlympusDbOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MEMBER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:
                return CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        String firstName = contentValues.getAsString(KEY_FIRST_NAME);
        if (firstName == null) {
            throw new IllegalArgumentException("You have to input first name");
        }
        String lastName = contentValues.getAsString(KEY_LAST_NAME);
        if (lastName == null) {
            throw new IllegalArgumentException("You have to input last name");
        }
        Integer gender = contentValues.getAsInteger(KEY_GENDER);
        if (gender == null || !(gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE)) {
            throw new IllegalArgumentException("You have to input correct gender");
        }
        String sport = contentValues.getAsString(KEY_SPORT);
        if (sport == null) {
            throw new IllegalArgumentException("You have to input sport type");
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                long id = db.insert(TABLE_NAME, null, contentValues);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return db.delete(TABLE_NAME, selection, selectionArgs);
            case MEMBER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't delete this URI" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        if (contentValues.containsKey(KEY_FIRST_NAME)) {
            String firstName = contentValues.getAsString(KEY_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }

        if (contentValues.containsKey(KEY_LAST_NAME)) {
            String lastName = contentValues.getAsString(KEY_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (contentValues.containsKey(KEY_GENDER)) {
            Integer gender = contentValues.getAsInteger(KEY_GENDER);
            if (gender == null || !(gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE)) {
                throw new IllegalArgumentException("You have to input correct gender");
            }
        }
        if (contentValues.containsKey(KEY_SPORT)) {
            String sport = contentValues.getAsString(KEY_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport type");
            }
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case MEMBERS:
                return db.update(TABLE_NAME, contentValues, selection, selectionArgs);
            case MEMBER_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(TABLE_NAME, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't delete update URI" + uri);
        }
    }
}
