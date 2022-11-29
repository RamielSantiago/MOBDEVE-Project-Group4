package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityTtsBinding
import java.text.SimpleDateFormat
import java.util.*


class TTS : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, TextToSpeech.OnInitListener{
    private lateinit var binding: ActivityTtsBinding
    private lateinit var tts: TextToSpeech
    private lateinit var cal : Calendar

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minutes = 0

    var days = 0
    var mth = 0
    var yrs = 0
    var hrs = 0
    var mins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTtsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.alarms.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{
            var intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }
        binding.track.setOnClickListener{
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.ytapi.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.setdate.setOnClickListener(){
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
        tts = TextToSpeech(this, this)
    }

    private fun getDateTimeCalendar(){
        cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minutes = cal.get(Calendar.DAY_OF_MONTH)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        days = dayOfMonth
        mth = month
        yrs = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minutes, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        hrs = hourOfDay
        mins = minute

        var newcal : Calendar = Calendar.getInstance()
//        newcal = Calendar.getInstance()
        newcal.set(yrs, mth, days, hrs, mins)
        if(newcal.timeInMillis - cal.timeInMillis > 0){
            setTTSAlarm(newcal.timeInMillis)
        } else {
            Toast.makeText(this, "Invalid Date or Time", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTTSAlarm(inputTime: Long) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, inputTime, pendingIntent)
        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()

        var ttsmessage = ""
        ttsmessage = if(hrs > 12){
            "Alarm set for $hrs $mins pm"
        } else {
            "Alarm set for $hrs $mins am"
        }
        tts!!.speak(ttsmessage, TextToSpeech.QUEUE_ADD, null, "")
    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            val result = tts.setLanguage(Locale.UK)

            if(result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this, "Missing Data", Toast.LENGTH_SHORT).show()
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    override fun onDestroy() {
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}