package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
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
    }
}