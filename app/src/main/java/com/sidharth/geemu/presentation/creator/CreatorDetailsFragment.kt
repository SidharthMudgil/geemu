package com.sidharth.geemu.presentation.creator

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import coil.load
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.sidharth.geemu.R
import com.sidharth.geemu.core.util.BlurTransformation
import com.sidharth.geemu.databinding.FragmentCreatorDetailsBinding
import com.sidharth.geemu.domain.model.Game
import com.sidharth.geemu.domain.model.Rating
import com.sidharth.geemu.domain.model.Timeline
import com.sidharth.geemu.presentation.creator.adapter.GamesAdapter
import com.sidharth.geemu.presentation.creator.callback.OnGameClickCallback
import com.sidharth.geemu.presentation.creator.viewmodel.CreatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatorDetailsFragment : Fragment(), OnGameClickCallback {

    private lateinit var binding: FragmentCreatorDetailsBinding
    private val creatorViewModel: CreatorViewModel by viewModels()
    private val args: CreatorDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatorDetailsBinding.inflate(inflater)

        fetchCreatorDetails()
        observeCreatorDetails()
        observeSavedGames()

        return binding.root
    }

    private fun fetchCreatorDetails() {
        creatorViewModel.fetchData(args.id)
    }

    private fun observeCreatorDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                creatorViewModel.creatorDetails.collect { creator ->
                    binding.apply {
                        generateRatingChart(creator.ratings).apply {
                            chartRating.aa_drawChartWithChartModel(this)
                        }

                        generateTimelineChart(creator.timeline).apply {
                            chartTimeline.aa_drawChartWithChartModel(this)
                        }

                        ivImage.load(creator.image)
                        ivBackground.load(creator.background) {
                            transformations(BlurTransformation(10))
                        }
                        tvName.text = creator.name
                        tvRatings.text = creator.rating
                        tvReviews.text = "${creator.reviewsCount}"
                        tvGamesCount.text = "${creator.gamesCount}"
                        tvDescription.text =
                            Html.fromHtml(creator.description, Html.FROM_HTML_MODE_LEGACY)
                    }
                }
            }
        }
    }

    private fun observeSavedGames() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                creatorViewModel.games.collect {
                    binding.rvItems.layoutManager = LinearLayoutManager(
                        binding.root.context, HORIZONTAL, false
                    )

                    binding.rvItems.adapter = GamesAdapter(
                        games = it,
                        onGameClickCallback = this@CreatorDetailsFragment
                    )
                }
            }
        }
    }

    private fun generateRatingChart(ratings: List<Rating>): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor(R.color.grey800)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Rating Count")
                        .data(
                            ratings.map { arrayOf(it.title, it.count) }.toTypedArray()
                        )
                )
            )
    }

    private fun generateTimelineChart(timeline: List<Timeline>): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Area)
            .backgroundColor(R.color.grey800)
            .series(
                arrayOf(
                    AASeriesElement().name("Games Count")
                        .data(timeline.map {
                            arrayOf(it.year, it.count)
                        }.toTypedArray())
                )
            )
    }

    override fun onGameClick(game: Game) {
        val action =
            CreatorDetailsFragmentDirections.actionCreatorDetailsFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}