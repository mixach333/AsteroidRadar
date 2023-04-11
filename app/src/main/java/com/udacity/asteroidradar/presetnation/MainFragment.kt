package com.udacity.asteroidradar.presetnation

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.database.DateFilter
import com.udacity.asteroidradar.presetnation.login.AuthenticationState

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        viewModel.authenticationState.observe(viewLifecycleOwner){
            if(it==AuthenticationState.UNAUTHENTICATED){
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                findNavController().popBackStack(R.id.mainFragment, true)
            }
        }
        val adapter = AsteroidItemAdapter { selectedAsteroid ->
            val action = MainFragmentDirections.actionShowDetail(selectedAsteroid)
            findNavController().navigate(action)
        }

        binding.textView.text = viewModel.getCurrentUserName()

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            asteroidRecycler.adapter = adapter
            asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.show_all_menu -> viewModel.showFilteredAsteroids(DateFilter.WEEK)
                    R.id.show_saved_menu -> viewModel.showFilteredAsteroids(DateFilter.SAVED)
                    R.id.show_today_menu -> viewModel.showFilteredAsteroids(DateFilter.TODAY)
                    R.id.log_out_menu_item -> FirebaseAuth.getInstance().signOut()
                }
                return true
            }
        }, this.viewLifecycleOwner)
        //setHasOptionsMenu(true)

        viewModel.asteroidList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.imageUrl.observe(viewLifecycleOwner) {
            binding.imageOfTheDay = it
        }
        viewModel.subscribeToAsteroidsTopic()
        return binding.root
    }

}
