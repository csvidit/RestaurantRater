package com.depauw.restaurantrater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.depauw.restaurantrater.databinding.ActivityViewReviewsBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ViewReviewsActivity extends AppCompatActivity {

    private ActivityViewReviewsBinding binding;
    private List<Review> reviews;

    public static final int FROM_ADD_REVIEW_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        binding = ActivityViewReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reviews = new ArrayList<Review>();
        setReviewList();
        binding.listviewReviews.setOnItemClickListener(listviewReviews_onItemClickListener);
        binding.buttonAddReview.setOnClickListener(button_addreview_clickListener);
    }

    private void setReviewList()
    {
        File reviewsFile = new File(getFilesDir(), "reviews.csv");
        try(Scanner sc = new Scanner(reviewsFile))
        {
            while(sc.hasNextLine())
            {
                Review newReview = new Review(sc.nextLine());
                Log.d("shawn", newReview.getRestaurantName());
                reviews.add(newReview);
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        CustomAdapter adapter = new CustomAdapter(reviews, this);
        binding.listviewReviews.setAdapter(adapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setReviewList();

    }

    private AdapterView.OnItemClickListener listviewReviews_onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Review thisReview = (Review) adapterView.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewReviewsActivity.this);
            String message = "This review was created on "+thisReview.getDate()+" at "+thisReview.getTime();
            builder.setTitle("Review Details").setMessage(message);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private View.OnClickListener button_addreview_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent addReview = new Intent(ViewReviewsActivity.this, AddReviewActivity.class);
            startActivityForResult(addReview, FROM_ADD_REVIEW_ACTIVITY);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FROM_ADD_REVIEW_ACTIVITY && resultCode == Activity.RESULT_OK)
        {
//            setReviewList();
        }
    }
}