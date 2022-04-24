package com.depauw.restaurantrater;

public class Review {

    private String restaurantName;
    private String date;
    private String time;
    private String meal;
    private int rating;
    private boolean isFavorite;

    public Review(String reviewLine)
    {
        String[] review = reviewLine.split(",");
        restaurantName = review[0];
        date = review[1];
        time = review[2];
        meal = review[3];
        rating = Integer.valueOf(review[4]);
        if(review[5].equals("1"))
        {
            isFavorite = true;
        }
        else
        {
            isFavorite = false;
        }
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
