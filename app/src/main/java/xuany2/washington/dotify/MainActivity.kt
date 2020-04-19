package xuany2.washington.dotify

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    companion object {
        // Keys for intents
        const val SONG_KEY = "song_key"
    }

    var songs = mapOf<Int, String>()
    var artistes = mapOf<Int, String>()
    var songImage = mapOf<Int, Int>()
    var users = mutableMapOf<String, MutableMap<Int, Int>>()

    var DEFAULT_PARAMS = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

    var current = -1
    var currentUser = ""
    var timePlayedMap: MutableMap<Int, Int>? = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setup()
        setup2()
    }

    fun setup2() {
        val song: Song = intent.getParcelableExtra<Song>(SONG_KEY)
        val songName = song.title
        songTitle.text = songName
        val time = Random.nextInt(0, 10032)
        playedTimes.text = "$time plays"
        album.setImageResource(song.largeImageID)
        val artisteName = song.artist
        artisteNameText.text = artisteName
        username.text = "Misaka 10032"
        btnChangeUser.text = "Can't change user in this mode"
    }

    fun setup() {
        songs = mapOf(
            0 to resources.getString(R.string.songName0),
            1 to resources.getString(R.string.songName1),
            2 to resources.getString(R.string.songName2),
            3 to resources.getString(R.string.songName3)
        )

        artistes = mapOf(
            0 to resources.getString(R.string.singer0),
            1 to resources.getString(R.string.singer1),
            2 to resources.getString(R.string.singer2),
            3 to resources.getString(R.string.singer3)
        )

        songImage = mapOf(
            0 to R.drawable.railgun,
            1 to R.drawable.warriors,
            2 to R.drawable.rise,
            3 to R.drawable.lemon
        )

        users = mutableMapOf(
            resources.getString(R.string.defaultUserName) to mutableMapOf(
                0 to 0,
                1 to 0,
                2 to 0,
                3 to 0
            )
        )

        currentUser = resources.getString(R.string.defaultUserName)
        timePlayedMap = users.get(currentUser)

        for (i in 0..4) {
            val randomNumber = Random.nextInt(1, 1000)
            timePlayedMap?.put(i, randomNumber)
        }
        username.text = resources.getString(R.string.defaultUserName)
        btnChangeUser.text = resources.getString(R.string.changeUser)
        current = Random.nextInt(0, 4)
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
        btnApply.text = resources.getString(R.string.apply)
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
                resources.getString(R.string.blankUserInput), Toast.LENGTH_SHORT).show()
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
                    2 to 0,
                    3 to 0
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
        val artisteName = artistes[current]
        artisteNameText.text = artisteName
    }

    fun play(view: View) {
        var currentTimePlayed: Int = timePlayedMap?.get(current) ?:0
        currentTimePlayed++
        timePlayedMap?.put(current, currentTimePlayed)
        showSong()
    }

    fun next(view: View) {
        if (current == 3) {
            current = 0
        } else {
            current++
        }
        Toast.makeText(this, resources.getText(R.string.nextSong), Toast.LENGTH_SHORT).show()
        showSong()
    }

    fun previous(view: View) {
        if (current == 0) {
            current = 3
        } else {
            current--;
        }
        Toast.makeText(this, resources.getText(R.string.previousSong), Toast.LENGTH_SHORT).show()
        showSong()
    }
}
