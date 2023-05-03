//package com.example.stories101.utils.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Filter
//import android.widget.Filterable
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.stories101.R
//import com.google.firebase.database.*
//
//class StoryAdapter(private val query: Query) : RecyclerView.Adapter<StoryAdapter.ViewHolder>(), Filterable {
//
//    private var stories: List<DataSnapshot> = ArrayList()
//    private var filteredStories: List<DataSnapshot> = ArrayList()
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val titleTextView: TextView = itemView.findViewById(R.id.storyTitleTextView)
//        val descriptionTextView: TextView = itemView.findViewById(R.id.storyDescriptionTextView)
//        private val shareButton: ImageButton = itemView.findViewById(R.id.share_button)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_list_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val storySnapshot: DataSnapshot = filteredStories[position]
//        val title: String = storySnapshot.child("title").value as String
//        val description: String = storySnapshot.child("description").value as String
//        holder.titleTextView.text = title
//        holder.descriptionTextView.text = description
//
//    }
//
//    override fun getItemCount(): Int {
//        return filteredStories.size
//    }
//
//    private fun getItem(position: Int): DataSnapshot {
//        return filteredStories[position]
//    }
//
//    private fun getStoriesList() {
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                stories = ArrayList()
//                for (child in snapshot.children) {
//                    (stories as ArrayList<DataSnapshot>).add(child)
//                }
//                filteredStories = stories
//                notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "loadPost:onCancelled", error.toException())
//            }
//        })
//    }
//
//    init {
//        getStoriesList()
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filteredList = ArrayList<DataSnapshot>()
//                if (constraint == null || constraint.isEmpty()) {
//                    filteredList.addAll(stories)
//                } else {
//                    val filterPattern = constraint.toString().toLowerCase().trim()
//                    for (story in stories) {
//                        val title = story.child("title").value as String
//                        val description = story.child("description").value as String
//                        if (title.toLowerCase().contains(filterPattern) || description.toLowerCase().contains(filterPattern)) {
//                            filteredList.add(story)
//                        }
//                    }
//                }
//                val results = FilterResults()
//                results.values = filteredList
//                return results
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredStories = results?.values as List<DataSnapshot>
//                notifyDataSetChanged()
//            }
//        }
//    }
//
//    companion object {
//        private const val TAG = "StoryAdapter"
//    }
//}


package com.example.stories101.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stories101.R
import com.google.firebase.database.*

class StoryAdapter(private val query: Query, private val onShareClicked: (String) -> Unit) : RecyclerView.Adapter<StoryAdapter.ViewHolder>(), Filterable {

    private var stories: List<DataSnapshot> = ArrayList()
    private var filteredStories: List<DataSnapshot> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.storyTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.storyDescriptionTextView)
        private val shareButton: ImageButton = itemView.findViewById(R.id.share_button)

        fun bind(storySnapshot: DataSnapshot) {
            val title: String = storySnapshot.child("title").value as String
            val description: String = storySnapshot.child("description").value as String
            titleTextView.text = title
            descriptionTextView.text = description

            shareButton.setOnClickListener {
                onShareClicked(title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storySnapshot: DataSnapshot = filteredStories[position]
        holder.bind(storySnapshot)
    }

    override fun getItemCount(): Int {
        return filteredStories.size
    }

    private fun getItem(position: Int): DataSnapshot {
        return filteredStories[position]
    }

    private fun getStoriesList() {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                stories = ArrayList()
                for (child in snapshot.children) {
                    (stories as ArrayList<DataSnapshot>).add(child)
                }
                filteredStories = stories
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    init {
        getStoriesList()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<DataSnapshot>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(stories)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (story in stories) {
                        val title = story.child("title").value as String
                        val description = story.child("description").value as String
                        if (title.toLowerCase().contains(filterPattern) || description.toLowerCase().contains(filterPattern)) {
                            filteredList.add(story)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredStories = results?.values as List<DataSnapshot>
                notifyDataSetChanged()
            }
        }
    }
}
