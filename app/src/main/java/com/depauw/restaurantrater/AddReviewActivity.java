package com.depauw.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.depauw.restaurantrater.databinding.ActivityAddReviewBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddReviewBinding binding;

    private String DATE;
    private String TIME;
    private String MEAL;
    private String IS_FAVORITE;
    private String RATING;
    private String RESTAURANT_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddReview.setOnClickListener(this);
        binding.edittextReviewDate.setOnClickListener(this);
        binding.edittextReviewTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.edittext_review_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        this, datePickerDialog_dateSetListener, LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                datePickerDialog.show();
                break;
            case R.id.edittext_review_time:
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        this, timePickerDialog_timeSetListener, LocalTime.now().getHour(),
                        LocalTime.now().getMinute(), false);
                timePickerDialog.show();
                break;
            case R.id.button_add_review:
                switch(binding.radiogroupMeals.getCheckedRadioButtonId())
                {
                    case R.id.radio_breakfast:
                        MEAL = binding.radioBreakfast.getText().toString();
                        break;
                    case R.id.radio_lunch:
                        MEAL = binding.radioLunch.getText().toString();
                        break;
                    case R.id.radio_dinner:
                        MEAL = binding.radioDinner.getText().toString();
                        break;
                }
                if(binding.checkboxFavorite.isChecked())
                {
                    IS_FAVORITE="1";
                }
                else
                {
                    IS_FAVORITE="0";
                }
                RESTAURANT_NAME=binding.edittextRestaurantName.getText().toString();
                RATING = String.valueOf(binding.seekbarRating.getProgress());
                String newReviewCsvEntry=RESTAURANT_NAME+","+DATE+
                        ","+TIME+","+MEAL+","+RATING+","+IS_FAVORITE+"\r\n";
                File reviewsFile = new File(getFilesDir(), "reviews.csv");
                try (FileWriter fw = new FileWriter(reviewsFile, true))
                {
                    fw.write(newReviewCsvEntry);
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }

    }

    private DatePickerDialog.OnDateSetListener datePickerDialog_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            DATE = String.valueOf(month)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
            binding.edittextReviewDate.setText(DATE);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerDialog_timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

            if(hourOfDay==0)
            {
                TIME = String.valueOf(12)+":"+String.valueOf(minute)+" AM";
            }
            else if(hourOfDay<=12)
            {
                TIME = String.valueOf(hourOfDay)+":"+String.valueOf(minute)+" AM";
            }
            else
            {
                TIME = String.valueOf((hourOfDay-12))+":"+String.valueOf(minute)+" PM";
            }
            binding.edittextReviewTime.setText(TIME);
        }
    };
}