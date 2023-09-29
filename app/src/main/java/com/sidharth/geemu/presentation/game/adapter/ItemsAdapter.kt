package com.sidharth.geemu.presentation.game.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.sidharth.geemu.core.constant.Constants
import com.sidharth.geemu.core.enums.GameFilterType
import com.sidharth.geemu.core.util.BlurTransformation
import com.sidharth.geemu.databinding.ItemCardCreatorBinding
import com.sidharth.geemu.databinding.ItemCardGame2Binding
import com.sidharth.geemu.databinding.ItemCardLabelImageBinding
import com.sidharth.geemu.databinding.ItemCardScreenshotBinding
import com.sidharth.geemu.domain.model.Creator
import com.sidharth.geemu.domain.model.Developer
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Publisher
import com.sidharth.geemu.domain.model.Trailer
import com.sidharth.geemu.presentation.game.callback.OnCreatorClickCallback
import com.sidharth.geemu.presentation.game.callback.OnItemClickCallback
import com.sidharth.geemu.presentation.game.callback.OnMediaClickCallback
import com.stfalcon.imageviewer.StfalconImageViewer

class ItemsAdapter(
    private val type: CardType,
    private val items: List<Any>,
    private val onItemClickCallback: Any? = null,
) : Adapter<ViewHolder>() {
    enum class CardType {
        SCREENSHOT, ADDITION, CREATOR, PUBLISHER, DEVELOPER
    }

    inner class ScreenshotViewHolder(
        private val binding: ItemCardScreenshotBinding,
    ) : ViewHolder(binding.root) {
        fun bind(item: Any) {
            binding.apply {
                when (item) {
                    is Trailer -> {
                        ivImage.load(item.preview)
                        btnPlay.visibility = VISIBLE
                        ivImage.setOnClickListener {
                            (onItemClickCallback as OnMediaClickCallback).onVideoClick(
                                preview = item.preview,
                                low = item.qualityLow,
                                high = item.qualityMax,
                            )
                        }
                    }

                    is String -> {
                        ivImage.load(item)
                        btnPlay.visibility = GONE
                        val iv = StfalconImageViewer.Builder(
                            root.context,
                            listOf(item)
                        ) { view, url -> view.load(url) }
                        ivImage.setOnClickListener {
                            iv.show()
                        }
                    }
                }
            }
        }
    }

    inner class CreatorViewHolder(
        private val binding: ItemCardCreatorBinding,
    ) : ViewHolder(binding.root) {
        fun bind(item: Any) {
            binding.apply {
                ivImage.load((item as Creator).image)
                tvName.text = item.name
                tvRole.text = item.role
                cvCreator.setOnClickListener {
                    (onItemClickCallback as OnCreatorClickCallback).onCreatorClick(item)
                }
            }
        }
    }

    inner class AdditionViewHolder(
        private val binding: ItemCardGame2Binding
    ) : ViewHolder(binding.root) {
        fun bind(item: Any) {
            binding.apply {
                when {
                    (item as Game).image.isBlank() -> {
                        ivCover.load(Constants.BACKGROUND_IMAGE) {
                            transformations(BlurTransformation(5))
                        }
                    }

                    else -> ivCover.load(item.image)
                }
                tvName.text = item.name
                tvRatings.text = item.rating
                tvGenres.text = item.genres
                tvReleaseDate.text = item.release
            }
        }
    }

    inner class LabelImageViewHolder(
        private val binding: ItemCardLabelImageBinding,
    ) : ViewHolder(binding.root) {
        fun bind(item: Any) {
            binding.apply {
                when (type) {
                    CardType.DEVELOPER -> {
                        ivImage.load((item as Developer).image)
                        tvLabel.text = item.name
                        cvLabelImage.setOnClickListener {
                            (onItemClickCallback as OnItemClickCallback).onItemClick(
                                id = item.id,
                                name = item.name,
                                type = GameFilterType.DEVELOPER,
                            )
                        }
                    }

                    CardType.PUBLISHER -> {
                        ivImage.load((item as Publisher).image)
                        tvLabel.text = item.name
                        cvLabelImage.setOnClickListener {
                            (onItemClickCallback as OnItemClickCallback).onItemClick(
                                id = item.id,
                                name = item.name,
                                type = GameFilterType.PUBLISHER,
                            )
                        }
                    }

                    else -> throw IllegalStateException("Invalid ViewType")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            CardType.SCREENSHOT -> {
                ScreenshotViewHolder(
                    ItemCardScreenshotBinding.inflate(
                        inflater, parent, false
                    )
                )
            }

            CardType.ADDITION -> AdditionViewHolder(
                ItemCardGame2Binding.inflate(
                    inflater, parent, false
                )
            )

            CardType.CREATOR -> CreatorViewHolder(
                ItemCardCreatorBinding.inflate(
                    inflater, parent, false
                )
            )

            CardType.PUBLISHER, CardType.DEVELOPER -> LabelImageViewHolder(
                ItemCardLabelImageBinding.inflate(
                    inflater, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (type) {
            CardType.SCREENSHOT -> (holder as ScreenshotViewHolder).bind(items[position])
            CardType.ADDITION -> (holder as AdditionViewHolder).bind(items[position])
            CardType.CREATOR -> (holder as CreatorViewHolder).bind(items[position])
            CardType.PUBLISHER -> (holder as LabelImageViewHolder).bind(items[position])
            CardType.DEVELOPER -> (holder as LabelImageViewHolder).bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}