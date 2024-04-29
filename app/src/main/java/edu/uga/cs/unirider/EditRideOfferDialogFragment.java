package edu.uga.cs.unirider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class EditRideOfferDialogFragment extends DialogFragment {

    public static final int SAVE = 1; 
    public static final int DELETE = 2;

    public static final int ACCEPT = 3;

    private TextView driverNameView;
    private DatePicker dateView;
    private TimePicker timeView;
    private EditText pickupView;
    private EditText dropoffView;

    int position;

    String key;
    String driverName;
    String date;
    String time;
    String pickup;
    String dropoff;

    public interface EditRideOfferDialogListener {
        void updateRideOffer(int position, RideOffer rideOffer, int action);
    }

    public static EditRideOfferDialogFragment newInstance(int position, String key, String driverName, String date, String time, String pickup, String dropoff) {
        EditRideOfferDialogFragment dialog = new EditRideOfferDialogFragment();

        //
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("key", key);
        args.putString("driverName", driverName);
        args.putString("date", date);
        args.putString("time", time);
        args.putString("pickup", pickup);
        args.putString("dropoff", dropoff);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        driverName = getArguments().getString("driverName");
        date = getArguments().getString("date");
        time = getArguments().getString("time");
        pickup = getArguments().getString("pickup");
        dropoff = getArguments().getString("dropoff");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_ride_offer_dialog, getActivity().findViewById(R.id.root));

        driverNameView = layout.findViewById(R.id.rideOfferDialog_editText1);
        dateView = layout.findViewById(R.id.rideOfferDialog_editText2);
        timeView = layout.findViewById(R.id.rideOfferDialog_editText3);
        pickupView = layout.findViewById(R.id.rideOfferDialog_editText4);
        dropoffView = layout.findViewById(R.id.rideOfferDialog_editText5);

        driverNameView.setText(driverName);
        dateView.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(getContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        timeView.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new TimePickerDialog(getContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();
        });
        pickupView.setText(pickup);
        dropoffView.setText(dropoff);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        builder.setTitle("Edit Ride Offer");

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("SAVE", new SaveButtonClickListener());

        builder.setNeutralButton("DELETE", new DeleteButtonClickListener());

        return builder.create();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateInView(cal);
        }
    };

    private void updateDateInView(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dateView.updateDate(year, month, day);
    }
    

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            updateTimeInView(cal);
        }
    };

    private void updateTimeInView(Calendar cal) {
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        timeView.setHour(hourOfDay);
        timeView.setMinute(minute);

    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String editedDriverName = driverNameView.getText().toString();
            int year = dateView.getYear();
            int month = dateView.getMonth();
            int day = dateView.getDayOfMonth();
            int hour = timeView.getCurrentHour();
            int minute = timeView.getCurrentMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            String editedDate = dateFormat.format(calendar.getTime());
            String editedTime = timeFormat.format(calendar.getTime());
            String editedPickup = pickupView.getText().toString();
            String editedDropoff = dropoffView.getText().toString();

            RideOffer rideOffer = new RideOffer(editedDriverName, editedDate, editedTime, editedPickup, editedDropoff);
            rideOffer.setKey(key);

            EditRideOfferDialogListener listener = (EditRideOfferDialogListener) getActivity();
            listener.updateRideOffer(position, rideOffer, SAVE);

            dialog.dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            RideOffer rideOffer = new RideOffer(driverName, date, time, pickup, dropoff);
            rideOffer.setKey(key);

            EditRideOfferDialogListener listener = (EditRideOfferDialogListener) getActivity();
            listener.updateRideOffer(position, rideOffer, DELETE);

            dialog.dismiss();
        }
    }

    private class AcceptButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            RideOffer rideOffer = new RideOffer(driverName, date, time, pickup, dropoff);
            rideOffer.setKey(key);

            EditRideOfferDialogListener listener = (EditRideOfferDialogListener) getActivity();
            listener.updateRideOffer(position, rideOffer, ACCEPT);

            dialog.dismiss();
        }
    }
}

