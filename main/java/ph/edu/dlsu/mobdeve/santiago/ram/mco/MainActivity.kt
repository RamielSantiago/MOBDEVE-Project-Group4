package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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