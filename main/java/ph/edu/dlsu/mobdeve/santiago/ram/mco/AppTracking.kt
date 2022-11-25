package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityAppTrackingBinding
import java.text.SimpleDateFormat
import java.util.*


class AppTracking : AppCompatActivity() {
    private lateinit var binding: ActivityAppTrackingBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    val SAs: supportedApps = supportedApps()

    class supportedApps(){
        var DiscordUsage: String = ""
        var MessengerUsage: String = ""
        var FacebookUsage: String = ""
        var SnapchatUsage: String = ""
        var TwitterUsage: String = ""
        var MessTimeMilis: Long = 0
        var SCTimeMilis: Long = 0
        var TwtTimeMilis: Long = 0
        var FBTimeMilis: Long = 0

        fun saveData(sp: SharedPreferences, ed: SharedPreferences.Editor){
            ed.putString(MessengerUsage, MessengerUsage)
            ed.putString(FacebookUsage, FacebookUsage)
            ed.putString(TwitterUsage, TwitterUsage)
            ed.putString(SnapchatUsage, SnapchatUsage)
            ed.apply()
        }
        fun loadData(sp: SharedPreferences, ed: SharedPreferences.Editor){

        }
    }

    private fun showUsage(){
        val usm: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        val startTime = calendar.timeInMillis - 86400000

        val usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
        var stats : String = ""
        for(i in 0..usageStatsList.size-1){
            if(usageStatsList[i].packageName.equals("com.facebook.orca")){
                SAs.MessTimeMilis = usageStatsList[i].totalTimeInForeground
                SAs.MessengerUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.fbmTime.text = SAs.MessengerUsage
            }
            if(usageStatsList[i].packageName.equals("com.facebook.katana")){
                SAs.FacebookUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.fbTime.text = SAs.FacebookUsage
            }
            if(usageStatsList[i].packageName.equals("com.twitter.android")){
                SAs.TwitterUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.tweetTime.text = SAs.TwitterUsage
            }
            if(usageStatsList[i].packageName.equals("com.snapchat.android")){
                SAs.SnapchatUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.scTime.text = SAs.SnapchatUsage
            }
        }
    }
    private fun convertTime(stat: Long): String {
        var date: Date = Date(stat)
        var format: SimpleDateFormat = SimpleDateFormat("mm:ss", Locale.ENGLISH)
        return format.format(date)
    }
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
        sp = getSharedPreferences("SAVE_DATA", Context.MODE_PRIVATE)
        editor = sp.edit()

        showUsage()
        SAs.saveData(sp, editor)
    }

    override fun onPause() {
        super.onPause()
        SAs.saveData(sp, editor)
    }

    override fun onResume() {
        super.onResume()
        showUsage()
    }
}
