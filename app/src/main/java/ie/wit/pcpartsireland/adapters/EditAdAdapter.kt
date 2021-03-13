package ie.wit.pcpartsireland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.list_cards.view.*

interface EditPartListener {
    fun onPartClick(part: Model)
}

class EditAdAdapter(
    private var adverts: List<Model>,
    private val listener: CardViewPartListener
) : RecyclerView.Adapter<EditAdAdapter.EditMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditMainHolder {
        return EditMainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.edit_cards,
                parent,
                false

            )
        )
    }

    override fun onBindViewHolder(holder: EditMainHolder, position: Int) {
        val part = adverts[holder.adapterPosition]
        holder.bind(part, listener)
    }

    override fun getItemCount(): Int = adverts.size

    class EditMainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(part: Model, listener: CardViewPartListener) {
            itemView.title.text = part.title
            itemView.category.text = part.category
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, part.image))
            itemView.setOnClickListener { listener.onPartClick(part) }
        }
    }
}