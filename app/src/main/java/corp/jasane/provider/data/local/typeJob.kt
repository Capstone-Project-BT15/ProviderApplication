package corp.jasane.provider.data.local

import android.content.Context
import corp.jasane.provider.R

data class TypeJob(
    val dataTypeJob: List<ItemJob>
)

data class ItemJob(
    val id: Int,
    val nameJob: String
)

fun createJobList(context: Context): List<ItemJob> {
    return listOf(
        ItemJob(id = 1, nameJob = context.getString(R.string.kerja_lepas)),
        ItemJob(id = 2, nameJob = context.getString(R.string.part_time)),
        ItemJob(id = 3, nameJob = context.getString(R.string.full_time)),
        ItemJob(id = 4, nameJob = context.getString(R.string.kontrak))
    )
}