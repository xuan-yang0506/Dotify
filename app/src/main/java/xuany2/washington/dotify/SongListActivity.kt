package xuany2.washington.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*
import xuany2.washington.dotify.MainActivity.Companion.SONG_KEY
import java.util.Collections.shuffle

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"
        var currentSong: Song? = null

        val listOfSong = SongDataProvider.getAllSongs().toMutableList()

        val songAdapter = SongListAdapter(listOfSong)

        songAdapter.onSongClickListener = {
            textNowPlaying.text = "${it.title} - ${it.artist}"
            currentSong = it
        }


        btnShuffle.setOnClickListener{
            val newListOfSong = listOfSong.apply {shuffle()}
            songAdapter.shuffle(newListOfSong)
        }

        nowPlayingSection.setOnClickListener{
            if (currentSong == null) {
                Toast.makeText(this, "No song is currently playing", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(SONG_KEY, currentSong)
                startActivity(intent)
            }
        }

        rvSong.adapter = songAdapter
    }
}
