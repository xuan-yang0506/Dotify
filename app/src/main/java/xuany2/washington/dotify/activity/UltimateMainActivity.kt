package xuany2.washington.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_ultimate_main.*
import xuany2.washington.dotify.R
import xuany2.washington.dotify.fragment.OnSongClickListener
import xuany2.washington.dotify.fragment.NowPlayingFragment
import xuany2.washington.dotify.fragment.SongListFragment

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    companion object {
        const val CURRENT_SONG: String = "current_song"
        const val SONG_LIST: String = "SONG_LIST"
    }

    private var currentSong:Song? = null
    private lateinit var listOfSong: MutableList<Song>

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_SONG, currentSong)
        outState.putParcelableArrayList(SONG_LIST, listOfSong as ArrayList<Song>)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                listOfSong = getParcelableArrayList<Song>(SONG_LIST) as MutableList<Song>
            }
        } else {
            listOfSong = SongDataProvider.getAllSongs().toMutableList()
        }
        setContentView(R.layout.activity_ultimate_main)

        if (supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
            var songListFragment = SongListFragment()
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_SONG_LIST, ArrayList(listOfSong))
            }
            songListFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }


        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            nowPlayingSection.visibility = View.INVISIBLE
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                nowPlayingSection.visibility = View.INVISIBLE
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                nowPlayingSection.visibility = View.VISIBLE
            }
        }

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currentSong = getParcelable(CURRENT_SONG)
            }
            if (currentSong != null) {
                onSongClicked(currentSong!!)
            }
        }

        nowPlayingSection.setOnClickListener {
            if (currentSong == null) {
                Toast.makeText(this, getString(R.string.noSongPlaying), Toast.LENGTH_SHORT).show()
            } else {
                var songDetailFragment = getSongDetailFragment()
                if (songDetailFragment == null) {
                    songDetailFragment = NowPlayingFragment()
                    val argumentBundle = Bundle().apply {
                        putParcelable(NowPlayingFragment.ARG_SONG, currentSong)
                    }
                    songDetailFragment.arguments = argumentBundle

                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragContainer, songDetailFragment, NowPlayingFragment.TAG)
                        .addToBackStack(NowPlayingFragment.TAG)
                        .commit()
                } else {
                    songDetailFragment.updateSong(currentSong)
                }
            }
        }

        btnShuffle.setOnClickListener {
            listOfSong.apply { shuffle() }
            var songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as SongListFragment
            songListFragment.shuffleList(listOfSong)
        }

    }

    private fun getSongDetailFragment() = supportFragmentManager
                                            .findFragmentByTag(NowPlayingFragment.TAG)
                                            as? NowPlayingFragment


    override fun onSongClicked(song: Song) {
        textNowPlaying.text = getString(R.string.nowPlayingText).format(song.title, song.artist)
        this.currentSong = song
    }
}
