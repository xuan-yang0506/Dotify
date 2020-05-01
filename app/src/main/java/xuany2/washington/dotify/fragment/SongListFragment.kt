package xuany2.washington.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*
import xuany2.washington.dotify.R
import xuany2.washington.dotify.SongListAdapter

class SongListFragment: Fragment() {
    private lateinit var songListAdaptor: SongListAdapter
    private var onSongClickedListener: OnSongClickListener? = null
    private var songList: MutableList<Song>? = null

    companion object {
        const val ARG_SONG_LIST: String = "arg_song_list"
        const val SONG_LIST: String = "song_list"
        val TAG: String = SongListFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(SONG_LIST) as MutableList<Song>
            }
        } else {
            arguments?.let { args ->
                songList = args.getParcelableArrayList<Song>(ARG_SONG_LIST) as MutableList<Song>
            }
        }
        songListAdaptor = SongListAdapter(songList!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(SONG_LIST, ArrayList<Song>(songList!!))
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSong.adapter = songListAdaptor

        songListAdaptor.onSongClickListener = {song ->
            onSongClickedListener?.onSongClicked(song)
        }
    }

    fun shuffleList(newListOfSong: MutableList<Song>) {
        this.songList = newListOfSong
        songListAdaptor.shuffle(newListOfSong)
    }
}


interface OnSongClickListener {
    fun onSongClicked(song: Song)
}