package ie.wit.pcpartsireland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.list_cards.view.*

interface PartListener {
    fun onPartClick(part: Model)
}

class Adapter(
    private var adverts: List<Model>, private val listener: PartListener
) : RecyclerView.Adapter<Adapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_cards,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val part = adverts[holder.adapterPosition]
        holder.bind(part, listener)
    }

    override fun getItemCount(): Int = adverts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(part: Model, listener: PartListener) {
            itemView.title.text = part.title
            itemView.price.text = part.price.toString()
            itemView.category.text = part.category
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            itemView.setOnClickListener { listener.onPartClick(part) }
        }
    }
}