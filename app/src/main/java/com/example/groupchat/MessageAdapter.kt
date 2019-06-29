package com.example.groupchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(messages : List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages : List<Message> = messages

    override fun getItemViewType(position: Int): Int {
        return if(messages[position].user == nickname){
            0
        } else{
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 0){
            return SentMessageVH(LayoutInflater.from(parent.context).inflate(R.layout.message_sent_layout,parent,false))
        }
        else{
            return ReceivedMessageVH(LayoutInflater.from(parent.context).inflate(R.layout.message_recived_layout,parent,false))
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currMessage : Message =  messages[position]

        when (getItemViewType(position)) {
            0 -> {
                (holder as SentMessageVH).message.text = currMessage.text
            }

            else -> {
                (holder as ReceivedMessageVH).nickname.text = currMessage.user
                (holder as ReceivedMessageVH).message.text = currMessage.text
            }

        }

    }

    class ReceivedMessageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickname : TextView
        val message : TextView

        init {
            nickname = itemView.findViewById(R.id.textview_nickname)
            message = itemView.findViewById(R.id.textview_message)
        }
    }

    class SentMessageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message : TextView

        init {
            message = itemView.findViewById(R.id.textview_message)
        }
    }

}