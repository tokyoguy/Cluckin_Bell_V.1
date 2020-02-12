package cluckinbell.com

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyItemAdapter(private val context: Context,
                    private val itemList: List<ItemData>?): RecyclerView.Adapter<MyItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layot_item, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.txt_title.setText(itemList!![position].name)
        Picasso.get().load(itemList!![position].image).into(myViewHolder.img_item)

        myViewHolder.setClick(object: ItemClickListener{
            override fun onItemClickListener(view: View, position: Int) {
                Toast.makeText(context, "" + itemList[position].name, Toast.LENGTH_SHORT).show()
            }

        })
    }





    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        var txt_title: TextView
        var img_item: ImageView

        lateinit var itemClickListener: ItemClickListener
        fun setClick(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        init {
            txt_title = view.findViewById(R.id.tvTitle) as TextView
            img_item = view.findViewById(R.id.itemImage) as ImageView
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener.onItemClickListener(view!!, adapterPosition)
        }

    }
}