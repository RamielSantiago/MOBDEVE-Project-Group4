package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivitySetAlarmBinding
import java.util.*


class SetAlarm : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var  alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmname: String
    private lateinit var alarmtime: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.RECEIVE_SMS,android.Manifest.permission.SEND_SMS),111)
        }else
            receiveMsg()

        binding.SetAlarmButton.setOnClickListener {
            setAlarm()
        }

        binding.SetTime.setOnClickListener {
            showTimePicker()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            receiveMsg()
    }

    private fun receiveMsg() {
            var br = object: BroadcastReceiver(){
                override fun onReceive(p0: Context?, p1: Intent?) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        for(sms: SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                        }
                    }
                }

            }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    private fun showCalendar() {

    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)


        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )

        alarmname = findViewById<EditText>(R.id.AlarmNameInput).text.toString()
        var smsinput = findViewById<EditText>(R.id.smsinput)
        var sms: SmsManager = SmsManager.getDefault()
        sms.sendTextMessage(
            smsinput.text.toString(),
            "ME",
            "You Have Received An Alarm \n Alarm Name:" + alarmname + "\n Alarm Time:" + alarmtime,
            null,
            null
        )



        val a = Intent(applicationContext, MainActivity::class.java)
        a.putExtra("alarmname", alarmname)
        a.putExtra("alarmtime", alarmtime)
        startActivity(a)

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()

    }

    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "Sched")

        picker.addOnPositiveButtonClickListener{
            if (picker.hour > 12){

                binding.SetTime.text =
                    String.format("%02d", picker.hour - 12) + ":" + String.format(
                        "%02d",
                        picker.minute
                    ) + "PM"
            }else{
                binding.SetTime.text=
                    String.format("%02d", picker.hour) + ":" + String.format(
                        "%02d",
                        picker.minute
                    ) + "AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            alarmtime = picker.hour.toString() + ":" + picker.minute.toString()
    }
}

}