package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener
import com.google.android.youtube.player.YouTubePlayerView
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityExerciseBinding


class exercise : YouTubeBaseActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var ytPlayerView : YouTubePlayerView
    private lateinit var ytListener: YouTubePlayer.OnInitializedListener
    private val API_KEY = "AIzaSyCUNcyfqargK78W-RkhfjQDn8NE1WheV4Y"

    fun getID(URL:String): String{
        val https: String = "https://"
        val linkType1: String = "www.youtube.com/watch?v="
        val linkType2: String = "youtu.be/"
        var newURL = URL
        if(newURL.contains(https)){
            if(newURL.contains(linkType1)){
                newURL = URL.subSequence(32, 43).toString()
            } else if (newURL.contains(linkType2)){
                newURL = URL.subSequence(17, 28).toString()
            } else {
                return "Invalid URL"
            }
        }
        else {
            if(newURL.contains(linkType1)){
                newURL = URL.subSequence(24, 35).toString()
            } else {
                return "Invalid URL"
            }
        }
        return newURL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ytPlayerView = binding.player
        var ytPlayer : YouTubePlayer?
        binding.track.setOnClickListener{
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.submit.setOnClickListener{
            var videoID: String = getID(binding.urlBox.text.toString())
            binding.urlBox.setText("")
            if(videoID == "Invalid URL"){
                Toast.makeText(applicationContext, videoID, Toast.LENGTH_SHORT).show()
            }
            else {
                ytListener = object: YouTubePlayer.OnInitializedListener{
                    override fun onInitializationSuccess(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubePlayer?,
                        p2: Boolean
                    ) {
                        p1?.setShowFullscreenButton(false)
                        p1?.loadVideo(videoID)
                    }
                    override fun onInitializationFailure(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubeInitializationResult?
                    ) {
                        Toast.makeText(applicationContext, "Video could not be played.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
