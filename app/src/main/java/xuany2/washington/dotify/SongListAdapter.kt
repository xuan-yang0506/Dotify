package xuany2.washington.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private val initialListOfSongs: MutableList<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs: MutableList<Song> = initialListOfSongs.toMutableList()
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    fun shuffle(shuffledList: MutableList<Song>) {
        listOfSongs = shuffledList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SongListAdapter.SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song)
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle = itemView.findViewById<TextView>(R.id.songTitle)
        private val songArtist = itemView.findViewById<TextView>(R.id.songArtistName)
        private val songImage = itemView.findViewById<ImageView>(R.id.album)

        fun bind(song: Song) {
            songTitle.text = song.title
            songArtist.text = song.artist
            songImage.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}