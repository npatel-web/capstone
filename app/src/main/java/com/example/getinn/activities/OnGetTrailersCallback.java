package com.example.getinn.activities;

import java.util.List;

public interface OnGetTrailersCallback {

    void onSuccess(List<Trailer> trailers);

    void onError();

}
