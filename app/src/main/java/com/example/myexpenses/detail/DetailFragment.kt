package com.example.myexpenses.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myexpenses.R
import com.example.myexpenses.database.ExpenseDatabase
import com.example.myexpenses.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val arguments: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application

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

        activity?.title = "Expense Detail"

        // Observer to the state variable for Navigating when the Close button is tapped.
        detailViewModel.navigateToMain.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToMainFragment()
                )
                detailViewModel.doneNavigating()
            }
        })

        // Observer of the Toast variable for showing it when the Delete button is tapped.
        detailViewModel.showToastEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(context, "Expense deleted!", Toast.LENGTH_SHORT).show()
                detailViewModel.doneShowingToast()
            }
        })

        detailViewModel.showConfirmation.observe(viewLifecycleOwner, { show ->
            context?.let {
                if (show == true) {
                    MaterialDialog(it).show {
                        title(R.string.confirmation_title)
                        message(R.string.confirmation_message)
                        positiveButton(R.string.agree) {
                            detailViewModel.onDeleteConfirmed()
                        }
                        negativeButton(R.string.disagree)
                    }
                }
            }
        })

        return binding.root
    }
}
