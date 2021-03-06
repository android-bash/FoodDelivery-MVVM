package com.kopa.me.driver.presentation.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kopa.me.driver.BR
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.ui.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * This is the documentation block about the class
 */


abstract class BaseFragment<VM: BaseViewModel, Binding: ViewDataBinding>(
	@LayoutRes private val layoutId: Int
) : Fragment() {

	protected lateinit var navController: NavController

	protected val TAG = "mylogs_" + javaClass.simpleName

	protected val sharedViewModel: SharedViewModel by sharedViewModel()

	protected abstract val mViewModel: VM?

	protected lateinit var binding: Binding
		private set


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false)
			.apply {
				lifecycleOwner = viewLifecycleOwner
				setVariable(BR.viewModel, mViewModel)
				binding = this
			}.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViews()
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		navController = findNavController()
	}

	abstract fun setupViews()

	open fun renderState(state: ViewState) {
		sharedViewModel.handleLoading(state)
	}
}