package com.e.occanotestsidep.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Single {

        private static Single instance;
        private RequestQueue requestQueue;
        private ImageLoader imageLoader;
        private static Context ctx;

        private Single(Context context) {
            ctx = context;
            requestQueue = getRequestQueue();
        }

        public static synchronized Single getInstance(Context context) {
            if (instance == null) {
                instance = new Single(context);
            }
            return instance;
        }

        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }

}
