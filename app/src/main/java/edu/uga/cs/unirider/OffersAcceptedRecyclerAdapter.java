package edu.uga.cs.unirider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OffersAcceptedRecyclerAdapter extends RecyclerView.Adapter<OffersAcceptedRecyclerAdapter.AcceptedOfferHolder> {

    public static final String DEBUG_TAG = "OffersAcceptedRecyclerAdapter";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference reference1;

    private List<OfferAccepted> acceptedOffersList;
    private Context context;

    public OffersAcceptedRecyclerAdapter(List<OfferAccepted> acceptedOffersList, Context context) {
        this.acceptedOffersList = acceptedOffersList;
        this.context = context;
    }

    class AcceptedOfferHolder extends RecyclerView.ViewHolder {

        TextView driverName;
        TextView riderName;
        TextView date;
        TextView time;
        TextView pickup;
        TextView dropoff;

        Button confirmOfferButton;

        public AcceptedOfferHolder(View itemView) {
            super(itemView);

            riderName = itemView.findViewById(R.id.accepted_rideOffers_riderName_TextView);
            driverName = itemView.findViewById(R.id.accepted_rideOffers_driverName_TextView);
            date = itemView.findViewById(R.id.accepted_rideOffers_date_textView);
            time = itemView.findViewById(R.id.accepted_rideOffers_time_TextView);
            pickup = itemView.findViewById(R.id.accepted_rideOffers_pickup_textView);
            dropoff = itemView.findViewById(R.id.accepted_rideOffers_destination_TextView);


            confirmOfferButton = itemView.findViewById(R.id.accepted_rideOffers_accept_button);
        }
    }

    @NonNull
    @Override
    public AcceptedOfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_accepted_offers, parent, false);
        return new AcceptedOfferHolder(view);
    }

    @Override
    public void onBindViewHolder(AcceptedOfferHolder holder, int position) {
        OfferAccepted acceptedOffer = acceptedOffersList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + acceptedOffer);

        String driverName = acceptedOffer.getDriverName();
        String riderName = acceptedOffer.getRiderName();
        String date = acceptedOffer.getDate();
        String time = acceptedOffer.getTime();
        String pickup = acceptedOffer.getPickup();
        String dropoff = acceptedOffer.getDropoff();

        holder.driverName.setText(driverName);
        holder.riderName.setText(riderName);
        holder.date.setText(date);
        holder.time.setText(time);
        holder.pickup.setText(pickup);
        holder.dropoff.setText(dropoff);

        // Add a click listener for the confirm button
        if (acceptedOffer.isDriverConfirmed()) {
            holder.confirmOfferButton.setEnabled(false);
            holder.confirmOfferButton.setText("Confirmed");
        } else {
            holder.confirmOfferButton.setEnabled(true);
            holder.confirmOfferButton.setText("Confirm Ride Offer");
            holder.confirmOfferButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if (currentUser != null) {
                        // Get the current user's email
                        String currentDriverEmail = currentUser.getEmail();
                        String currentRiderEmail = acceptedOffer.getRiderName();
                        String key = acceptedOffer.getKey();
                        holder.confirmOfferButton.setEnabled(false);
                        reference1 = database.getReference("AcceptedOffers");
                        holder.confirmOfferButton.setText("Confirmed");
                        acceptedOffer.setDriverConfirmed(true);
                        reference1.child(key).child("driverConfirmed").setValue(true);
                        // Function to update user points based on the email
                        updateUserPoints(currentDriverEmail, 50);
                        updateUserPoints(currentRiderEmail, -50);
                    } else {
                        // User is not signed in
                        if (context != null) {
                            Toast.makeText(context, "User is not signed in", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TAG", "Context is null");
                        }
                    }
                }
            });
        }
    }
    // Separate function to update user points
    private void updateUserPoints(String userEmail, int pointsChange) {
        // Query the database to find the user with the matching email
        reference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Loop through the results (there should be only one match)
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Get the userPoints snapshot
                        DataSnapshot userPointsSnapshot = snapshot.child("userPoints");

                        // Check if the "userPoints" node exists
                        if (userPointsSnapshot.exists()) {
                            // Get the current userPoints value
                            Object rawUserPoints = userPointsSnapshot.getValue();

                            // Check if rawUserPoints is not null
                            if (rawUserPoints != null) {
                                // Attempt to convert the value to Integer
                                try {
                                    Integer existingUserPoints = Integer.valueOf(rawUserPoints.toString());

                                    // Update the userPoints based on the pointsChange
                                    int updatedUserPoints = existingUserPoints + pointsChange;

                                    // Update the userPoints in the database
                                    reference.child(snapshot.getKey()).child("userPoints").setValue(updatedUserPoints);

                                    // Access the context from the adapter
                                    if (context != null) {
                                        Toast.makeText(context, "UserPoints are updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("TAG", "Context is null");
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the case where the "userPoints" value cannot be converted to Integer
                                    Log.e("TAG", "Error converting userPoints to Integer: " + e.getMessage());
                                }
                            } else {
                                // Handle the case where rawUserPoints is null
                                // This may happen if the "userPoints" value is missing or null in the database
                                Log.e("TAG", "userPoints value is missing or null in the database");
                            }
                        } else {
                            // Handle the case where the "userPoints" node does not exist in the database
                            Log.e("TAG", "userPoints node does not exist in the database");
                        }
                    }
                } else {
                    // No user found with the provided email
                    Log.d("TAG", "No user found with the provided email: " + userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("TAG", "Database error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return acceptedOffersList.size();
    }
}

