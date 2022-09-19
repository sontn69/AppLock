package com.example.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.app.other.PrefrancesUtils;
import com.example.app.service.PinLockService;

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = PrefrancesUtils.getLockType(context);

        if (type.equalsIgnoreCase("pin")) {
            context.stopService(new Intent(context, PinLockService.class));
        } else if (type.equalsIgnoreCase("pattern")) {
            context.stopService(new Intent(context, PinLockService.class));
        } else if (type.equalsIgnoreCase("gesture")) {
            context.stopService(new Intent(context, PinLockService.class));
        }
    }
}