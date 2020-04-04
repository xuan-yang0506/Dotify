package xuany2.washington.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val songs = mapOf(
        0 to "Only My Railgun",
        1 to "Warriors",
        2 to "RISE"
    )

//    val songImage = mapOf(
//        0 to "railgun",
//        1 to "warriors",
//        2 to "rise"
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    fun setup() {
        username.text = "user1"
        btnChangeUser.text = "CHANGE USER"
        val songIndex = Random.nextInt(0, 3)
        val songName = songs[songIndex]
        songTitle.text = songName
        val time = Random.nextInt(1, 1000)
        playedTimes.text = "$time plays"
//        val albumName = songImage[songIndex]
        if (songIndex == 0) {
            album.setImageResource(R.drawable.railgun)
        } else if (songIndex == 1) {
            album.setImageResource(R.drawable.warriors)
        } else {
            album.setImageResource(R.drawable.rise)
        }
    }
}
