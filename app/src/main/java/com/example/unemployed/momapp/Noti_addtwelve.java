package com.example.unemployed.momapp;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Noti_addtwelve extends IntentService {
    private NotificationManager notifManager;
    Integer count_move = 50;
    boolean running = true;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.unemployed.momapp.action.FOO";
    private static final String ACTION_BAZ = "com.example.unemployed.momapp.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.unemployed.momapp.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.unemployed.momapp.extra.PARAM2";

    public Noti_addtwelve() {
        super("Noti_addtwelve");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, Noti_addtwelve.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, Noti_addtwelve.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String date1 = intent.getStringExtra("timeintwelve");
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        try {
            while (running) {
                if (isMyServiceRunning(Noti_addtwelve.class)) {
                    String firstcheck = date1;
                    Date firstchecktime = java.sql.Time.valueOf(firstcheck);
                    SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
                    long date = System.currentTimeMillis();
                    String datastring = timeformat.format(date);
                    Date NowChecktime = java.sql.Time.valueOf(datastring);

                    if (NowChecktime.after(firstchecktime)) {
                        final String dateforfirebase = new SimpleDateFormat("d+M+yyyy").format(Calendar.getInstance().getTime());
                        Log.i("dateforfirebase", dateforfirebase);
                        Log.i("FirebaseUser user", user.getUid());
                        dref.child("User").child(user.getUid()).child("Date").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                count_move = dataSnapshot.child(dateforfirebase).getValue(Integer.class);
                                Log.i("count move ", "is" + count_move);
                                if (count_move>=10) {
                                    Log.i("count move on >10", "is" + count_move);
                                    addNotification("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + "ครั้ง ซึ่งถือว่าอยู่ในเกณฑ์ปกติค่ะคุณแม่ไม่ต้องกังวลนะคะ");
                                    stopSelf();
                                    running = false;
                                    return;
                                } else {
                                    Log.i("count move on Another", "is" + count_move);
                                    addNotification("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + "ครั้ง ซึ่งถือว่าอาจเกิดอันตรายหรือมีปัญหากับทารกในครรภ์ แนะนำให้คุณแม่ไปโรงพยาบาลทันทีที่ห้องคลอด รพ.จุฬาลงกรณ์ สภากาชาดไทย อาคารภูมิสิริมังคลานุสรณ์ชั้น9 โซนเอ หรือ โทร02-2565273-4");
                                    stopSelf();
                                    running = false;
                                    return;
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Transaction completed
                                Log.d("dataSnapshot", "postTransaction:onComplete:" + databaseError);
                            }
                        });
                        Log.i("onHandleIntent :", "onHandleIntent Online");
                    }
                    Log.i("now check tme", "i " + NowChecktime);
                    Log.i("first check tme", "i " + firstchecktime);
                    Thread.sleep(2000);
                }
            }

        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }


    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void addNotification(String aMessage , String artibye) {
//        Toast.makeText(this, "addNotification", Toast.LENGTH_SHORT).show();
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(artibye))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(artibye))
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
