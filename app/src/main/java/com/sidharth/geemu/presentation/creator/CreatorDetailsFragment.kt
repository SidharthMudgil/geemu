package com.sidharth.geemu.presentation.creator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import coil.load
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.sidharth.geemu.databinding.FragmentCreatorDetailsBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.presentation.creator.adapter.GamesAdapter
import com.sidharth.geemu.presentation.creator.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.creator.viewmodel.CreatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatorDetailsFragment : Fragment(), OnGameClickCallback {

    private val creatorViewModel: CreatorViewModel by viewModels()
    private val args: CreatorDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatorDetailsBinding.inflate(inflater)

        creatorViewModel.fetchData(args.id)
        creatorViewModel.creatorDetails.observe(viewLifecycleOwner) { creator ->
            binding.apply {
                AAChartModel()
                    .chartType(AAChartType.Pie)
                    .series(
                        arrayOf(
                            AASeriesElement().name("Rating Count")
                                .data(
                                    creator.ratings.map {
                                        arrayOf(it.title, it.count)
                                    }.toTypedArray()
                                )
                        )
                    ).apply {
                        chartRating.aa_drawChartWithChartModel(this)
                    }

                AAChartModel()
                    .chartType(AAChartType.Area)
                    .polar(true)
                    .dataLabelsEnabled(false)
                    .categories(
                        creator.timeline.map { it.year.toString() }.toTypedArray()
                    )
                    .series(
                        arrayOf(
                            AASeriesElement().name("Games Count")
                                .data(creator.timeline.map { it.count }.toTypedArray())
                        )
                    ).apply {
                        chartTimeline.aa_drawChartWithChartModel(this)
                    }

                ivImage.load(creator.image)
                ivBackground.load(creator.background)
                tvName.text = creator.name
                tvRatings.text = creator.rating
                tvReviews.text = "${creator.reviewsCount}"
                tvGamesCount.text = "${creator.gamesCount}"
                tvDescription.text = creator.description
            }
        }

        creatorViewModel.games.observe(viewLifecycleOwner) {
            binding.rvItems.layoutManager = LinearLayoutManager(
                binding.root.context, HORIZONTAL, false
            )
            LinearSnapHelper().attachToRecyclerView(binding.rvItems)
            binding.rvItems.adapter = GamesAdapter(
                games = it,
                onGameClickCallback = this
            )
        }

        return binding.root
    }

    override fun onGameClick(game: Game) {

    }

}