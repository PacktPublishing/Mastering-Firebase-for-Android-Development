package packt.com.firebasestorage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_images.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase




class ImagesActivity : AppCompatActivity() {

    //adapter object
    private lateinit var adapter: RecyclerView.Adapter<*>
    //database reference
    private lateinit var mDatabase: DatabaseReference

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    //list to hold all the uploaded images
    private var uploads: List<Upload>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressDialog = ProgressDialog(this)

        uploads = ArrayList()

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...")
        progressDialog.show()
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss()

                //iterating through all the values in database
                for (postSnapshot in snapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)
                    upload?.let { (uploads as ArrayList<Upload>).add(it) }
                }
                //creating adapter
                adapter = ImagesAdapter(applicationContext, uploads as ArrayList<Upload>)

                //adding adapter to recyclerview
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.dismiss()
            }
        })


    }
}
