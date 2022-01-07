package com.cartrack.omdapi.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cartrack.omdapi.data.entities.SearchContent
import com.cartrack.omdapi.databinding.FragmentMediaItemBinding

class MediaListAdapter(
    private val callback: (String) -> Unit,
) : RecyclerView.Adapter<MediaListAdapter.ViewHolder>() {

    private val values: MutableList<SearchContent> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMediaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.apply {
            mediaTitle.text = item.title
            mediaType.text = item.type.name
            Glide.with(imageView.context)
                .load(item.poster)
                .placeholder(ColorDrawable(Color.GRAY))
                .circleCrop()
                .into(imageView)
            mediaItemContainer.setOnClickListener { callback.invoke(item.imdbID) }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: AppCompatImageView = binding.mediaPosterImage
        val mediaTitle: AppCompatTextView = binding.mediaTitle
        val mediaType: AppCompatTextView = binding.mediaType
        val mediaItemContainer: ConstraintLayout = binding.mediaItemContainer
    }

    fun updateData(items: List<SearchContent>?) {
        items?.let {
            values.clear()
            values.addAll(items)
            notifyItemRangeChanged(0, items.size)
        }
    }

}