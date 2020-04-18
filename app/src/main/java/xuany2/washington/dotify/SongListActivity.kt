package xuany2.washington.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*
import java.util.Collections.shuffle

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"

        var listOfSong = SongDataProvider.getAllSongs().toMutableList()

        var songAdapter = SongListAdapter(listOfSong)

        songAdapter.onSongClickListener = {
            textNowPlaying.text = "${it.title} - ${it.artist}"
        }


        btnShuffle.setOnClickListener{
            val newListOfSong = listOfSong.apply {shuffle()}
            songAdapter.shuffle(newListOfSong)
        }

        rvSong.adapter = songAdapter
    }
}
