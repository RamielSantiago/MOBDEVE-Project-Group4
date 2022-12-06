package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Firebase.auth.currentUser
        binding.track.setOnClickListener{
            val intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            val intent = Intent(this, TTS::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{
            val intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }
        binding.users.setOnClickListener{
            val intent = Intent(this, Friends::class.java)
            startActivity(intent)
        }
        binding.ytapi.setOnClickListener{
            val intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.tologin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.toregister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.tologout.setOnClickListener{
            if(user != null){
                Firebase.auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        if (user == null) {
            binding.Mic.isVisible = false
            binding.track.isVisible = false
            binding.add.isVisible = false
            binding.users.isVisible = false
            binding.ytapi.isVisible = false
            binding.tologout.isVisible = false

            binding.Mic.isEnabled = false
            binding.track.isEnabled = false
            binding.add.isEnabled = false
            binding.users.isEnabled = false
            binding.ytapi.isEnabled = false
            binding.tologout.isEnabled = false
        } else {
            binding.toregister.isVisible = false
            binding.tologin.isVisible = false

            binding.toregister.isEnabled = false
            binding.tologin.isEnabled = false
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

    override fun onResume() {
        super.onResume()
        val user = Firebase.auth.currentUser
        if (user == null) {
            binding.Mic.isVisible = false
            binding.track.isVisible = false
            binding.add.isVisible = false
            binding.users.isVisible = false
            binding.tologout.isVisible = false

            binding.Mic.isEnabled = false
            binding.track.isEnabled = false
            binding.add.isEnabled = false
            binding.users.isEnabled = false
            binding.ytapi.isEnabled = false
            binding.tologout.isEnabled = false
        } else {
            binding.toregister.isVisible = false
            binding.tologin.isVisible = false

            binding.toregister.isEnabled = false
            binding.tologin.isEnabled = false
        }
    }
}