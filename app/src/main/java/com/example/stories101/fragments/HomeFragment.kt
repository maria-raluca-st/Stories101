package com.example.stories101.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stories101.R
import com.example.stories101.utils.adapter.StoryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        storyAdapter = StoryAdapter(database) { title ->
            // Handle onShareClicked event
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.share_bottom_sheet, null)
            bottomSheetView.findViewById<TextView>(R.id.share_title_text).text = title
            bottomSheetView.findViewById<Button>(R.id.share_button).setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, title)
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent, "Share Story Title"))
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        recyclerView.adapter = storyAdapter

        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.home_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.add_story -> {
//                // Handle add story button click event
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//        // Set up "Add Story" button
//        val addStoryButton: View = view.findViewById(R.id.add_new_story_button)
//        addStoryButton.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, AddStoryFragment())
//                .addToBackStack(null)
//                .commit()
//        }
}
