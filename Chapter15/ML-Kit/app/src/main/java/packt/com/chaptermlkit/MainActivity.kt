package packt.com.chaptermlkit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.cloud.text.FirebaseVisionCloudText

class MainActivity : AppCompatActivity() {

    internal lateinit var bitmap: Bitmap
    private val CODE = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this);
        snapBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CODE)
        }
        detectBtn.setOnClickListener {
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().visionTextDetector
            detector.detectInImage(image).addOnSuccessListener { firebaseVisionText ->
                val textdata = firebaseVisionText.blocks
                if (textdata.size > 0) {
                    var displaydata = ""
                    for (myblock in firebaseVisionText.blocks) {
                        displaydata = displaydata + myblock.text + "\n"
                    }
                    textView.text = displaydata
                } else {
                    Toast.makeText(this@MainActivity, "Failed to detect any text.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { Toast.makeText(this@MainActivity, "No text detected in the given image.", Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            val bundle = data.extras
            bitmap = bundle?.get("data") as Bitmap // the default key is 'data'
            imageView.setImageBitmap(bitmap)
        }
    }

    companion object {
        private val CODE = 15
    }
}