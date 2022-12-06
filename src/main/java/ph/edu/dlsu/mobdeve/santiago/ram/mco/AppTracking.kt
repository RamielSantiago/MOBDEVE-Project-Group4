package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
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
    private fun isAppInstalled(packageName: String, context: Context): Boolean {
        return try {
            val packageManager = context.packageManager
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    private fun showUsage(){
        val usm: UsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        val startTime = calendar.timeInMillis - 86400000

        val usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
        val isEmpty = usageStatsList.isEmpty()
        if (isEmpty) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        var installed = booleanArrayOf(isAppInstalled("com.facebook.orca", applicationContext),
                                       isAppInstalled("com.facebook.katana", applicationContext),
                                       isAppInstalled("com.twitter.android", applicationContext),
                                       isAppInstalled("com.snapchat.android", applicationContext),
                                       isAppInstalled("com.discord", applicationContext))
        if (!installed[0]) {
            binding.fbmTime.text = "Application not Installed"
        }
        if (!installed[1]) {
            binding.fbTime.text = "Application not Installed"
        }
        if (!installed[2]){
            binding.tweetTime.text = "Application not Installed"
        }
        if (!installed[3]) {
            binding.scTime.text = "Application not Installed"
        }
        if (!installed[4]) {
            binding.discordTime.text = "Application not Installed"
        }

        for(i in 0..usageStatsList.size-1){
            if(usageStatsList[i].packageName.equals("com.facebook.orca") && installed[0]){
                SAs.MessengerUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.fbmTime.text = SAs.MessengerUsage
            }
            if(usageStatsList[i].packageName.equals("com.facebook.katana") && installed[1]){
                SAs.FacebookUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.fbTime.text = SAs.FacebookUsage
            }
            if(usageStatsList[i].packageName.equals("com.twitter.android") && installed[2]){
                SAs.TwitterUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.tweetTime.text = SAs.TwitterUsage
            }
            if(usageStatsList[i].packageName.equals("com.snapchat.android") && installed[3]){
                SAs.SnapchatUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.scTime.text = SAs.SnapchatUsage
            }
            if(usageStatsList[i].packageName.equals("com.discord") && installed[4]){
                SAs.DiscordUsage = convertTime(usageStatsList[i].totalTimeInForeground)
                binding.discordTime.text = SAs.SnapchatUsage
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
        showUsage()
        binding.alarms.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{
            var intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener{
            var intent = Intent(this, TTS::class.java)
            startActivity(intent)
        }
        binding.users.setOnClickListener{
            var intent = Intent(this, Friends::class.java)
            startActivity((intent))
        }
        binding.ytapi.setOnClickListener{
            var intent = Intent(this, exercise::class.java)
            startActivity((intent))
        }
        sp = getSharedPreferences("SAVE_DATA", Context.MODE_PRIVATE)
        editor = sp.edit()
        SAs.saveData(sp, editor)
    }

    override fun onPause() {
        super.onPause()
        showUsage()
        SAs.saveData(sp, editor)
    }

    override fun onResume() {
        super.onResume()
        showUsage()
    }
}
