package com.example.groupchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_nickname.setOnClickListener {
            val nickname = editText_nickname.text.toString()
            val roomId = editText_roomId.text.toString()

            if(nickname.isNotBlank() && roomId.isNotBlank()){
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("nickname", nickname)
                intent.putExtra("roomid", roomId)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Fields can't be blank!!",Toast.LENGTH_LONG).show()
            }

        }

    }
}
