package com.pgr301.dev

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : CrudRepository<Song, Long>