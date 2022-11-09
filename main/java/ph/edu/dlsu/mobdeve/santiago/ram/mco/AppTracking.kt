package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityAppTrackingBinding

class AppTracking : AppCompatActivity() {
    private lateinit var binding: ActivityAppTrackingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.track.setOnClickListener{
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
    }
}