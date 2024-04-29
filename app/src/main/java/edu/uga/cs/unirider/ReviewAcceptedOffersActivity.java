package edu.uga.cs.unirider;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ReviewAcceptedOffersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AcceptedOffersRecyclerAdapter recyclerAdapter;
    private List<AcceptedOffer> acceptedOffersList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_accepted_ride_offers);

        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        acceptedOffersList = new ArrayList<>();
        recyclerAdapter = new AcceptedOffersRecyclerAdapter(acceptedOffersList, this);
        recyclerView.setAdapter(recyclerAdapter);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        loadAcceptedOffers();
    }

    private void loadAcceptedOffers() {
        DatabaseReference acceptedOffersRef = database.getReference("AcceptedOffers");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        acceptedOffersRef.orderByChild("driverName").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acceptedOffersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AcceptedOffer acceptedOffer = snapshot.getValue(AcceptedOffer.class);
                    acceptedOffer.setKey(snapshot.getKey());
                    if (acceptedOffer != null) {
                        acceptedOffersList.add(acceptedOffer);
                    }
                    Log.d("DEBUG_TAG", "loadAcceptedOffers: " + acceptedOffer);

                }
                
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }
}
