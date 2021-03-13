package ie.wit.pcpartsireland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.list_cards.view.*

interface ViewPartListener {
    fun onPartClick(part: Model)
}

class ViewAdAdapter(
    private var adverts: List<Model>,
    private val listener: CardViewPartListener
) : RecyclerView.Adapter<ViewAdAdapter.ViewAdMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdMainHolder {
        return ViewAdMainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_cards,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewAdMainHolder, position: Int) {
        val part = adverts[holder.adapterPosition]
        holder.bind(part, listener)
    }

    override fun getItemCount(): Int = adverts.size

    class ViewAdMainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(part: Model, listener: CardViewPartListener) {
            itemView.title.text = part.title
            itemView.price.text = part.price.toString()
            itemView.category.text = part.category
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, part.image))
            itemView.setOnClickListener { listener.onPartClick(part) }
        }
    }
}