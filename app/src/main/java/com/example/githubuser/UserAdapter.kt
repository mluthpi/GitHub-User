package com.example.githubuser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

var userFilterList = ArrayList<UserData>()
lateinit var mContext: Context
 class UserAdapter(private var listData: ArrayList<UserData>): RecyclerView.Adapter<UserAdapter.ListViewHolder>(), Filterable {
    init {
        userFilterList = listData
    }
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onITemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onITemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        val sch = ListViewHolder(view)
        mContext = parent.context
        return sch
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = userFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.txtUsername.text = data.username
        holder.txtName.text = data.name
        holder.txtCompany.text = data.company
        holder.txtLocation.text = data.location
        holder.itemView.setOnClickListener {
            val dataUser = UserData(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(dataUser: UserData)
    }

    override fun getItemCount(): Int = userFilterList.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgAvatar: CircleImageView = itemView.findViewById(R.id.imgAvatar)
        var txtUsername: TextView = itemView.findViewById(R.id.tv_username)
        var txtName: TextView = itemView.findViewById(R.id.tv_name)
        var txtCompany: TextView = itemView.findViewById(R.id.tv_company)
        var txtLocation: TextView = itemView.findViewById(R.id.tv_location)
    }

     override fun getFilter(): Filter {
         return object : Filter(){
             override fun performFiltering(constraint: CharSequence?): FilterResults {
                 val charSearch = constraint.toString()
                 userFilterList = if(charSearch.isEmpty()){
                     listData
                 } else {
                     val resultList = ArrayList<UserData>()
                     for(row in userFilterList){
                         if((row.username.toString().toLowerCase(Locale.ROOT)
                                 .contains(charSearch.toLowerCase(Locale.ROOT)))
                         ) {
                             resultList.add(
                                 UserData(
                                     row.username,
                                     row.name,
                                     row.avatar,
                                     row.company,
                                     row.location,
                                     row.repository,
                                     row.followers,
                                     row.following
                                 )
                             )
                         }
                     }
                     resultList
                 }
                 val filterResult = FilterResults()
                 filterResult.values = userFilterList
                 return filterResult
             }

             @Suppress("UNCHECKED_CAST")
             override fun publishResults(constraint: CharSequence, result: FilterResults) {
                 userFilterList = result.values as ArrayList<UserData>
                 notifyDataSetChanged()
             }
         }
     }
 }