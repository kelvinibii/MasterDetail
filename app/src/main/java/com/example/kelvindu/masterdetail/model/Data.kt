package com.example.kelvindu.masterdetail.model

/**
 * Created by KelvinDu on 10/17/2017.
 */
data class Post(val userId: Long,
                val id: Long,
                val title: String,
                val body: String)

data class Position(val post_id: Long,
                    val latitude: String,
                    val longitude: String,
                    val timestamp: String)

object PostTable {
    val TABLE_NAME = "post"
    val USER_ID = "userId"
    val ID = "id"
    val TITLE = "title"
    val BODY = "body"
}

object PositionTable {
    val TABLE_NAME = "position"
    val POST_ID = "post_id"
    val LATITUDE = "latitude";
    val LONGITUDE = "longitude"
    val TIMESTAMP ="timestamp"
}