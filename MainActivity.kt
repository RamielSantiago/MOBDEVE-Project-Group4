package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Switch.setOnClickListener{
            var intent = Intent(this, WorkAlarms::class.java)
            startActivity(intent)
        }
        binding.track.setOnClickListener{
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{
            var intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }

        createNotificationChannel()



    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "Sched"
            val description = "Scheduler App"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Sched",name,importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel((channel))
        }

    }
}