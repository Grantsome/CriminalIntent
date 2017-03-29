package com.grantsome.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tom on 2017/3/13.
 */

public class DatePickerFragment extends DialogFragment {

    public static String DATA = "date";

    public static final String EXTRA_DATE = "mDate";

    public DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle bundle = new Bundle();
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        bundle.putSerializable(DATA,date);
        Log.d("DatePickerFragment","date = " +date.toString());
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Date date = (Date) getArguments().getSerializable(DATA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        //DatePicker datePicker = new DatePicker(getActivity());
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year,month,day,null);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date myDate = new GregorianCalendar(year,month,day).getTime();
                Log.d("DatePickerFragment","new 里面的date = " +myDate.toString());
                sendRequest(Activity.RESULT_OK,myDate);
            }
       }).create();
    }

    private void sendRequest(int requestCode, Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        Log.d("DatePickerFragment","send里面的date = " +date.toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(),requestCode,intent);
    }

}
