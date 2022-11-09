package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityExerciseBinding

class exercise : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val play = binding.player
        val mediaControl =  MediaController(this)
        mediaControl.setAnchorView(play)
        mediaControl.setMediaPlayer(play)
        play.setMediaController(mediaControl)

        binding.track.setOnClickListener{
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.submit.setOnClickListener{
            var Input = binding.urlBox.text.toString()
            Toast.makeText(applicationContext, Input, Toast.LENGTH_SHORT).show()
            if(Input.trim().isNotEmpty()){
                val linkuri = Uri.parse(Input)
                play.setVideoURI(linkuri)
                play.requestFocus()
                play.start()
            } else {
                Toast.makeText(applicationContext, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
        }
    }
}