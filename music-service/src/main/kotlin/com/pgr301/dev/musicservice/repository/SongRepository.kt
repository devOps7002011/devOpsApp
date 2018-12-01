package com.pgr301.dev.musicservice.repository

import com.pgr301.dev.musicservice.entity.Song
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface SongRepository : CrudRepository<Song, Long>