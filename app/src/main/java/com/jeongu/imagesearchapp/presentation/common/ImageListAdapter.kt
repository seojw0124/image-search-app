package com.jeongu.imagesearchapp.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeongu.imagesearchapp.databinding.ItemSearchResultBinding
import com.jeongu.imagesearchapp.presentation.ImageInfo

class ImageListAdapter(
    private val onClick: (ImageInfo) -> Unit
) : ListAdapter<ImageInfo, ImageListAdapter.SearchResultViewHolder>(SearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SearchResultViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onClick: (ImageInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageInfo) {
            with(binding) {
                ivBookmark.setOnClickListener {
                    onClick(item)
                }
                Glide.with(ivSearchResultThumbnailImage)
                    .load(item.thumbnailUrl)
                    .into(ivSearchResultThumbnailImage)
                tvLatestArticleTitle.text = item.siteName
                tvLatestArticlePublishDate.text = item.dateTime
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (ImageInfo) -> Unit): SearchResultViewHolder {
                return SearchResultViewHolder(
                    ItemSearchResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onClick
                )
            }
        }
    }

}

private class SearchResultDiffCallback : DiffUtil.ItemCallback<ImageInfo>() {
    override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
        return oldItem == newItem
    }
}