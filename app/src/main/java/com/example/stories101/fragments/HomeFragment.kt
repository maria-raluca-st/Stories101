package com.example.stories101.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stories101.R
import com.example.stories101.utils.adapter.StoryAdapter
import com.example.stories101.utils.model.StoryData
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                storyAdapter.filter.filter(newText)
                return true
            }
        })

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("stories")

        // Set up RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.stories_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        storyAdapter = StoryAdapter(database)

        recyclerView.adapter = storyAdapter

        // Retrieve all stories from Firebase database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val storiesList = mutableListOf<StoryData>()
                for (storySnapshot in dataSnapshot.children) {
                    val title = storySnapshot.child("title").value as String
                    val description = storySnapshot.child("description").value as String
                    val story = StoryData(title, description)
                    storiesList.add(story)
                }
//                storyAdapter.submitList(storiesList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

//        // Set up "Add Story" button
//        val addStoryButton: View = view.findViewById(R.id.add_new_story_button)
//        addStoryButton.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, AddStoryFragment())
//                .addToBackStack(null)
//                .commit()
//        }

        return view
    }
}
