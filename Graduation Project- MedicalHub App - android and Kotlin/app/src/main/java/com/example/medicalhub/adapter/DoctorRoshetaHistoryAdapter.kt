package com.example.medicalhub.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R

class DoctorRoshetaHistoryAdapter :
    RecyclerView.Adapter<DoctorRoshetaHistoryAdapter.MyViewHolder>() {
    private var userIDList = mutableListOf<String>()
    private var dateList = mutableListOf<String>()
    private var diagnosisList = mutableListOf<String>()
    private var medicinList = mutableListOf<String>()
    private var analysisList = mutableListOf<String>()
    private var xRayList = mutableListOf<String>()
    private var addNotesList = mutableListOf<String>()

    private var filteredUserIDList = mutableListOf<String>()
    private var filteredDateList = mutableListOf<String>()
    private var filteredDiagnosisList = mutableListOf<String>()
    private var filteredMedicineList = mutableListOf<String>()
    private var filteredAnalysisList = mutableListOf<String>()
    private var filteredXRayList = mutableListOf<String>()
    private var filteredAddNotesList = mutableListOf<String>()

    init {
        // Initially, show all items
        filteredUserIDList = userIDList.toMutableList()
        filteredDateList = dateList.toMutableList()
        filteredDiagnosisList = diagnosisList.toMutableList()
        filteredMedicineList = medicinList.toMutableList()
        filteredAnalysisList = analysisList.toMutableList()
        filteredXRayList = xRayList.toMutableList()
        filteredAddNotesList = addNotesList.toMutableList()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ID: TextView = itemView.findViewById(R.id.ID)
        val Date: TextView = itemView.findViewById(R.id.Date)
        val hiddenLayout: LinearLayout = itemView.findViewById(R.id.hiddenLayout)
        val row: LinearLayout = itemView.findViewById(R.id.row)
        val hiddenText: TextView = itemView.findViewById(R.id.hiddenText)

        init {
            row.setOnClickListener {
                if (hiddenLayout.visibility == View.GONE) {
                    hiddenLayout.visibility = View.VISIBLE
                } else {
                    hiddenLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.dr_rosheta_history,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredUserIDList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ID.text = "ID: " + filteredUserIDList[position]
        holder.Date.text = "Date: " + filteredDateList[position]

        val diagnosis = filteredDiagnosisList[position]
        val medicin = filteredMedicineList[position]
        val analysis = filteredAnalysisList[position]
        val xRay = filteredXRayList[position]
        val addNotes = filteredAddNotesList[position]

        val formattedText = """
            <b>Diagnosis:</b> $diagnosis<br/>
            <b>Medicin:</b> $medicin<br/>
            <<b>Analysis:</b> $analysis<br/>
            <<b>XRay:</b> $xRay<br/>
            <<b>AddNotes:</b> $addNotes
        
        """.trimIndent()

        holder.hiddenText.text = Html.fromHtml(formattedText, Html.FROM_HTML_MODE_COMPACT)
    }

    fun setData(
        userIDList: List<String>,
        dateList: List<String>,
        diagnosisList: List<String>,
        medicineList: List<String>,
        analysisList: List<String>,
        xRayList: List<String>,
        addNotesList: List<String>
    ) {
        this.userIDList = userIDList.toMutableList()
        this.dateList = dateList.toMutableList()
        this.diagnosisList = diagnosisList.toMutableList()
        this.medicinList = medicineList.toMutableList()
        this.analysisList = analysisList.toMutableList()
        this.xRayList = xRayList.toMutableList()
        this.addNotesList = addNotesList.toMutableList()

        // Initially show all items in the filtered list
        filter("")
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.toLowerCase()

        filteredUserIDList.clear()
        filteredDateList.clear()
        filteredDiagnosisList.clear()
        filteredMedicineList.clear()
        filteredAnalysisList.clear()
        filteredXRayList.clear()
        filteredAddNotesList.clear()

        for (i in userIDList.indices) {
            if (userIDList[i].toLowerCase().contains(lowerCaseQuery)) {
                filteredUserIDList.add(userIDList[i])
                filteredDateList.add(dateList[i])
                filteredDiagnosisList.add(diagnosisList[i])
                filteredMedicineList.add(medicinList[i])
                filteredAnalysisList.add(analysisList[i])
                filteredXRayList.add(xRayList[i])
                filteredAddNotesList.add(addNotesList[i])
            }
        }
        notifyDataSetChanged()
    }

}