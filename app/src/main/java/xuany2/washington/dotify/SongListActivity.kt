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

        title = getString(R.string.allSong)
        textNowPlaying.text = getString(R.string.defaultNowPlaying)
        var currentSong: Song? = null

        // get all songs from data provider
        val listOfSong = SongDataProvider.getAllSongs().toMutableList()

        val songAdapter = SongListAdapter(listOfSong)

        // set onclick event for any song
        // change the text in now playing section
        songAdapter.onSongClickListener = {
            textNowPlaying.text = getString(R.string.nowPlayingText).format(it.title, it.artist)
            currentSong = it
        }

        // set onclick event for the shuffle button
        // when a user clicks on the shuffle button, the list of
        // songs will be shuffled
        btnShuffle.setOnClickListener{
            val newListOfSong = listOfSong.apply {shuffle()}
            // pass the shuffled list to adapter
            songAdapter.shuffle(newListOfSong)
        }

        // set onclick event for now playing section
        // switch to a new activity when clicking on now playing section
        nowPlayingSection.setOnClickListener{
            // if no song is playing, ask the user to play a song first
            if (currentSong == null) {
                Toast.makeText(this, getString(R.string.noSongPlaying), Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(SONG_KEY, currentSong)
                startActivity(intent)
            }
        }

        rvSong.adapter = songAdapter
    }
}
