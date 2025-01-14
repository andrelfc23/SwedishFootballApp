package com.abav.footballfranzy.APIFootball


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.abav.footballfranzy.ChatAdapter
import com.abav.footballfranzy.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GroupChatFragment : Fragment() {

    private lateinit var messages: MutableList<ChatMessage>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var sendButton: AppCompatImageView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatRecyclerView: RecyclerView
    private var username: String = "Unknown" // Default value if username isn't fetched
    private var uId: String = "id" // Default value if username isn't fetched

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_chat, container, false)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        messages= ArrayList<ChatMessage>()
        // Fetch the username of the logged-in user
        fetchLoggedInUsername()

        // Initialize RecyclerView
        chatRecyclerView = view.findViewById(R.id.rvChat)


        // Initialize send button
        sendButton = view.findViewById(R.id.ivSend)
        sendButton.setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.etMessage)
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedDateTime = currentDateTime.format(formatter)

            val messageData = hashMapOf(
                "message" to editText.text.toString(),
                "sender" to username, // Use dynamically fetched username
                "time" to formattedDateTime,
                "id" to uId
            )

            firestore.collection("chat")
                .add(messageData)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e")
                }

            editText.setText("")
        }

        // Listen for live chat updates
        firestore.collection("chat").orderBy("time")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    println("Listen failed: $e")
                    return@addSnapshotListener
                }

                messages.clear()
                for (document in snapshots!!) {

                    val message = document.getString("message") ?: "No message"
                    val sender = document.getString("sender") ?: "No sender"
                    val time = document.getString("time") ?: "No time"
                    val id = document.getString("id") ?: "No time"
                    messages.add(ChatMessage(id ,sender, message, time))
                }

                try {
                    chatRecyclerView.layoutManager = LinearLayoutManager(context)
                    chatAdapter = ChatAdapter(messages, auth.currentUser!!.uid.toString())
                   // chatRecyclerView.smoothScrollToPosition(chatRecyclerView.adapter?.itemCount ?: 0);
                    chatRecyclerView.adapter = chatAdapter
                    chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                    chatAdapter.notifyDataSetChanged()
                }
                catch (e:Exception){
                    e.printStackTrace()
                }

            }


        return view
    }

    private fun fetchLoggedInUsername() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val userId = it.uid // Unique identifier for the user
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        username = document.getString("name") ?: "Unknown"
                        uId = document.getString("uid") ?: "Unknown"
                    } else {
                        println("No such user document")
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error fetching username: ${exception.message}")
                }
        } ?: run {
            println("No logged-in user")
        }
    }

}
