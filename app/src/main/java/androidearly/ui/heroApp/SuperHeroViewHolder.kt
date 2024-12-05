package androidearly.heroApp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coco.primeraapp.R
import com.coco.primeraapp.databinding.ItemSuperHeroBinding

class SuperHeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSuperHeroBinding.bind(view) // Ajusta esto según tu binding

    fun bind(superHeroItemResponse: SuperHeroItemResponse, onItemClicked: (String) -> Unit) {
        // Asigna otros datos del superhéroe a las vistas
        binding.tvItemHero.text = superHeroItemResponse.name
        // Cargar la imagen usando Glide
        Glide.with(itemView.context)
            .load(superHeroItemResponse.image.url)
            .placeholder(R.drawable.ic_launcher_foreground) // Reemplaza con tu recurso de imagen placeholder
            .into(binding.ivHeroItem)


        // Agrega un clic listener
        binding.root.setOnClickListener {
            onItemClicked(superHeroItemResponse.id)
        }
    }
}
