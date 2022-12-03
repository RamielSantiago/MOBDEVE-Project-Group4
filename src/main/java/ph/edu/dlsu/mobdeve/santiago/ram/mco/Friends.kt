package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityFriendsBinding

class Friends : AppCompatActivity() {
    private lateinit var binding:ActivityFriendsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarms.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener {
            var intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }
        binding.track.setOnClickListener {
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.ytapi.setOnClickListener {
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
    }
}