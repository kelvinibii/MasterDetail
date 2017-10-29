package com.example.kelvindu.masterdetail.db

import com.example.kelvindu.masterdetail.App
import com.example.kelvindu.masterdetail.model.Position
import com.example.kelvindu.masterdetail.model.PositionTable
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.model.PostTable
import io.reactivex.Observable
import org.jetbrains.anko.db.*
import timber.log.Timber

/**
 * Created by KelvinDu on 10/17/2017.
 */
class MainDOImpl(var dbHelper: MainDBHelper = MainDBHelper(App.instance)): MainDO {
    override fun insertPost(feed: Post): Boolean = dbHelper.use {
        try {
            beginTransaction()
            val inserting = insert(PostTable.TABLE_NAME,
                    PostTable.ID to feed.id,
                    PostTable.USER_ID to feed.userId,
                    PostTable.TITLE to feed.title,
                    PostTable.BODY to feed.body)
            if (inserting != -1L){
                setTransactionSuccessful()
                true
            } else {
                false
            }
        } finally {
            endTransaction()
        }
    }

    override fun getCount(): Int{
        val listPosts = ArrayList<Post>()
        dbHelper.use {
            select(PostTable.TABLE_NAME).parseList(object: MapRowParser<List<Post>>{
                override fun parseRow(columns: Map<String, Any?>): List<Post> {
                    val p = Post(id = columns[PostTable.ID] as Long,
                            userId = columns[PostTable.USER_ID] as Long,
                            title = columns[PostTable.TITLE] as String,
                            body = columns[PostTable.BODY] as String)
                    listPosts.add(p)
                    return listPosts
                }
            })
        }
        return listPosts.size

    }

    override fun loadPost(): Observable<List<Post>> {
        val listPosts = ArrayList<Post>()
        dbHelper.use {
            select(PostTable.TABLE_NAME).parseList(object: MapRowParser<List<Post>>{
                override fun parseRow(columns: Map<String, Any?>): List<Post> {
                    val p = Post(id = columns.getValue(PostTable.ID) as Long,
                            userId = columns.getValue(PostTable.USER_ID) as Long,
                            title = columns.getValue(PostTable.TITLE) as String,
                            body = columns.getValue(PostTable.BODY) as String)
                    listPosts.add(p)
                    return listPosts
                }
            })
        }
        return Observable.just(listPosts)
    }

    override fun loadById(id: Long): Observable<Post> {
        var post: Post? = null
        dbHelper.use {
            select(PostTable.TABLE_NAME)
                    .whereArgs("${PostTable.ID} = {post_id}", "post_id" to id).parseSingle(object: MapRowParser<Post>{
                override fun parseRow(columns: Map<String, Any?>): Post {
                    val p = Post(id = columns.getValue(PostTable.ID) as Long,
                            userId = columns.getValue(PostTable.USER_ID) as Long,
                            title = columns.getValue(PostTable.TITLE) as String,
                            body = columns.getValue(PostTable.BODY) as String)
                    post = p
                    return p
                }
            })
        }
        return Observable.just(post)
    }

    override fun insertPosition(feed: Position): Boolean = dbHelper.use {
        try {
            beginTransaction()
            val inserting = insert(PositionTable.TABLE_NAME,
                    PositionTable.POST_ID to feed.post_id,
                    PositionTable.LATITUDE to feed.latitude,
                    PositionTable.LONGITUDE to feed.longitude,
                    PositionTable.TIMESTAMP to feed.timestamp)
            if (inserting != -1L){
                setTransactionSuccessful()
                Timber.d("Insert Successfull")
                true
            } else {
                false
            }
        } finally {
            endTransaction()
        }
    }

    override fun updatePosition(position: Position){
        dbHelper.use {
            update(PositionTable.TABLE_NAME,
                    PositionTable.LATITUDE to position.latitude,
                    PositionTable.LONGITUDE to position.longitude,
                    PositionTable.TIMESTAMP to position.timestamp)
                    .whereArgs("${PositionTable.POST_ID} = {post_id}", "post_id" to position.post_id)
                    .exec()
        }
    }

    override fun loadPosition(post: Long): Observable<Position> {
        val position = dbHelper.use {
            select(PositionTable.TABLE_NAME)
                    .whereArgs("${PositionTable.POST_ID} = {post_id}", "post_id" to post)
                    .parseOpt(object: MapRowParser<Position>{
                        override fun parseRow(columns: Map<String, Any?>): Position {
                            val p = Position(latitude = columns.getValue(PositionTable.LATITUDE) as String,
                                    longitude = columns.getValue(PositionTable.LONGITUDE) as String,
                                    timestamp = columns.getValue(PositionTable.TIMESTAMP) as String,
                                    post_id =  columns.getValue(PositionTable.POST_ID) as Long)
                            return p
                        }
                    })
        }
        if (position == null)
            return Observable.empty()
        else
            return Observable.just(position)
    }

    override fun checkAvailablePosition(post_id: Long): Boolean {
        val isExist = dbHelper.use {
            select(PositionTable.TABLE_NAME)
                    .whereArgs("${PositionTable.POST_ID} = {post_id}", "post_id" to post_id)
                    .parseOpt(object: MapRowParser<Position>{
                        override fun parseRow(columns: Map<String, Any?>): Position {
                            val p = Position(latitude = columns.getValue(PositionTable.LATITUDE) as String,
                                    longitude = columns.getValue(PositionTable.LONGITUDE) as String,
                                    timestamp = columns.getValue(PositionTable.TIMESTAMP) as String,
                                    post_id =  columns.getValue(PositionTable.POST_ID) as Long)
                            return p
                        }
                    })
        }
        return isExist != null
    }
}