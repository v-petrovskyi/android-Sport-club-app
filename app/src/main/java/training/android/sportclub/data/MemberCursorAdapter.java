package training.android.sportclub.data;

import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_FIRST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_GENDER;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_LAST_NAME;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry.KEY_SPORT;
import static training.android.sportclub.data.ClubOlympusContract.MemberEntry._ID;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import training.android.sportclub.R;

public class MemberCursorAdapter extends CursorAdapter {

    public MemberCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.member_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvLastName = view.findViewById(R.id.tvLastName);
        TextView tvGender = view.findViewById(R.id.tvGender);
        TextView tvSport = view.findViewById(R.id.tvSport);

        int idIndex = cursor.getColumnIndex(_ID);
        int firstNameIndex = cursor.getColumnIndex(KEY_FIRST_NAME);
        int lastNameIndex = cursor.getColumnIndex(KEY_LAST_NAME);
        int genderIndex = cursor.getColumnIndex(KEY_GENDER);
        int sportIndex = cursor.getColumnIndex(KEY_SPORT);

        int currentId = cursor.getInt(idIndex);
        String currentFirstName = cursor.getString(firstNameIndex);
        String currentLastName = cursor.getString(lastNameIndex);
        int currentGender = cursor.getInt(genderIndex);
        String currentSport = cursor.getString(sportIndex);


        tvId.setText(String.valueOf(currentId));
        tvFirstName.setText(currentFirstName);
        tvLastName.setText(currentLastName);
        tvGender.setText(String.valueOf(currentGender));
        tvSport.setText(currentSport);
    }
}
