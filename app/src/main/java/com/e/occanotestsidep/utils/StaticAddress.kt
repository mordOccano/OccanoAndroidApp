package com.e.occanosidetest.utils

import android.app.Activity
import android.content.Context
import com.e.occanotestsidep.R

class StaticAddress {
    companion object {
//        var Ip = "192.168.43.177"
//            @JvmStatic
//            lateinit var Ip: String
//            @JvmStatic var URL_LOCAL_SHIP_REPORT : String
//            @JvmStatic var URL_LOCAL_SHIP_CALIBRATION : String
//            @JvmStatic var URL_LOCAL_SHIP_REGISTER : String
//
//
//            init {
////                     Ip = "office.occano.io:4000"
//
//
//                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//                    val defaultValue = "office.occano.io:4000"
//                    val Ip = sharedPref.getString("ip", defaultValue)
//
//
//             URL_LOCAL_SHIP_REPORT = "http://"+Ip+"/report/"
//             URL_LOCAL_SHIP_CALIBRATION = "http://"+Ip+"/manual_correction/"
//             URL_LOCAL_SHIP_REGISTER = "http://"+Ip+"/register/"
//
//            }


//            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//            with (sharedPref.edit()) {
//                    putInt(getString(R.string.saved_high_score_key), newHighScore)
//                    commit()
//            }
//
//


        //max value gauge
        var max_torque_gauge = 400f
        var max_bmep_gauge = 30f
        var max_break_power_gauge = 100000f
        var max_comp_pres_gauge = 300f
        var max_engine_speed_gauge = 100f
        var max_exhaust_gauge = 500f
        var max_firing_pres_gauge = 300f
        var max_fuel_gauge = 550f
        var max_imep_gauge = 30f
        var max_injec_gauge = 4f
        var max_load_gauge = 150f
        var max_scave_gauge = 10f
    }

}

//for wherever korti
//        fun URL_LOCAL_SHIP_REPORT() : String = "http://"+Ip+":4001/report/"
//        fun URL_LOCAL_SHIP_CALIBRATION() : String = "http://"+Ip+":4001/manual_correction/"
//        fun URL_LOCAL_SHIP_REGISTER() : String = "http://"+Ip+":4001/register/"

//        fun URL_LOCAL_SHIP_REPORT() : String = "http://"+Ip+"/report/"
//        fun URL_LOCAL_SHIP_CALIBRATION() : String = "http://"+Ip+"/manual_correction/"
//        fun URL_LOCAL_SHIP_REGISTER() : String = "http://"+Ip+"/register/"
