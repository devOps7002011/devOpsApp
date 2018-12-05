package com.pgr301.dev


object SongConverter{


    fun transform(song: Song) : SongDto{

        return SongDto(
                title = song.title,
                genre = song.genre,
                year = song.year,
                id = song.id.toString()
        )
    }

    fun transform(songs: Iterable<Song>) : List<SongDto>{

        return songs.map { transform(it) }
    }



}