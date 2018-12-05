package com.pgr301.dev

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

data class SongDto(


        @ApiModelProperty("The id of the song")
        @get:NotNull
        var id: String? = null,

        @ApiModelProperty("The Title of the song")
        @get:NotNull
        var title: String? = null,

        @ApiModelProperty("The genre of the song")
        @get:NotNull
        var genre: String? = null,

        @ApiModelProperty("The year of the song")
        @get:NotNull
        var year: Int? = null

)