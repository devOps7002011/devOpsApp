package com.pgr301.musicrest

import com.pgr301.dev.MusicApp
import com.pgr301.dev.Song
import com.pgr301.dev.SongDto
import com.pgr301.dev.SongRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(MusicApp::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicRestTest{

    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var repository: SongRepository


    @Before
    @After
    fun clean(){

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/songs"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        repository.run {
            deleteAll()
            save(Song("Song 1", "Disco", 2000))
            save(Song("Song 2", "R&B", 2001))
            save(Song("Song 3", "Disco", 1979))
            save(Song("Song 4", "Techno", 1988))
            save(Song("Song 5", "Pop", 1979))

        }

    }

    @Test
    fun testGetAll(){

        RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(5))
    }

    @Test
    fun testNoGetSong(){
        RestAssured.given().accept(ContentType.JSON)
                .get("/-3")
                .then()
                .statusCode(404)
                .body("code", CoreMatchers.equalTo(404))
                .body("message", CoreMatchers.not(CoreMatchers.equalTo(null)))

    }

    @Test
    fun testDeleteAllBooks() {

        val songs = RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(5))
                .extract().body().jsonPath().getList("data", SongDto::class.java)

        for (s in  songs) {

            RestAssured.given().accept(ContentType.JSON)
                    .delete("/${s.id}")
                    .then()
                    .statusCode(204)
        }

        RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(0))
    }


}