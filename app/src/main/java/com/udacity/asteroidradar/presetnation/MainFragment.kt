package com.udacity.asteroidradar.presetnation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.database.DateFilter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        val adapter = AsteroidItemAdapter { selectedAsteroid ->
            val action = MainFragmentDirections.actionShowDetail(selectedAsteroid)
            findNavController().navigate(action)
        }

        with(binding) {
            lifecycleOwner = this@MainFragment
            viewModel = viewModel
            asteroidRecycler.adapter = adapter
            asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
        }

        setHasOptionsMenu(true)

        viewModel.asteroidList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        viewModel.imageUrl.observe(viewLifecycleOwner) {
            binding.imageOfTheDay = it
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.showFilteredAsteroids(DateFilter.WEEK)
            R.id.show_saved_menu -> viewModel.showFilteredAsteroids(DateFilter.SAVED)
            R.id.show_today_menu -> viewModel.showFilteredAsteroids(DateFilter.TODAY)
        }
        return true
    }


}
