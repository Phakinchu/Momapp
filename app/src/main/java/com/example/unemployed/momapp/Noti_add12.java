package com.example.unemployed.momapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Noti_add12 extends Service {
    long count_move ;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    private NotificationManager notifManager;

    public Noti_add12() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("ssssss", "onCreate");

//        Toast.makeText(this, "Service add12 Create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();


        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        long date = System.currentTimeMillis();
        String datastring = timeformat.format(date);
        Date NowChecktime = java.sql.Time.valueOf(datastring);


        final String dateforfirebase = new SimpleDateFormat("d+M+yyyy").format(Calendar.getInstance().getTime());
        Log.i("dateforfirebase", dateforfirebase);
        Log.i("FirebaseUser user", user.getUid());
        dref.child("User").child(user.getUid()).child("Date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count_move = dataSnapshot.child(dateforfirebase).getValue(Integer.class);
                Log.i("count move ", "is" + count_move);
                if (count_move>=10) {
                    Log.i("count move on <10", "is" + count_move);
                    addNotification("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + "ครั้ง ซึ่งถือว่าอยู่ในเกณฑ์ปกติค่ะคุณแม่ไม่ต้องกังวลนะคะ",HomeActivity.class);
                    stopSelf();
                } else {
                    Log.i("count move on Another", "is" + count_move);
                    addNotification("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + "ครั้ง ซึ่งถือว่าอาจเกิดอันตรายหรือมีปัญหากับทารกในครรภ์ แนะนำให้คุณแม่ไปโรงพยาบาลทันทีที่ห้องคลอด รพ.จุฬาลงกรณ์ สภากาชาดไทย อาคารภูมิสิริมังคลานุสรณ์ชั้น9 โซนเอ หรือ โทร02-2565273-4",ContractActicity.class);
                    stopSelf();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Transaction completed
                Log.d("dataSnapshot", "postTransaction:onComplete:" + databaseError);
            }
        });
        Log.i("onHandleIntent :", "onHandleIntent Online");

        Log.i("now check tme", "i " + NowChecktime);

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.i("ssssss", "onDestroy");
//        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    private void addNotification(String aMessage, String artibye,Class<?> Activity) {
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

            intent = new Intent(this, Activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(artibye)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(artibye))
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, Activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(artibye)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(artibye))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

}
