package edu.uga.cs.unirider;

import android.os.Bundle;
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

    private AcceptedRequestsRecyclerAdapter recyclerAdapter1;
    private List<AcceptedOffer> acceptedOffersList;

    private List<AcceptedRequest> acceptedRequestList;

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

        acceptedRequestList = new ArrayList<>();
        recyclerAdapter1 = new AcceptedRequestsRecyclerAdapter(acceptedRequestList, this);
        recyclerView.setAdapter(recyclerAdapter1);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        // Load accepted offers from Firebase
        loadAcceptedOffers();
        loadAcceptedRequests();
    }

    private void loadAcceptedOffers() {
        // Reference to the "AcceptedOffers" node in Firebase
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
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }
    private void loadAcceptedRequests() {
        // Reference to the "AcceptedOffers" node in Firebase
        DatabaseReference acceptedRequestsRef = database.getReference("AcceptedRequests");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        acceptedRequestsRef.orderByChild("driverName").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acceptedRequestList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AcceptedRequest acceptedRequest = snapshot.getValue(AcceptedRequest.class);
                    acceptedRequest.setKey(snapshot.getKey());
                    if (acceptedRequest != null) {
                        acceptedRequestList.add(acceptedRequest);
                    }
                }
                recyclerAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }
}
