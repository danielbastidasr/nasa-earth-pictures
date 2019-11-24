package daniel.bastidas.earthnasa.feature.naturallist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.natural_list_item.view.*

import daniel.bastidas.earthnasa.R
import daniel.bastidas.earthnasa.common.aux.ImageType
import daniel.bastidas.earthnasa.common.aux.NaturalListDiffUtilCallback
import daniel.bastidas.earthnasa.common.aux.getUrl
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth


class NaturalEarthListAdapter(
    private val clickCallback: (selectNaturalItem: NaturalEarth) -> Unit
) :RecyclerView.Adapter<NaturalEarthListAdapter.NaturalEarthViewHolder>() {

    private var listNaturalEarth:List<NaturalEarth> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaturalEarthViewHolder
            = LayoutInflater.from(parent.context).run {
                NaturalEarthViewHolder(inflate(R.layout.natural_list_item, parent, false), clickCallback::invoke)
            }

    override fun getItemCount(): Int = listNaturalEarth.count()

    override fun onBindViewHolder(holder: NaturalEarthViewHolder, position: Int) {
        holder.bind(listNaturalEarth[position])
    }

    fun updateData(list: List<NaturalEarth>) {
        val diffResult = DiffUtil.calculateDiff(NaturalListDiffUtilCallback(listNaturalEarth, list))
        this.listNaturalEarth = list
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NaturalEarthViewHolder(
        itemView:View,
        private val clickCallback: (selectNaturalItem: NaturalEarth) -> Unit) :RecyclerView.ViewHolder(itemView){

        private var selected: NaturalEarth? = null

        init {
            itemView.setOnClickListener {
                selected?.let(clickCallback::invoke)
            }
        }

        fun bind(naturalEarth: NaturalEarth){

            selected = naturalEarth

            itemView.run {
                date.text = naturalEarth.date
                caption.text = naturalEarth.caption
                Picasso
                    .get()
                    .load(naturalEarth.getUrl(ImageType.thumbs))
                    .error(R.drawable.ic_launcher_background)
                    .into(imageItem)
            }
        }

    }
}