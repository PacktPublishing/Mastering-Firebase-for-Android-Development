package packt.com.cloudmessaging

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        //Fetch the push token
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

    }

    private fun sendRegistrationToServer(token: String) {

          }

    companion object {
        private const val TAG = "MyFirebaseIIDService"
    }
}
