package training.android.sportclub;

import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.CONTENT_URI;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_FIRST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_GENDER;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_LAST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_SPORT;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.TABLE_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry._ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import training.android.sportclub.data.ClubOlympusContract;

public class MainActivity extends AppCompatActivity {
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_SPORT};
        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, null, null);
        dataTextView.setText("all members\n\n");
        dataTextView.append(_ID + " " + KEY_FIRST_NAME + " " + KEY_LAST_NAME + " " + KEY_GENDER + " " + KEY_SPORT);
        int idIndex = cursor.getColumnIndex(_ID);
        int firstNameIndex = cursor.getColumnIndex(KEY_FIRST_NAME);
        int lastNameIndex = cursor.getColumnIndex(KEY_LAST_NAME);
        int genderIndex = cursor.getColumnIndex(KEY_GENDER);
        int sportIndex = cursor.getColumnIndex(KEY_SPORT);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentFirstName = cursor.getString(firstNameIndex);
            String currentLastName = cursor.getString(lastNameIndex);
            int currentGender = cursor.getInt(genderIndex);
            String currentSport = cursor.getString(sportIndex);

            dataTextView.append("\n" + currentId + " " + currentFirstName+ " " +currentLastName+ " " +currentGender+ " " +currentSport);
        }
        cursor.close();
    }
}