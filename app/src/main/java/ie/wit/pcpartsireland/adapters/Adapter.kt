package ie.wit.pcpartsireland.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.ui.home.HomeFragment
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_create_advert.view.*
import kotlinx.android.synthetic.main.list_cards.view.*



class Adapter(
    private var adverts: List<Model>, homeFragment: HomeFragment
)
    : RecyclerView.Adapter<Adapter.MainHolder>() {

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
        holder.bind(part)
    }

    override fun getItemCount(): Int = adverts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(part: Model) {
            itemView.title.text = part.title
            itemView.price.text = part.price.toString()
            itemView.category.text = part.category
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
        }
    }
}