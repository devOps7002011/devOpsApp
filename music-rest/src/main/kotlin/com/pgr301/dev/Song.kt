package com.pgr301.dev

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Song(


        @get:NotBlank
        @get:Size(max = 256)
        var title: String,

        @get:NotBlank
        @get:Size(max = 256)
        var genre: String,

        @get:Max(2025) @get:NotNull
        var year: Int,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null

)