package com.pgr301.dev.musicservice.api

import com.pgr301.dev.musicservice.dto.SongConverter
import com.pgr301.dev.musicservice.dto.SongDto
import com.pgr301.dev.musicservice.entity.Song
import com.pgr301.dev.musicservice.repository.SongRepository
import com.pgr301.dev.rest.exception.RestResponseFactory
import com.pgr301.dev.rest.exception.WrappedResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI


@Api(value = "/songs", description = "Handling of creating and retrieving song entries")
@RequestMapping(path = ["/songs"])
@RestController
@Validated
//Enable CORS for all endpoints in this REST controller for the frontend
@CrossOrigin(origins = ["http://localhost:8080"])
class MusicRestApiR(val repository: SongRepository){

    @ApiOperation("Get all the songs")
    @GetMapping
    fun getAll(): ResponseEntity<WrappedResponse<List<SongDto>>> {

        return ResponseEntity.status(200).body(
                WrappedResponse(
                        code = 200,
                        data = SongConverter.transform(repository.findAll()))
                        .validated()
        )
    }

    @ApiOperation("Create a new song")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun create(
            @ApiParam("Data for new song")
            @RequestBody
            dto: SongDto
    ): ResponseEntity<WrappedResponse<Void>> {

        if (dto.id != null) {
            return RestResponseFactory.userFailure("Cannot specify an id when creating a new song")
        }

        val entity = Song(dto.title!!, dto.genre!!, dto.year!!)
        repository.save(entity)

        return RestResponseFactory.created(URI.create("/songs/" + entity.id))
    }

    @ApiOperation("Get a specific song, by id")
    @GetMapping(path = ["/{id}"])
    fun getById(
            @ApiParam("The id of the song")
            @PathVariable("id")
            pathId: String
    ): ResponseEntity<WrappedResponse<SongDto>> {

        val id: Long
        try {
            id = pathId.toLong()
        } catch (e: Exception) {
            return RestResponseFactory.userFailure("Invalid id '$pathId'")
        }

        val song = repository.findById(id).orElse(null)
                ?: return RestResponseFactory.notFound(
                        "The requested song with id '$id' is not in the database")

        return RestResponseFactory.payload(200, SongConverter.transform(song))
    }

    @ApiOperation("Delete a specific song, by id")
    @DeleteMapping(path = ["/{id}"])
    fun deleteById(
            @ApiParam("The id of the song")
            @PathVariable("id")
            pathId: String
    ): ResponseEntity<WrappedResponse<Void>> {

        val id: Long
        try {
            id = pathId.toLong()
        } catch (e: Exception) {
            return RestResponseFactory.userFailure("Invalid id '$pathId'")
        }

        if (!repository.existsById(id)) {
            return RestResponseFactory.notFound(
                    "The requested song with id '$id' is not in the database")
        }

        repository.deleteById(id)

        return RestResponseFactory.noPayload(204)
    }


    @ApiOperation("Update a specific song")
    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateById(
            @ApiParam("The id of the song")
            @PathVariable("id")
            pathId: String,
            //
            @ApiParam("New data for updating the book")
            @RequestBody
            dto: SongDto
    ): ResponseEntity<WrappedResponse<Void>> {

        val id: Long
        try {
            id = pathId.toLong()
        } catch (e: Exception) {
            return RestResponseFactory.userFailure("Invalid id '$pathId'")
        }

        if(dto.id == null){
            return RestResponseFactory.userFailure("Missing id JSON payload")
        }

        if(dto.id != pathId){
            return RestResponseFactory.userFailure("Inconsistent id between URL and JSON payload", 409)
        }

        val entity = repository.findById(id).orElse(null)
                ?: return RestResponseFactory.notFound(
                        "The requested song with id '$id' is not in the database. " +
                                "This PUT operation will not create it.")

        entity.title = dto.title!!
        entity.genre = dto.genre!!
        entity.year = dto.year!!

        repository.save(entity)

        return RestResponseFactory.noPayload(204)
    }



}