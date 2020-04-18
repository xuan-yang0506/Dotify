package xuany2.washington.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)

        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongListAdapter.SongViewHolder, position: Int) {
        val song = listOfSongs[position]
        holder.bind(song.title, song.smallImageID)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle = itemView.findViewById<TextView>(R.id.songTitle)
        private val songImage = itemView.findViewById<ImageView>(R.id.album)

        fun bind(name: String, album: Int) {
            songTitle.text = name
            songImage.setImageResource(album)
        }
    }
}