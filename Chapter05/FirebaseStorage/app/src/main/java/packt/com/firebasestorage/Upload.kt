package packt.com.firebasestorage

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by ashok on 02/04/18.
 */
@IgnoreExtraProperties
data class Upload( var name: String,
                   var url: String)