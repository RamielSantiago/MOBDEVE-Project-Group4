package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityTtsBinding
import java.util.*


class TTS : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, TextToSpeech.OnInitListener{
    private lateinit var binding: ActivityTtsBinding
    private lateinit var tts: TextToSpeech
    private lateinit var cal : Calendar
    var yr = 0
    var mth = 0
    var day = 0
    var hr = 0
    var min = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTtsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
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
        binding.users.setOnClickListener{
            var intent = Intent(this, Friends::class.java)
            startActivity((intent))
        }
        binding.ytapi.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.setdate.setOnClickListener(){
            cal = Calendar.getInstance()
            DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        yr = year
        mth = month
        day = dayOfMonth
        TimePickerDialog(this, this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        hr = hourOfDay
        min = minute
        var currcal : Calendar = Calendar.getInstance()
        cal.set(yr, mth, day, hr, min, 0)
        if(cal.timeInMillis - currcal.timeInMillis > 0){
            setTTSAlarm()
        } else {
            Toast.makeText(this, "Invalid Date or Time", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTTSAlarm() {
        var ttsmessage = ""
        ttsmessage = if(hr > 12){
            "Alarm set for $hr $min pm"
        } else {
            "Alarm set for $hr $min am"
        }
        tts!!.speak(ttsmessage, TextToSpeech.QUEUE_FLUSH, null, "")

//        val intent = Intent(baseContext, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(baseContext, 1, intent, 0)
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            val result = tts.setLanguage(Locale.ENGLISH)

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