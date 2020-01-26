package com.e.occanotestsidep.ui.main.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.occanosidetest.models.Status
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.utils.Utility
import kotlinx.android.synthetic.main.fragment_status.*
import kotlin.random.Random

class StatusFragment :Fragment(), AckStatusAdapter.Interaction,NewStatusAdapter.Interaction, View.OnClickListener {

    var statusesList = ArrayList<Status>()
    lateinit var statusNewRVAdapter: NewStatusAdapter
    lateinit var statusAckRVAdapter: AckStatusAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(view)
        initNewRv()
        initAckRv()
        initRvList()

        this.statusNewRVAdapter.submitList(statusesList)
        this.statusAckRVAdapter.submitList(statusesList)
    }

    private fun setListeners(v:View) {
        v.findViewById<ImageButton>(R.id.btn_status_to_dashboard).setOnClickListener(this)
        v.findViewById<ImageButton>(R.id.btn_status_to_log).setOnClickListener(this)
        v.findViewById<ImageButton>(R.id.btn_status_current).setOnClickListener(this)
    }

    private fun initRvList() {
        for (i in 1..20){
            statusesList.add(Status(i,"Main Title", "sub title", "subTite","sub moria contenta sub more content sub moria contenta sub more content",
                (Random.nextInt(1,100))%3,(i+1)%2,0, Utility.getCurrentTimeStamp().plus(i)))
        }
    }

    private fun initAckRv() {
        status_rv_acknowledge_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val  topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            statusAckRVAdapter = AckStatusAdapter(this@StatusFragment)
            adapter = statusAckRVAdapter
        }
    }

    private fun initNewRv() {
        status_rv_new_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            statusNewRVAdapter = NewStatusAdapter(this@StatusFragment)
            adapter = statusNewRVAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Status) {
        statusesList.get(position).setKindOfAcknowledge(0)
//        this.statusNewRVAdapter.submitList(statusesList)
//        this.statusAckRVAdapter.submitList(statusesList)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_status_to_log->{
                v.findNavController().navigate(R.id.action_statusFragment_to_logFragment)
            }

            R.id.btn_status_to_dashboard->{
                v.findNavController().navigate(R.id.action_statusFragment_to_dashFragment)
            }
            else->{
                Toast.makeText(this.context,"try again", Toast.LENGTH_SHORT).show()
            }
        }    }
}