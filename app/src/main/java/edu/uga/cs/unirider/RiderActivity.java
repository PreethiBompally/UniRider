package edu.uga.cs.unirider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RiderActivity extends AppCompatActivity {

    private CardView viewRideOffers;
    private CardView postRideRequests;
    private CardView viewAcceptedRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        viewRideOffers = findViewById(R.id.cardViewViewRideOffer);
        postRideRequests = findViewById(R.id.cardViewRequestRide);
        viewAcceptedRides = findViewById(R.id.cardViewViewAcceptedRides1);

        viewRideOffers.setOnClickListener(new ViewRideOffersButtonClickListener());
        postRideRequests.setOnClickListener(new PostRideRequestButtonClickListener());
        viewAcceptedRides.setOnClickListener(new ViewAcceptedRidesButtonClickListener());
    }

    private class ViewRideOffersButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewRideOffersActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class PostRideRequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), NewRideRequest.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ViewAcceptedRidesButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), ReviewAcceptedRequestsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}