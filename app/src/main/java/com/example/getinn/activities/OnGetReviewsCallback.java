package com.example.getinn.activities;

import java.util.List;

public interface OnGetReviewsCallback {

    void onSuccess(List<Review> reviews);

    void onError();

}
