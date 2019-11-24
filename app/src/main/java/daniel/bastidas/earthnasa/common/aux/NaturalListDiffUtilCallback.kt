package daniel.bastidas.earthnasa.common.aux

import androidx.recyclerview.widget.DiffUtil
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth

class NaturalListDiffUtilCallback(
    private val oldList: List<NaturalEarth>,
    private val newList: List<NaturalEarth>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}