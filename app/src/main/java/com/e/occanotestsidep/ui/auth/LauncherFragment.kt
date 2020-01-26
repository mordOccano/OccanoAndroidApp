package com.e.occanotestsidep.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanotestsidep.R
import kotlinx.android.synthetic.main.fragment_launcher.*

class LauncherFragment:Fragment(), View.OnClickListener{
    lateinit var navController: NavController
     var ip:String? = "office.occano.io:4000"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher,container,false)
    }

    companion object{
        fun newInstance(): LauncherFragment {
            return LauncherFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setListeners(view)


    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.start_btn).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_set_ip).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_open_set_ip).setOnClickListener(this)
        view.findViewById<View>(R.id.focusable_view).requestFocus()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.start_btn -> {
                start_btn.isClickable = false
                findNavController().navigate(R.id.action_launcherFragment_to_settingsFragment)
            }
            R.id.btn_open_set_ip -> {
                et_set_ip.visibility = View.VISIBLE
                btn_open_set_ip.visibility = View.GONE
                btn_set_ip.visibility = View.VISIBLE
            }
            R.id.btn_set_ip -> {
                btn_set_ip.isClickable = false
                start_btn.isClickable = false
                if (et_set_ip.text!=null){
//                    StaticAddress.Ip = et_set_ip.text.toString(
                    saveAddressProperties()
                    Toast.makeText(this.context, " הכתובת $ip " + " הוזנה ", Toast.LENGTH_SHORT).show()
                    start_btn.isClickable = true
                }
            }
            else -> {
                btn_set_ip.isClickable = false
            }
        }
    }

    fun saveAddressProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString("ip", et_set_ip.text.toString())
                    commit()
            }

        getAddress()
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

//    var Ip = "192.168.43.177"
//    @JvmStatic
//    lateinit var Ip: String
//    @JvmStatic var URL_LOCAL_SHIP_REPORT : String
//    @JvmStatic var URL_LOCAL_SHIP_CALIBRATION : String
//    @JvmStatic var URL_LOCAL_SHIP_REGISTER : String
//
//
//    init {
////                     Ip = "office.occano.io:4000"
//
//        val sharedPref = ?.getPreferences(Context.MODE_PRIVATE) ?: return
//        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
//
//        if (sharedPref.getInt("ip", defaultValue)!=null){
//            Ip = sharedPref.getInt("ip", defaultValue)
//        }
//        else{
//            Ip = "office.occano.io:4000"
//        }
//
//
//        URL_LOCAL_SHIP_REPORT = "http://"+Ip+"/report/"
//        URL_LOCAL_SHIP_CALIBRATION = "http://"+Ip+"/manual_correction/"
//        URL_LOCAL_SHIP_REGISTER = "http://"+Ip+"/register/"
//
//    }
//
//
////            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
////            with (sharedPref.edit()) {
////                    putInt(getString(R.string.saved_high_score_key), newHighScore)
////                    commit()
////            }
////
////


}