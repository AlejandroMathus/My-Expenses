package com.example.myexpenses.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myexpenses.R
import com.example.myexpenses.database.ExpenseDatabase
import com.example.myexpenses.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment.
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        // Reference to the app this fragment is attached to, to pass into the view model factory provider.
        val application = requireNotNull(this.activity).application

        //  Reference to the data source via a reference to de DAO.
        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao

        // Creates an instance of the View Model Factory, with a data source and application passed.
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        // Reference to the Main View Model.
        val mainViewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(
            MainViewModel::class.java
        )

        val adapter = ExpenseAdapter(ExpenseListener { expenseId ->
            mainViewModel.onExpenseClicked(expenseId)
        })

        binding.expensesList.adapter = adapter

        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel

        mainViewModel.expenses.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        mainViewModel.navigateToNewExpense.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(
                    MainFragmentDirections
                        .actionMainFragmentToNewExpenseFragment()
                )
                mainViewModel.newExpenseFinishedNavigating()
            }
        })

        return binding.root
    }
}