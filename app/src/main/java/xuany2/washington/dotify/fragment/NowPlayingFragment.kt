package xuany2.washington.dotify.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_detail.*
import xuany2.washington.dotify.R
import kotlin.random.Random

class NowPlayingFragment: Fragment() {

    private var song: Song? = null
    private var numPlayed: Int = 0

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName

        const val ARG_SONG = "arg_song"
        const val NUM_PLAYED = "num_played"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }
    }

    fun updateSong(song: Song?) {
        if (song != null) {
            this.song = song
            updateSongViews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NUM_PLAYED, numPlayed)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username?.text = getString(R.string.userName)
        btnChangeUser?.text = getString(R.string.cannotChangeUserText)
        if (savedInstanceState == null) {
            numPlayed = Random.nextInt(0, 20001)
        } else {
            with(savedInstanceState) {
                numPlayed = getInt(NUM_PLAYED)
            }
        }
        updateSongViews()
        btnPlay.setOnClickListener{
            this.numPlayed++
            updateSongViews()
        }

    }

    private fun updateSongViews() {
        song?.let {
            album.setImageResource(it.largeImageID)
            songTitle.text = it.title
            playedTimes.text = getString(R.string.numTimePlay).format(numPlayed)
            artisteNameText.text = it.artist
        }
    }
}