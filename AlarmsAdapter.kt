package ph.edu.dlsu.mobdeve.santiago.ram.mco

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

    class AlarmsAdapter (private val Alarms : ArrayList<Alarms>, context : Context) :
    RecyclerView.Adapter<AlarmsAdapter.viewHold>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):viewHold {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.alarm_display,
            parent, false)
            return viewHold(itemView)
        }

    override fun onBindViewHolder(holder: viewHold, position: Int) {
        val currentItem = Alarms[position]
        holder.alarmname.text = currentItem.alarmname
        holder.alarmtime.text = currentItem.alarmtime
    }

    override fun getItemCount(): Int {
        return Alarms.size
    }
    inner class viewHold(itemView : View) : RecyclerView.ViewHolder(itemView){
        val alarmname : TextView
        val alarmtime : TextView
        init {
            alarmname = itemView.findViewById(R.id.alarmname)
            alarmtime = itemView.findViewById(R.id.alarmtime)
        }
    }
    }

