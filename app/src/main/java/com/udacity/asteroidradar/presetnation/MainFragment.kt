package com.udacity.asteroidradar.presetnation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
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
            it.let {
                adapter.submitList(it)
            }
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
        return true
    }


}
