package com.example.groupchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_chat.*
import java.net.URISyntaxException
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

lateinit var nickname : String

class ChatActivity : AppCompatActivity() {

    lateinit var roomId : String
    lateinit var recyclerView : RecyclerView
    lateinit var socket : Socket
    lateinit var msg : ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        nickname = intent.getStringExtra("nickname")
        roomId = intent.getStringExtra("roomid")


        msg = ArrayList()
        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapter = MessageAdapter(msg)
        recyclerView.adapter = adapter


        try {
            socket = IO.socket("https://chat-socket-servr.herokuapp.com/")
            socket.connect()
        }
        catch (e : URISyntaxException) {
            e.printStackTrace()
        }

        socket.emit("join-room",roomId)
        socket.emit("join",nickname)

        button_send.setOnClickListener {

            val message = editText_message.text.toString()

            if(message.isNotBlank()){
                socket.emit("messagedetection",nickname,message,roomId)
                editText_message.text.clear()
            }
        }

        //implementing socket listeners
        socket.on("userjoinedthechat") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatActivity,data,Toast.LENGTH_LONG).show()
            }
        }
        socket.on("userdisconnect") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatActivity, data, Toast.LENGTH_SHORT).show()
            }
        }

        socket.on("message") { args ->
            runOnUiThread {
                val data = args[0] as JSONObject

                try{
                    val nickname = data.getString("senderNickname")
                    val message = data.getString("message")
                    val m = Message(message, nickname,0)
                    msg.add(m)
                    adapter.notifyItemInserted(msg.size-1)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}

