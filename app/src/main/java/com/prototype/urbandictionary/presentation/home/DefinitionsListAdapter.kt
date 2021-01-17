package com.prototype.urbandictionary.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.prototype.domain.model.DefinitionModel
import com.prototype.urbandictionary.R
import com.prototype.urbandictionary.databinding.ItemWordDefinitionBinding

class DefinitionsListAdapter(
    private val entries: List<DefinitionModel>
) : RecyclerView.Adapter<DefinitionsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_word_definition, parent, false
        )
    )

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(definition: DefinitionModel) {
            DataBindingUtil.bind<ItemWordDefinitionBinding>(view).apply {
                this?.viewModel = DefinitionViewModel(definition)
            }
        }
    }

}