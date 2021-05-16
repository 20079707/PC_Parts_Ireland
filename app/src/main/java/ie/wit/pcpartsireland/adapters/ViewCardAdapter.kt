package ie.wit.pcpartsireland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.list_cards.view.*

interface CardViewPartListener {
    fun onPartClick(part: Model)
}

class ViewCardAdapter(
    private var adverts: List<Model>,
    private val listenerCard: CardViewPartListener
) : RecyclerView.Adapter<ViewCardAdapter.MainHolder>() {

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
        holder.bind(part, listenerCard)
    }

    override fun getItemCount(): Int = adverts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(part: Model, listenerCard: CardViewPartListener) {
            itemView.title.text = part.title
            itemView.price.text = part.price.toString()
            itemView.category.text = part.category
            Glide.with(itemView.context).load(part.image).into(itemView.imageIcon)
            itemView.setOnClickListener { listenerCard.onPartClick(part) }
            if(part.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)
        }
    }
}