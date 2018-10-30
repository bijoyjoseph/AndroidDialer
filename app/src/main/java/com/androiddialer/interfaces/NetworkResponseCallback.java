package com.androiddialer.interfaces;

public interface NetworkResponseCallback {

    void onSuccess(Object... object);

    void onError(Exception exception);
}
