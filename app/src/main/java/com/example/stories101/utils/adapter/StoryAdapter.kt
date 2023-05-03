package com.example.stories101.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stories101.R
import com.google.firebase.database.*

class StoryAdapter(private val databaseReference: DatabaseReference) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.storyTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.storyDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storySnapshot: DataSnapshot = getItem(position)
        val title: String = storySnapshot.child("title").value as String
        val description: String = storySnapshot.child("description").value as String
        holder.titleTextView.text = title
        holder.descriptionTextView.text = description
    }

    override fun getItemCount(): Int {
        return snapshots.size
    }

    private fun getItem(position: Int): DataSnapshot {
        return snapshots[position]
    }

    private val snapshots = ArrayList<DataSnapshot>()

    init {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshots.clear()
                for (storySnapshot in snapshot.children) {
                    snapshots.add(storySnapshot)
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Not implemented
            }
        })
    }

}
