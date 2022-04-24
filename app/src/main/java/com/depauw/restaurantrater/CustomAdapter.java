package com.depauw.restaurantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Review> reviews;
    private Context context;

    public CustomAdapter(List<Review> reviews, Context context)
    {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_reviews_row, parent, false);
        }
        Review thisReview = reviews.get(position);

        TextView restaurantName = convertView.findViewById(R.id.textview_restaurant_name);
        RatingBar isFavorite = convertView.findViewById(R.id.ratingbar_is_favorite);
        RadioGroup meal = convertView.findViewById(R.id.radiogroup_meal);
        ProgressBar rating = convertView.findViewById(R.id.progressbar_rating);

        restaurantName.setText(thisReview.getRestaurantName());
        if(thisReview.isFavorite())
        {
            isFavorite.setRating(1.0f);
        }
        else
        {
            isFavorite.setRating(0.0f);
        }
        switch(thisReview.getMeal())
        {
            case "Breakfast":
                meal.check(R.id.radiobutton_breakfast);
                break;
            case "Lunch":
                meal.check(R.id.radiobutton_lunch);
                break;
            case "Dinner":
                meal.check(R.id.radiobutton_dinner);
                break;
        }
        rating.setProgress(thisReview.getRating());

        return convertView;
    }
}
