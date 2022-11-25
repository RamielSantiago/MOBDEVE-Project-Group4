package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityMainBinding
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityWorkAlarmsBinding

class WorkAlarms : AppCompatActivity() {
    private lateinit var binding: ActivityWorkAlarmsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkAlarmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Switch.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
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