package packt.com.firebasestorage

import android.app.Activity
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.provider.MediaStore
import java.io.IOException
import android.webkit.MimeTypeMap
import android.content.ContentResolver
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.OnProgressListener
import android.widget.Toast
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import android.app.ProgressDialog





/**
 * Created by ashok on 02/04/18.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    //constant to track image chooser intent
    private val PICK_IMAGE_REQUEST = 234

    //uri to store file
    private lateinit var filePath: Uri

    //firebase objects
    private var storageReference: StorageReference? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storageReference = FirebaseStorage.getInstance().reference
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)

        buttonChoose.setOnClickListener(this)
        buttonUpload.setOnClickListener(this);
        textViewShow.setOnClickListener(this);
    }

    override fun onClick(view: View?) {
        when (view) {
            buttonChoose -> {
                showFileChooser()
            }
            buttonUpload -> {
                uploadFile()
            }
            textViewShow -> {
                val intent = Intent(this, ImagesActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun getFileExtension(uri: Uri): String {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            //getting the storage reference
            val sRef = storageReference?.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath))

            //adding the file to reference
            sRef?.putFile(filePath)
                    ?.addOnSuccessListener { taskSnapshot ->
                        //dismissing the progress dialog
                        progressDialog.dismiss()

                        //displaying success toast
                        Toast.makeText(applicationContext, "File Uploaded ", Toast.LENGTH_LONG).show()

                        //creating the upload object to store uploaded image details
                        val upload = Upload(editText.text.toString().trim(), taskSnapshot.downloadUrl!!.toString())

                        //adding an upload to firebase database
                        val uploadId = mDatabase?.push()?.key
                        mDatabase?.child(uploadId)?.setValue(upload)
                    }
                    ?.addOnFailureListener { exception ->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                    }
                    ?.addOnProgressListener { taskSnapshot ->
                        //displaying the upload progress
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    }
        } else {
            //display an error if no file is selected
        }
    }

}
