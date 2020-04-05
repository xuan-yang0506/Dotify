package xuany2.washington.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val songs = mapOf(
        0 to "Only My Railgun",
        1 to "Warriors",
        2 to "RISE"
    )

    val timePlayedMap = mutableMapOf(
        0 to 0,
        1 to 1,
        2 to 2
    )
    val songImage = mapOf(
        0 to R.drawable.railgun,
        1 to R.drawable.warriors,
        2 to R.drawable.rise
    )

    var current = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    fun setup() {
        for (i in 0..3) {
            val randomNumber = Random.nextInt(1, 1000)
            timePlayedMap.put(i, randomNumber)
        }
        username.text = "user1"
        btnChangeUser.text = "CHANGE USER"
        current = Random.nextInt(0, 3)
        btnPrevious.setOnClickListener{previousBtn: View -> previous(previousBtn)}
        btnNext.setOnClickListener{nextBtn: View -> next(nextBtn)}
        btnPlay.setOnClickListener{playBtn: View -> play(playBtn)}
        showSong()
    }

    fun showSong() {
        val songName = songs[current]
        songTitle.text = songName
        val time = timePlayedMap[current]
        playedTimes.text = "$time plays"
        val albumName = songImage[current]
        album.setImageResource(songImage[current]?:0)
    }

    fun play(view: View) {
        var currentTimePlayed: Int = timePlayedMap[current]?:0
        currentTimePlayed++
        timePlayedMap.put(current, currentTimePlayed)
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
