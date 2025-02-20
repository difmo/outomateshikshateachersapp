package com.saptrishi.outomateshikshateachersapp.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.saptrishi.outomateshikshateachersapp.ChatActivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.LoginActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Config;
import com.saptrishi.outomateshikshateachersapp.utils.NotificationHelper;
import com.saptrishi.outomateshikshateachersapp.utils.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFireBaseInstanceService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;
    private NotificationHelper notificationHelper;
    String TAG="NOtifiacation";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        Log.e("title", Objects.requireNonNull(remoteMessage.getNotification()).getTitle());
//        Log.e("body",remoteMessage.getNotification().getBody());
//        Log.e("body_getData",remoteMessage.getData()+"");
//        Log.e("body_remoteMessage",remoteMessage+"");
        String Title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        if (remoteMessage == null) Log.e("retremoteMessage","no");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        {
            if (remoteMessage.getData().isEmpty())
            { handleNotification1(Title, body);

            }

            else {

                // Check if message contains a data payload.
                if (remoteMessage.getData().size() > 0) {

                    try {
                        JSONObject json = new JSONObject(remoteMessage.getData().toString());
                        handleDataMessage1(json);
                    } catch (Exception e) {
                        Log.e("error", "Exception: " + e.getMessage());
                    }
                }

                // Log.e("else body_remoteMessage",remoteMessage.getData()+"");
                //showNotification(remoteMessage.getData());
            }
        }
        else {
            if (remoteMessage.getNotification() != null) {
                Log.e("inif","ifmessage");
                Log.e("inif","ifmessage");
                String title = remoteMessage.getNotification().getTitle();
                String message = remoteMessage.getNotification().getBody();

                //dbhelper.insertTable3(message , "A" , currentDateTime());
                //Log.e(TAG, "Notification BodyFORE: " + remoteMessage.getNotification().getBody());
                handleNotification(title,message);
                showNotification(title , Html.fromHtml(message).toString());
            }
            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
//                String message = remoteMessage.getData().toString();
//                Log.e("OreoMsg", message);
//                handleNotification("Outomate",message);

                //Log.e(TAG, "Data PayloadOreo: " + remoteMessage.getData().toString());
                try {
                    JSONObject  json = new JSONObject(remoteMessage.getData().toString());
                    handleDataMessage(json);
                } catch (Exception e) {
                    Log.e("erorr", "Exception: " + e.getMessage());
                }
            }
        }
//        if (remoteMessage.getNotification().getBody() != null) {
//            Log.e("FIREBASE", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage);
//        }
    }

    private  NotificationManager notifManager;
    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "com.saptrishi.outomateshikshateachersapp.test"; // default_channel_id
        String title = "Notification"; // Default Channel
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
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            Notification.Builder builder1=new Notification.Builder(context,id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder1.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.notice)
                    // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.notice)
                    // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 4000, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notice)
                .setContentTitle("Vikram")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = null;
        if (notification != null) {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            assert notificationBuilder != null;
            notificationManager.notify(0, notificationBuilder.build());
        }
    }




    private void handleDataMessage(JSONObject json) {
        Log.e("test", "push json oreo: " + json.toString());
        try {
            JSONArray data = json.getJSONArray("obj");
            JSONObject objData = data.getJSONObject(0);
            String title = objData.getString("title");
            String message = objData.getString("body");
//            handleNotification(title, message);
            showNotification(title , Html.fromHtml(message).toString());
            //dbhelper.insertTable3(message , "A" , currentDateTime());
            // boolean isBackground = objData.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
            //JSONObject payload = objData.getJSONObject("payload");
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
            // Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);

            if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationHelper notificationHelper = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationHelper = new NotificationHelper(getApplicationContext());
                }
                notificationHelper.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                notificationHelper.playNotificationSound();

//                 check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
            }
        } catch (JSONException e) {
            Log.e("error", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("error", "Exception: " + e.getMessage());
        }
    }

    private void handleDataMessage1(JSONObject json) {
        //Log.e(TAG, "push json: " + json.toString());
        try {
//            JSONObject data = json.getJSONObject("data");
//            String title = data.getString("title");
//            String message = data.getString("body");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//            dbhelper.insertTable3(message , "A" , currentDateTime());Log.e("chknotification","t1");
            JSONArray data = json.getJSONArray("obj");
            JSONObject objData = data.getJSONObject(0);
            String title = objData.getString("title");
            String message = objData.getString("body") ;
            handleNotification1(title, message);
            showNotification(title,message);
            Log.e("chknotification","t1");


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                Log.e("chknotification","t7");
            } else {
                Log.e("c","t6");
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                notificationUtils.playNotificationSound();
                // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    //showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
            }
        } catch (JSONException e) {
            //Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            //Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    @SuppressWarnings("deprecation")
    private void handleNotification(String title,String message) {
        NotificationHelper notificationHelper = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationHelper = new NotificationHelper(getApplicationContext());
        }
        if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationHelper.getNotification1(title, Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY).toString());
            }
            Log.e("chknotification","t4");
            showNotification(title , Html.fromHtml(message).toString());
        }else
        {
            Log.e("chknotification","t5");
            // If the app is in background, firebase itself handles the notification
            //new NotificationHelper(getApplicationContext()).getNotification1(title, message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationHelper.getNotification1(title , Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY).toString());
            }
            notificationHelper.playNotificationSound();
        }
    }
    private void handleNotification1(String title,String message) {
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        // if (!NotificationHelper.isAppIsInBackground(getApplicationContext())) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());

            notificationUtils.playNotificationSound();
            showNotification(title , Html.fromHtml(message).toString());
            Log.e("chknotification","t2");
        } else {
            // If the app is in background, firebase itself handles the notification
            Log.e("offtest_notice",message);
            showNotification(title , Html.fromHtml(message).toString());
            notificationUtils.playNotificationSound();
            Log.e("chknotification","t3");
        }
    }
    public void showNotification(String title , String message){
        Log.e("test_noticemsg",message);
        Log.e("test_noticetitle",ChatActivity.chatidnumber+"");

        if (("New Reply "+ChatActivity.chatidnumber).equals(message)) {
            Log.e("test_notic",message+"if");
            ChatActivity.featchdatafromvolley(this,ChatActivity.chatidnumber);
        }
        else
        {
            Log.e("test_notic",message+"else");
            String NOTIFICATION_CHANNEL_ID = "com.saptrishi.outomateshikshateachersapp.test";
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setContentText(Html.fromHtml(message))
                    .setSmallIcon(R.drawable.notice);

            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Log.e("chknotification", "t1");
            notificationManager.notify(1, notificationBuilder.build());
        }
    }


//    private void showNotification(String title, String body) {
//
//        Log.e("bodyoffapp",body);
//        String content=null;
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            content= String.valueOf(((Html.fromHtml(body))));
//        }else {
//            content= String.valueOf(((Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY))));
//        }
//        Log.e("bodyoffappcontent",content+"");
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANNEL_ID="com.saptrishi.outomateshiksha.test";
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.setDescription("vikram's notice");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.BLUE);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//            notificationManager.createNotificationChannel(notificationChannel);
//
//        }
//
//
//
//        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
//
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.notice)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setContentInfo("Info");
//
//        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
//    }

    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        SharedPreferences.Editor edit=getSharedPreferences("ShikshaContainer1",MODE_PRIVATE).edit();
        edit.putString("token12",s);
        edit.apply();

        Log.e("token12",s);

    }
}
