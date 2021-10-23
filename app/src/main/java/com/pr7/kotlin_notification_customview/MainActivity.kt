package com.pr7.kotlin_notification_customview

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "CHANNEL_1"
    val NOTIFICATION_ID = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            createNotificationChannel()
            val remoteView_normal_layout= RemoteViews(packageName,R.layout.custom_normal);
            val remoteView_expended_layout= RemoteViews(packageName,R.layout.custom_expended);


            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


            val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID)
            notificationCompat.setSmallIcon(R.drawable.ic_baseline_message_24)
//            notificationCompat.setContentTitle("Simple Notification")
//            notificationCompat.setContentText("This is a Simple Notification")
            notificationCompat.setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationCompat.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            notificationCompat.setCustomContentView(remoteView_normal_layout)
            notificationCompat.setCustomBigContentView(remoteView_expended_layout)

            remoteView_normal_layout.setTextViewText(R.id.textView,"Lorem Ipsum is simply dummy text axaxaxaxa")
            remoteView_expended_layout.setOnClickPendingIntent(R.id.imageView,pendingIntent)





            val notificationManagerCompat = NotificationManagerCompat.from(this)
            notificationManagerCompat.notify(NOTIFICATION_ID, notificationCompat.build())
        }


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Personal Notification"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

}