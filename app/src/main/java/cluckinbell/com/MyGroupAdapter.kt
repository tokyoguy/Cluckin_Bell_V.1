package cluckinbell.com

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyGroupAdapter(private val context: Context,
                     private val dataList: List<ItemGroup>?): RecyclerView.Adapter<MyGroupAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layot_group, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.itemTitle.text = dataList!![p1].headerTitle
        var items = dataList[p1].listItem

        p0.recycler_view_list.setHasFixedSize(true)
        p0.recycler_view_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val itemListAdapter = MyItemAdapter(context, items)
        p0.recycler_view_list.adapter = itemListAdapter
        p0.recycler_view_list.isNestedScrollingEnabled = false
        p0.button_more.setOnClickListener {
            Toast.makeText(context, "НОВИНКА: " +dataList[p1].headerTitle, Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var itemTitle: TextView
        var recycler_view_list: RecyclerView
        var button_more: Button

        init {
            itemTitle = view.findViewById(R.id.itemTitle) as TextView
            button_more = view.findViewById(R.id.button_more) as Button
            recycler_view_list = view.findViewById(R.id.recycler_view_list) as RecyclerView
        }
    }
}