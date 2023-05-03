package com.example.stories101.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stories101.R
import com.example.stories101.databinding.FragmentAddStoryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddStoryFragment : Fragment() {

    private lateinit var binding: FragmentAddStoryBinding
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var addButton: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var storiesReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleEditText = binding.titleEditText
        descriptionEditText = binding.descriptionEditText
        addButton = binding.addStoryButton

        database = FirebaseDatabase.getInstance()
        storiesReference = database.getReference("stories")

        addButton.setOnClickListener {
            Log.d("AddStoryFragment", "Add story button clicked")
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
                val newStory = storiesReference.push()
                newStory.child("title").setValue(title)
                newStory.child("description").setValue(description)

                // Optional: Clear the input fields after adding a new story
                titleEditText.text.clear()
                descriptionEditText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
