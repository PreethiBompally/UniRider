package edu.uga.cs.unirider;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;

public class NewRideOffer extends AppCompatActivity {

    private static final String TAG = "NewRideOffer"; // Define a TAG for logging

    private TextView driverName;
    private DatePicker date;
    private TimePicker time;
    private TextView text;
    private TextView pickup;
    private TextView dropoff;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;

    private Button saveButton;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride_offer);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        date = (DatePicker) findViewById(R.id.DatePicker01);
        time = (TimePicker) findViewById(R.id.TimePicker01);
        text = (TextView) findViewById(R.id.text_datetime);
        pickup = findViewById(R.id.editText4);
        dropoff = findViewById(R.id.editText5);
        saveButton = findViewById(R.id.button);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        timeFormatter = new SimpleDateFormat("HH:mm");

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                text.setText(calendar.getTime().toString());
            }
        });

        Calendar cal = Calendar.getInstance();

        date.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth, time.getCurrentHour(),
                                time.getCurrentMinute());
                        text.setText(calendar.getTime().toString());
                    }
                });


        saveButton.setOnClickListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "User email: " + user.getEmail());
            String driverNameText = user.getEmail();
            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                    time.getCurrentHour(), time.getCurrentMinute());

            String formattedDate = dateFormatter.format(calendar.getTime());
            String formattedTime = timeFormatter.format(calendar.getTime());

            String pickupText = pickup.getText().toString();
            String dropoffText = dropoff.getText().toString();

            final RideOffer rideOffer = new RideOffer(driverNameText, formattedTime, formattedDate, pickupText, dropoffText);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("RideOffers");

            myRef.push().setValue(rideOffer)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Ride offer created for " + rideOffer.getDriverName(),
                                    Toast.LENGTH_SHORT).show();

                            Log.d("Ride offer created for ", rideOffer.getDriverName());

                            driverName.setText("");
//                            date.setText("");
//                            time.setText("");
                            pickup.setText("");
                            dropoff.setText("");

                            Log.d(TAG, "Ride offer created successfully");

                            Intent intent = new Intent(NewRideOffer.this, Driver.class);
                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create a ride offer for " + rideOffer.getDriverName(),
                                    Toast.LENGTH_SHORT).show();

                            Log.e(TAG, "Failed to create a ride offer", e);
                        }
                    });
        }
    }
}
