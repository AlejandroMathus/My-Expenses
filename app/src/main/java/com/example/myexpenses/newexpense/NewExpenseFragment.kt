package com.example.myexpenses.newexpense

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.myexpenses.R
import com.example.myexpenses.database.ExpenseDatabase

import com.example.myexpenses.databinding.FragmentNewExpenseBinding
import kotlinx.android.synthetic.main.fragment_new_expense.*

val categories = listOf("Shop", "Entertainment", "Food", "Sport", "Bills", "Transport", "Other")

class NewExpenseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment.
        val binding: FragmentNewExpenseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_expense, container, false
        )

        // Reference to the app this fragment is attached to, to pass into the view model factory provider.
        val application = requireNotNull(this.activity).application

        //  Reference to the data source via a reference to de DAO.
        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao

        // Creates an instance of the View Model Factory, with a data source and application passed.
        val viewModelFactory = NewExpenseViewModelFactory(dataSource)
        // Reference to the Main View Model.
        val newExpenseViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(
                NewExpenseViewModel::class.java
            )

        binding.lifecycleOwner = this

        binding.newExpenseViewModel = newExpenseViewModel

        // Observer to the state variable for Navigating when the Submit button is tapped.
        newExpenseViewModel.navigateToMain.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(NewExpenseFragmentDirections.actionNewExpenseFragmentToMainFragment())
                newExpenseViewModel.mainNavigated()
            }
        })

        // Observer of the Toast variable for showing it when the data submitted is not valid.
        newExpenseViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(context, "Todo mal", Toast.LENGTH_SHORT).show()
                newExpenseViewModel.doneShowingToast()
            }
        })

        newExpenseViewModel.showList.observe(viewLifecycleOwner, Observer { show ->
            context?.let {
                if (show == true) {
                    MaterialDialog(it).show {
                        listItemsSingleChoice(items = categories)
                    }
                }
            }
        })
        return binding.root
    }
}