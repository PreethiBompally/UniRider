package edu.uga.cs.unirider;



import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Driver extends AppCompatActivity {

    private CardView postRideRequest;
    private CardView viewRideRequest;
    private CardView viewAcceptedRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        viewRideRequest = findViewById(R.id.cardViewViewRideRequest);
        postRideRequest = findViewById(R.id.cardViewPostRideRequest);
        viewAcceptedRides = findViewById(R.id.cardViewViewAcceptedRides1);

        viewRideRequest.setOnClickListener(new ViewRideRequestButtonClickListener());
        postRideRequest.setOnClickListener(new PostRideRequestButtonClickListener());
        viewAcceptedRides.setOnClickListener(new ViewAcceptedRidesButtonClickListener());

    }

    private class PostRideRequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), OfferRide.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ViewRideRequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewRideRequestsActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ViewAcceptedRidesButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewAcceptedOffersActivity.class);
            view.getContext().startActivity(intent);
        }
    }


}