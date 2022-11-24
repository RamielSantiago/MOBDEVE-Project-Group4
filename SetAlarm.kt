package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivitySetAlarmBinding
import java.util.Calendar


class SetAlarm : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var  alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.AlarmFriends.setOnClickListener{
            var intent = Intent(this, AlarmFriends::class.java)
            startActivity(intent)
        }

        binding.SetAlarmButton.setOnClickListener {
            setAlarm()

        }

        binding.SetTime.setOnClickListener {
            showTimePicker()
        }

        binding

    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )

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
        }


    }
}