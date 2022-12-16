package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.santiago.ram.mco.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Alarms>
    private var setAlarm: SetAlarm? = null
//    private lateinit var alarmname: MutableList<String>
//    private lateinit var alarmtime: MutableList<String>
    private lateinit var alarmname:ArrayList<String>
    private lateinit var alarmtime:ArrayList<String>
    private var SetAlarmsAdapter:RecyclerView.Adapter<AlarmsAdapter.viewHold>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newArrayList = ArrayList()
        binding.track.setOnClickListener {
            var intent = Intent(this, AppTracking::class.java)
            startActivity(intent)
        }
        binding.Mic.setOnClickListener {
            var intent = Intent(this, TTS::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener {
            var intent = Intent(this, SetAlarm::class.java)
            startActivity(intent)
        }
        binding.users.setOnClickListener {
            var intent = Intent(this, Friends::class.java)
            startActivity(intent)
        }
        binding.ytapi.setOnClickListener {
            var intent = Intent(this, exercise::class.java)
            startActivity(intent)
        }
        binding.tologin.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.toregister.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        val extras = intent.extras
        if(extras != null){
            var setalarmname: String? = extras.getString("alarmname", "")
            var setalarmtime: String? = extras.getString("alarmtime", "")

            newArrayList.add(Alarms(setalarmname.toString(),setalarmtime.toString()))
            binding.RecyclerView.adapter?.notifyItemInserted(newArrayList.size-1)
        } else {
            Toast.makeText(applicationContext, "An Error Occurred", Toast.LENGTH_SHORT).show()
        }

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        SetAlarmsAdapter = AlarmsAdapter(newArrayList,this)
        binding.RecyclerView.adapter = SetAlarmsAdapter

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
}


//
////        alarmname = arrayListOf()
////        alarmtime = arrayListOf()
////
////        val alarmnamelist = alarmname.toMutableList()
////        val alarmtimelist = alarmtime.toMutableList()
////
////        alarmnamelist.add("work")
////        alarmtimelist.add("9:00")
////
////        alarmname = alarmnamelist.toTypedArray().toMutableList()
////        alarmtime = alarmtimelist.toTypedArray().toMutableList()
//
//
//        alarmname = ArrayList()
//        alarmtime = ArrayList()
//        val extras = intent.extras
//        if(extras != null){
//            var setalarmname: String? = extras.getString("alarmname", "")
//            alarmname.add(setalarmname.toString())
//            var setalarmtime: String? = extras.getString("alarmname", "")
//            alarmtime.add(setalarmtime.toString())
//
//            newArrayList.add(Alarms(setalarmname.toString(),setalarmtime.toString()))
//            binding.RecyclerView.adapter?.notifyItemInserted(newArrayList.size-1)
//        } else {
//            Toast.makeText(applicationContext, "An Error Occurred", Toast.LENGTH_SHORT).show()
//        }
//
////        val bundle = intent.extras
////
////        if (bundle != null){
////
////            alarmname.plus("${bundle.getString("alarmname")}")
////            alarmtime.plus("${bundle.getString("alarmtime")}")}
//
//        newRecyclerView = findViewById(R.id.RecyclerView)
//        newRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        newArrayList = arrayListOf<Alarms>()
//           // getAlarmData()
//        for (i in alarmname.indices) {
//            val alarm = Alarms(alarmname[i], alarmtime[i])
//            newArrayList.add(alarm)
//        }
//        newRecyclerView.adapter = AlarmsAdapter(newArrayList)