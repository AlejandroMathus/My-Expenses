package com.example.myexpenses.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myexpenses.R
import com.example.myexpenses.database.ExpenseDatabase
import com.example.myexpenses.database.ExpenseDatabaseDao
import com.example.myexpenses.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(arguments)

        // Create an instance of the ViewModel Factory.
        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao
        val viewModelFactory = DetailViewModelFactory(arguments.expenseKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val detailViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(DetailViewModel::class.java)

        binding.detailViewModel = detailViewModel

        binding.lifecycleOwner = this

        // Observer to the state variable for Navigating when the Close button is tapped.
        detailViewModel.navigateToMain.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToMainFragment()
                )
                detailViewModel.doneNavigating()
            }
        })

        // Observer of the Toast variable for showing it when the Delete button is tapped.
        detailViewModel.showToastEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(context, "Expense deleted!", Toast.LENGTH_SHORT).show()
                detailViewModel.doneShowingToast()
            }
        })

        return binding.root
    }
}
