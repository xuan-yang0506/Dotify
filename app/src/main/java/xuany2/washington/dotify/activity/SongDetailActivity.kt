package xuany2.washington.dotify.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_song_detail.*
import xuany2.washington.dotify.R
import kotlin.random.Random


class SongDetailActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_song_detail)
        setup()
    }

    private fun setup() {
        val song: Song = intent.getParcelableExtra<Song>(SONG_KEY)
        val songName = song.title
        songTitle.text = songName
        var time = Random.nextInt(0, 10032)
        playedTimes.text = getString(R.string.numTimePlay).format(time)
        album.setImageResource(song.largeImageID)
        val artisteName = song.artist
        artisteNameText.text = artisteName
        username.text = getString(R.string.userName)
        btnChangeUser.text = getString(R.string.cannotChangeUserText)
        btnPlay.setOnClickListener{
            playedTimes.text = getString(R.string.numTimePlay).format(++time)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
