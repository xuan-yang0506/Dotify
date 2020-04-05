package xuany2.washington.dotify

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val songs = mapOf(
        0 to "Only My Railgun",
        1 to "Warriors",
        2 to "RISE"
    )

    val songImage = mapOf(
        0 to R.drawable.railgun,
        1 to R.drawable.warriors,
        2 to R.drawable.rise
    )

    val users = mutableMapOf(
        "user1" to mutableMapOf(
            0 to 0,
            1 to 0,
            2 to 0
        )
    )

    var DEFAULT_PARAMS = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

    var current = -1
    var currentUser = "user1"
    var timePlayedMap = users[currentUser]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    fun setup() {
        for (i in 0..3) {
            val randomNumber = Random.nextInt(1, 1000)
            timePlayedMap?.put(i, randomNumber)
        }
        username.text = "user1"
        btnChangeUser.text = "CHANGE USER"
        current = Random.nextInt(0, 3)
        btnPrevious.setOnClickListener{previousBtn: View -> previous(previousBtn)}
        btnNext.setOnClickListener{v: View -> next(v)}
        btnPlay.setOnClickListener{v: View -> play(v)}
        btnChangeUser.setOnClickListener{v: View -> changeUser(v)}
        showSong()
    }

    fun changeUser(view: View) {
        var editText = EditText(this)
        var editId = View.generateViewId()
        editText.id = editId
        var btnApply = Button(this)
        btnApply.text = "Apply"
        userSection.removeAllViews()
        editText.layoutParams = DEFAULT_PARAMS
        btnApply.layoutParams = DEFAULT_PARAMS
        userSection.addView(editText)
        userSection.addView(btnApply)
        btnApply.setOnClickListener{v: View -> applyChange(v, editId)}
    }

    fun applyChange(view: View, editID: Int) {
        var input = findViewById<EditText>(editID)
        var inputString: String = input.text.toString().trim()
        if (inputString.isEmpty()) {
            Toast.makeText(this,
                "User name can't be blank", Toast.LENGTH_SHORT).show()
        } else {
            if (users.containsKey(inputString)) {
                currentUser = inputString
                timePlayedMap = users.get(inputString)
                Toast.makeText(this,
                    "Welcome back, $inputString!", Toast.LENGTH_SHORT).show()
                restoreUserSection()
            } else {
                var newUserMap = mutableMapOf(
                    0 to 0,
                    1 to 0,
                    2 to 0
                )
                Toast.makeText(this,
                    "Welcome, new user \"$inputString\"!", Toast.LENGTH_SHORT).show()
                users.put(inputString, newUserMap)
                timePlayedMap = newUserMap
                currentUser = inputString
                restoreUserSection()
            }
        }
    }

    fun restoreUserSection() {
        userSection.removeAllViews()
        var text = TextView(this)
        text.text = currentUser
        text.layoutParams = DEFAULT_PARAMS
        var changeUserButton = Button(this)
        changeUserButton.layoutParams = DEFAULT_PARAMS
        changeUserButton.text = "CHANGE USER"
        changeUserButton.setOnClickListener{v: View -> changeUser(v)}
        userSection.addView(text)
        userSection.addView(changeUserButton)
        showSong()
    }

    fun showSong() {
        val songName = songs[current]
        songTitle.text = songName
        val time = timePlayedMap?.get(current)
        playedTimes.text = "$time plays"
        val albumName = songImage[current]
        album.setImageResource(songImage[current]?:0)
    }

    fun play(view: View) {
        var currentTimePlayed: Int = timePlayedMap?.get(current) ?:0
        currentTimePlayed++
        timePlayedMap?.put(current, currentTimePlayed)
        showSong()
    }

    fun next(view: View) {
        if (current == 2) {
            current = 0
        } else {
            current++
        }
        Toast.makeText(this, "“Skipping to next track", Toast.LENGTH_SHORT).show()
        showSong()
    }

    fun previous(view: View) {
        if (current == 0) {
            current = 2
        } else {
            current--;
        }
        Toast.makeText(this, "“Skipping to previous track", Toast.LENGTH_SHORT).show()
        showSong()
    }
}
