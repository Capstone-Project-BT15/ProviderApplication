package corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.provider.data.response.UserData
import corp.jasane.provider.data.response.WorkItem
import corp.jasane.provider.databinding.ListItemOffersWorkerBinding
import corp.jasane.provider.databinding.ItemOffersWorkerBinding

@Suppress("NAME_SHADOWING")
class OffersStatusOfferAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<Any> = mutableListOf()
    private var onItemClickCallBack: OnItemClickCallBack? = null
    private val MAX_DESCRIPTION_LINES = 4

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(workData: List<Any>) {
        list.clear()
        list.addAll(workData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_WORK_ITEM -> {
                val binding =
                    ListItemOffersWorkerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                WorkItemViewHolder(binding)
            }
            TYPE_USER_DATA -> {
                val binding =
                    ItemOffersWorkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserDataViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WorkItemViewHolder -> holder.bind(list[position] as WorkItem)
            is UserDataViewHolder -> holder.bind(list[position] as UserData)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is WorkItem -> TYPE_WORK_ITEM
            is UserData -> TYPE_USER_DATA
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    inner class WorkItemViewHolder(val binding: ListItemOffersWorkerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val userDataAdapter = OffersStatusOfferAdapter(context)

        init {
            binding.recyclerListWorker.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerListWorker.adapter = userDataAdapter

            userDataAdapter.setOnItemClickCallback(object : OffersStatusOfferAdapter.OnItemClickCallBack {
                override fun onItemClicked(data: WorkItem) {
                    onItemClickCallBack?.onItemClicked(data)
                }

                override fun onItemClickedData(data: UserData) {
                    onItemClickCallBack?.onItemClickedData(data)
                }
            })
        }

        fun bind(work: WorkItem) {
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(work)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(work.work_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(tvItemPhoto)
                textNameWorker.text = work.work_title

//                val userDataAdapter = OffersStatusOfferAdapter(context)
//                val userDataLayoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                recyclerListWorker.layoutManager = userDataLayoutManager
//                recyclerListWorker.adapter = userDataAdapter
                userDataAdapter.setList(work.data)
            }
        }
    }

    inner class UserDataViewHolder(val binding: ItemOffersWorkerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            binding.buttonOffersPending.setOnClickListener {
                val userData = list[adapterPosition] as? UserData
                userData?.let {
                    onItemClickCallBack?.onItemClickedData(it)
                }
            }
            binding.apply {
                Glide.with(itemView)
                    .load(userData.work_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(tvItemPhoto)
                jobDistance.text = userData.distance
                jobName.text = userData.user_fullname
                price.text = userData.tariff

                description.maxLines = MAX_DESCRIPTION_LINES
                description.text = userData.experience
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: WorkItem)
        fun onItemClickedData(data: UserData)
    }

    companion object {
        private const val TYPE_WORK_ITEM = 1
        private const val TYPE_USER_DATA = 2
    }
}