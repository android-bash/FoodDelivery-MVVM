package com.kopa.me.driver.presentation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kopa.me.driver.BR

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding</V></T>
 */

abstract class BaseAdapter<T>(private var data: List<T>,
                              @LayoutRes private val layoutId: Int? = null):

		RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder<T>>(),
		BindableAdapter<List<T>> {

	private var mClickListener: OnItemClickListener<T>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		BaseViewHolder<T>(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
		                                          viewType,
		                                          parent,
		                                          false))

	override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
		holder.bind(getItem(position))

	//default data setter
	//override to implement another approach
	override fun setNewData(newData: List<T>) {
		data = newData
		notifyDataSetChanged()
	}

	override fun getItemViewType(position: Int) = getLayoutIdForItem(position)

	override fun getItemCount(): Int = data.size

	protected fun getItem(position: Int): T = data[position]

	open fun getLayoutIdForItem(position: Int): Int = layoutId ?: 0

	// allows clicks events to be caught
	open fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>) {
		mClickListener = itemClickListener
	}

//	override fun onFailedToRecycleView(holder: BaseViewHolder<T>): Boolean { return true }

	inner class BaseViewHolder<T>(private val binding: ViewDataBinding):
			RecyclerView.ViewHolder(binding.root){

		init {
			mClickListener?.let { mClickListener ->
				itemView.setOnClickListener {
					mClickListener.onItemClick(getItem(adapterPosition), adapterPosition, binding.root)
				}
			}
		}

		fun bind(item: T) {
			binding.setVariable(BR.bindItem, item)
			binding.executePendingBindings()
		}
	}

	// parent fragment will override this method to respond to click events
	interface OnItemClickListener<T> {
		fun onItemClick(item: T, position: Int, view: View)
	}


}

interface BindableAdapter<T> {
	fun setNewData(newData: T)
}