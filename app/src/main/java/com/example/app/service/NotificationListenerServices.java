package com.example.app.service;

import android.annotation.SuppressLint;
import android.os.Build;
import android.service.notification.NotificationListenerService;

import androidx.annotation.RequiresApi;

@SuppressLint("OverrideAbstract")

@RequiresApi(api = Build.VERSION_CODES.M)
public class NotificationListenerServices extends NotificationListenerService {

}
