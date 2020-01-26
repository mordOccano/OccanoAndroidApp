package com.e.occanosidetest.utils

import android.app.Activity
import android.os.Handler

class Run {
    companion object {
        fun after(delay: Long, process: () -> Unit) {
            Handler().postDelayed({
                process()
            }, delay)
        }

        fun stop() {
            Handler().removeCallbacks(Runnable {  })
        }




        fun afterOnMain(delay: Long, activity: Activity, process: () -> Unit) {
            Handler().postDelayed({
                activity.runOnUiThread({
                    Runnable {
                        process()
                    }
                })
            }, delay)
        }
    }

}