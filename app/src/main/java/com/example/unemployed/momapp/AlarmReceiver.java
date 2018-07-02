package com.example.unemployed.momapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager notifManager;
    long count_move;
    DatabaseReference dref;
    FirebaseAuth mAuth;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Utils.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        String noti = intent.getStringExtra("AlarmAt");
        switch (noti){
            case "morning":
                Notification_morning("morning","วันนี้คุณแม่อย่าลืมนับลูกดิ้นนะคะ",context);
                return;
            case "six":
                final String dateforfirebase6 = new SimpleDateFormat("d+M+yyyy").format(Calendar.getInstance().getTime());
                Log.i("dateforfirebase", dateforfirebase6);
                Log.i("FirebaseUser user", user.getUid());
                dref.child("User").child(user.getUid()).child("Date").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        count_move = dataSnapshot.child(dateforfirebase6).getValue(Integer.class);
                        Log.i("count move ", "is" + count_move);
                        if (count_move >= 4) {
                            Log.i("count move on <10", "is" + count_move);
                           Notification_6hour("ผ่านมา 6 ชั่วโมงแล้ว", "ตอนนี้ลูกของคุณดิ้น " + count_move + " ครั้ง แนะนำให้คุณแม่นับการดิ้นของลูกต่อจนถึงเวลาต่อไป",HomeActivity.class,context);
                            return;
                        } else {
                            Log.i("count move on Another", "is" + count_move);
                            Notification_6hour("ผ่านมา 6 ชั่วโมงแล้ว", "ตอนนี้ลูกของคุณดิ้น " + count_move + " ครั้ง ซึ่งถือว่าอาจเกิดอันตรายหรือมีปัญหากับทารกในครรภ์ แนะนำให้คุณแม่ไปโรงพยาบาลทันทีที่ห้องคลอด รพ.จุฬาลงกรณ์ สภากาชาดไทย อาคารภูมิสิริมังคลานุสรณ์ชั้น 9 โซนเอ หรือ โทร 02-2564000 ต่อ 80921,80922",ContractActicity.class,context);
                            return;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Transaction completed
                        Log.d("dataSnapshot", "postTransaction:onComplete:" + databaseError);
                    }
                });
                return;
            case "twelve":
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
                            Notification_12hour("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + "ครั้ง ซึ่งถือว่าอยู่ในเกณฑ์ปกติค่ะคุณแม่ไม่ต้องกังวลนะคะ",HomeActivity.class,context);
                            return;
                        } else {
                            Log.i("count move on Another", "is" + count_move);
                            Notification_12hour("ผ่านมา 12 ชั่วโมงแล้ว ","ตอนนี้ลูกของคุณดิ้น " + count_move + " ครั้ง ซึ่งถือว่าอาจเกิดอันตรายหรือมีปัญหากับทารกในครรภ์ แนะนำให้คุณแม่ไปโรงพยาบาลทันทีที่ห้องคลอด รพ.จุฬาลงกรณ์ สภากาชาดไทย อาคารภูมิสิริมังคลานุสรณ์ชั้น9 โซนเอ หรือ โทร 02-2564000 ต่อ 80921,80922",ContractActicity.class,context);
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Transaction completed
                        Log.d("dataSnapshot", "postTransaction:onComplete:" + databaseError);
                    }
                });
                
        }


    }
    private void Notification_morning(String aMessage,String artibye,Context context){
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
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
            builder = new NotificationCompat.Builder(context, id);

            intent = new Intent(context,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context ,0, intent, 0);

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

            builder = new NotificationCompat.Builder(context);

            intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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
    private void Notification_6hour(String aMessage, String artibye,Class<?> activity,Context context){
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
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
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
            builder = new NotificationCompat.Builder(context, id);

            intent = new Intent(context, activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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

            builder = new NotificationCompat.Builder(context);

            intent = new Intent(context, activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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
    private void Notification_12hour(String aMessage, String artibye,Class<?> activity,Context context){
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
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
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
            builder = new NotificationCompat.Builder(context, id);

            intent = new Intent(context, activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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

            builder = new NotificationCompat.Builder(context);

            intent = new Intent(context, activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

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
