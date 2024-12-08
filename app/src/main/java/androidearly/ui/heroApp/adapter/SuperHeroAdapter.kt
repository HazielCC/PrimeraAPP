package androidearly.ui.heroApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidearly.ui.heroApp.data.SuperHeroItemResponse
import androidx.recyclerview.widget.RecyclerView
import com.coco.primeraapp.R


class SuperHeroAdapter(
    private var superHeroList: List<SuperHeroItemResponse> = emptyList(),
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<SuperHeroViewHolder>() {

    fun updateList(superHeroList: List<SuperHeroItemResponse>) {
        this.superHeroList = superHeroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SuperHeroViewHolder(layoutInflater.inflate(R.layout.item_super_hero, parent, false))
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(superHeroList[position], onItemClicked)
    }

    override fun getItemCount() = superHeroList.size
}
