package packt.com.firebasestorage

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.view.*


/**
 * Created by ashok on 02/04/18.
 */
internal class ImagesAdapter(private val context: Context, private val uploads: List<Upload>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_images, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, url) = uploads[position]
        holder.itemView.textViewShow.text = name
        Glide.with(context).load(url).into(holder.itemView.imageView)
    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}