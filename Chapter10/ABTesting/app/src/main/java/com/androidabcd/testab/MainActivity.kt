package com.androidabcd.testab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import android.view.View
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

	private var mFirebaseAnalytics: FirebaseAnalytics? = null
	private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null
	private var experiment1_variant: String? = null


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
		mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

		val configSettings = FirebaseRemoteConfigSettings.Builder()
				.setDeveloperModeEnabled(BuildConfig.DEBUG)
				.build()
		mFirebaseRemoteConfig?.setConfigSettings(configSettings)
		mFirebaseRemoteConfig?.setDefaults(R.xml.remoteconfig)
		fetchData()
	}


	private fun fetchData() {
		var cacheExpiration: Long = 3600 // 1 hour in seconds.
		if (mFirebaseRemoteConfig?.info?.configSettings?.isDeveloperModeEnabled == true) {
			cacheExpiration = 0
		}
		Log.d("IID_TOKEN", FirebaseInstanceId.getInstance().token);
		mFirebaseRemoteConfig?.fetch(cacheExpiration)?.addOnCompleteListener { task ->
			if (task.isSuccessful) {
				mFirebaseRemoteConfig?.activateFetched()
				experiment1_variant = mFirebaseRemoteConfig?.getString("onboarding")
				Log.d("welcome",""+experiment1_variant)
				mFirebaseAnalytics?.setUserProperty("MyExperiment", experiment1_variant)
				if (experiment1_variant?.toLowerCase()?.contentEquals("bottom") == false) {
					bubble_top?.visibility = View.VISIBLE
					Toast.makeText(this, ""+experiment1_variant,Toast.LENGTH_LONG).show()
				} else {
					Toast.makeText(this, ""+experiment1_variant,Toast.LENGTH_LONG).show()
					bubble_bottom?.visibility = View.VISIBLE
				}
			}
		}
	}
}
