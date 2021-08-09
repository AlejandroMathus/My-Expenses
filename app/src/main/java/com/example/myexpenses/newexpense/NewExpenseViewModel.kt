package com.example.myexpenses.newexpense


import androidx.lifecycle.*
import com.example.myexpenses.database.Expense
import com.example.myexpenses.database.ExpenseDatabaseDao
import kotlinx.coroutines.launch

class NewExpenseViewModel(dataSource: ExpenseDatabaseDao) : ViewModel() {

    private val database = dataSource

    // Store the values of the Edit Texts
    val amount = MutableLiveData<Double?>()
    val category = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    // Showing the error message for invalid data
    private val _errorMessage = MutableLiveData<Boolean?>()
    val errorMessage: LiveData<Boolean?>
        get() = _errorMessage

    // Navigation to the Main Fragment and save the new expense in database
    private val _navigateToMain = MutableLiveData<Boolean?>()
    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain

    fun saveExpense() {
        val valid = isDataValid()
        if (valid) {
            val expense = buildExpense()
            insert(expense)
            _navigateToMain.value = true
        } else {
            _errorMessage.value = true

        }
    }

    private fun isDataValid(): Boolean {
        return amount.value != null &&
                !category.value.isNullOrEmpty()
    }

    private fun buildExpense(): Expense {
        return Expense(
            expenseAmount = amount.value!!,
            expenseDescription = description.value,
            expenseCategory = category.value!!
        )
    }

    private fun insert(expense: Expense) = viewModelScope.launch {
        database.insert(expense)
    }

    // Show categories list and set one
    private val _showList = MutableLiveData<Boolean?>()
    val showList: LiveData<Boolean?>
        get() = _showList

    fun showCategories() {
        _showList.value = true
    }

    fun setCategory(selectedCategory: String) {
        category.value = selectedCategory
    }

    fun mainNavigated() {
        _navigateToMain.value = null
    }

    fun doneShowingToast() {
        _errorMessage.value = null
    }
}

class NewExpenseViewModelFactory(
    private val dataSource: ExpenseDatabaseDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewExpenseViewModel::class.java)) {
            return NewExpenseViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
