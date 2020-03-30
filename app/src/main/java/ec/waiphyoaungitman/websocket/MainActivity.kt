package ec.waiphyoaungitman.websocket
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
class MainActivity : AppCompatActivity() {
    private var webSocket: WebSocket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instantiateWebSocket()
        send.setOnClickListener {
            val message = messageBox.text.toString()
            if (!message.isEmpty()) {
                webSocket!!.send(message)
                messageBox.setText("")
            }
        }
    }
    private fun instantiateWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.43.207:8080").build()
        val socketListener = SocketListener(this)
        webSocket = client.newWebSocket(request, socketListener)
    }
    inner class SocketListener(var activity: MainActivity) : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            activity.runOnUiThread { Toast.makeText(activity, "Success!", Toast.LENGTH_LONG).show() }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            activity.runOnUiThread { showDialog(text) }
        }

    }
    fun showDialog(message: String?) {
        val builder = AlertDialog.Builder(this)
        val alertDialog = builder.setTitle("New Message Received")
                .setTitle("your friend send")
                .setNegativeButton("Ok") { _, which ->}
                .create()

        if (alertDialog.isShowing) {
            alertDialog.setMessage(message)
        } else {
            alertDialog.setMessage(message)
            alertDialog.show()
        }
    }
}