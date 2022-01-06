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
import com.cartrack.omdapi.data.entities.MediaContent
import com.cartrack.omdapi.databinding.FragmentMediaItemBinding
import com.cartrack.omdapi.ui.placeholder.PlaceholderContent.PlaceholderItem

class MediaListAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MediaListAdapter.ViewHolder>() {

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
            mediaTitle.text = item.content
            mediaDescription.text = item.content
            Glide.with(imageView.context)
                .load("url")
                .placeholder(ColorDrawable(Color.GRAY))
                .circleCrop()
                .into(imageView)
            mediaItemContainer.setOnClickListener{
                TODO("handle click event")
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: AppCompatImageView = binding.mediaPosterImage
        val mediaTitle: AppCompatTextView = binding.mediaTitle
        val mediaDescription: AppCompatTextView = binding.mediaDescription
        val mediaItemContainer : ConstraintLayout = binding.mediaItemContainer
    }

    fun setData(items: List<MediaContent>?) {

    }

}