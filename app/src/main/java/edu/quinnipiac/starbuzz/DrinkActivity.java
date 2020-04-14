package edu.quinnipiac.starbuzz;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
        //Drink drink = Drink.drinks[drinkId];

        //Create cursor
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try{
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();//this gets a reference to the database
            Cursor cursor = db.query ("DRINK", new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[] {Integer.toString(drinkId)},
            null, null, null);
            if(cursor.moveToFirst()){
                //Get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoID = cursor.getInt(2);

                //Populate the drink name
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //Populate drink descriptions
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                //Populate drink image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoID);
                photo.setContentDescription(nameText);
                Log.d("--- DRINK ACTIVIY ---", "   Drink Name:  " + nameText);
            }
            cursor.close();
            db.close();
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database Unavailable.", Toast.LENGTH_LONG);
            toast.show();
        }
    }


}