package com.jeongu.imagesearchapp.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeongu.imagesearchapp.databinding.ItemImageResultBinding
import com.jeongu.imagesearchapp.databinding.ItemVideoResultBinding
import com.jeongu.imagesearchapp.presentation.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.extensions.load
import com.jeongu.imagesearchapp.presentation.extensions.setTitle

private const val VIEW_TYPE_IMAGE = 0
private const val VIEW_TYPE_VIDEO = 1

class SearchResultAdapter(
    private val onClick: (SearchResultInfo) -> Unit
) : ListAdapter<SearchResultInfo, RecyclerView.ViewHolder>(SearchResultDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchResultInfo.ImageInfo -> VIEW_TYPE_IMAGE
            is SearchResultInfo.VideoInfo -> VIEW_TYPE_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> ImageResultViewHolder.from(parent, onClick)
            VIEW_TYPE_VIDEO -> VideoResultViewHolder.from(parent, onClick)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageResultViewHolder -> {
                val item = getItem(position) as SearchResultInfo.ImageInfo
                holder.bind(item)
            }
            is VideoResultViewHolder -> {
                val item = getItem(position) as SearchResultInfo.VideoInfo
                holder.bind(item)
            }
        }
    }


    class ImageResultViewHolder(
        private val binding: ItemImageResultBinding,
        private val onClick: (SearchResultInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResultInfo.ImageInfo) {
            with(binding) {
                itemView.setOnClickListener {
                    ivImageResultBookmark.isVisible = !ivImageResultBookmark.isVisible
                    onClick(item)
                }
                ivImageResultThumbnail.load(item.thumbnailUrl)
                tvImageResultTitle.setTitle(VIEW_TYPE_IMAGE ,item.siteName)
                tvImageResultDate.text = item.dateTime
                ivImageResultBookmark.isVisible = item.isBookmarked
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (SearchResultInfo) -> Unit): ImageResultViewHolder {
                return ImageResultViewHolder(
                    ItemImageResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onClick
                )
            }
        }
    }

    class VideoResultViewHolder(
        private val binding: ItemVideoResultBinding,
        private val onClick: (SearchResultInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResultInfo.VideoInfo) {
            with(binding) {
                itemView.setOnClickListener {
                    ivVideoResultBookmark.isVisible = !ivVideoResultBookmark.isVisible
                    onClick(item)
                }
                ivVideoResultThumbnail.load(item.thumbnail)
                tvVideoResultTitle.setTitle(VIEW_TYPE_VIDEO, item.title)
                tvVideoResultDate.text = item.dateTime
                ivVideoResultBookmark.isVisible = item.isBookmarked
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (SearchResultInfo) -> Unit): VideoResultViewHolder {
                return VideoResultViewHolder(
                    ItemVideoResultBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), onClick
                )
            }
        }
    }

}

private class SearchResultDiffCallback : DiffUtil.ItemCallback<SearchResultInfo>() {
    override fun areItemsTheSame(oldItem: SearchResultInfo, newItem: SearchResultInfo): Boolean {
        return when {
            oldItem is SearchResultInfo.ImageInfo && newItem is SearchResultInfo.ImageInfo -> oldItem.docUrl == newItem.docUrl
            oldItem is SearchResultInfo.VideoInfo && newItem is SearchResultInfo.VideoInfo -> oldItem.url == newItem.url
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: SearchResultInfo, newItem: SearchResultInfo): Boolean {
        return oldItem == newItem
    }
}