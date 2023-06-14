package training.android.sportclub;

import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.CONTENT_URI;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_FIRST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_GENDER;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_LAST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_SPORT;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.TABLE_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry._ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import training.android.sportclub.data.ClubOlympusContract;
import training.android.sportclub.data.MemberCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listViewMembers;
    private static final int MEMBER_LOADER = 123;
    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMembers = findViewById(R.id.listViewMembers);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });
        memberCursorAdapter = new MemberCursorAdapter(this, null, false);
        listViewMembers.setAdapter(memberCursorAdapter);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(MEMBER_LOADER, null, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_SPORT};

        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}