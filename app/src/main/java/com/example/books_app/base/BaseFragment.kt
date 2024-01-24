package com.example.books_app.base
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.books_app.ui.fragments.LoginEmailFragmentDirections

open class BaseFragment : Fragment() {

    // Common method for navigating to another fragment
    protected fun navigateToFragment(actionId:Int) {
        view?.let {
            Navigation.findNavController(it).navigate(actionId)
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    // Common method for handling UI initialization
    protected open fun initUI(view: View) {
        // Implement your common UI initialization logic
    }

    // Override onCreateView to provide a common layout inflation logic if needed
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // Override onViewCreated for common view initialization
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
    }

    // Other common methods...
}
