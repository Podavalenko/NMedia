// package com.example.nmedia.dao
//
// import android.content.ContentValues
// import android.database.Cursor
// import android.database.sqlite.SQLiteCursor
// import android.database.sqlite.SQLiteDatabase
// import com.example.nmedia.Post
//
// class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
// companion object {
// val DDL = """
// CREATE TABLE ${PostColumns.TABLE} (
// ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
// ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
// ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
// ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
// ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
// ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
// ${PostColumns.COLUMN_REPOSTS} INTEGER NOT NULL DEFAULT 0,
// ${PostColumns.COLUMN_VIDEO_URL} TEXT NULL
// );
// """.trimIndent()
// }
//
// object PostColumns {
// const val TABLE = "posts"
// const val COLUMN_ID = "id"
// const val COLUMN_AUTHOR = "author"
// const val COLUMN_CONTENT = "content"
// const val COLUMN_PUBLISHED = "published"
// const val COLUMN_LIKED_BY_ME = "likedByMe"
// const val COLUMN_LIKES = "likes"
// const val COLUMN_REPOSTS = "reposts"
// const val COLUMN_VIDEO_URL = "videoUrl"
// val ALL_COLUMNS = arrayOf(
// COLUMN_ID,
// COLUMN_AUTHOR,
// COLUMN_CONTENT,
// COLUMN_PUBLISHED,
// COLUMN_LIKED_BY_ME,
// COLUMN_LIKES,
// COLUMN_REPOSTS,
// COLUMN_VIDEO_URL
// )
// }
//
// override fun getAll(): List<Post> {
// val posts = mutableListOf<Post>()
// db.query(
// PostColumns.TABLE,
// PostColumns.ALL_COLUMNS,
// null,
// null,
// null,
// null,
// "${PostColumns.COLUMN_ID} DESC"
// ).use {
// while (it.moveToNext()) {
// posts.add(map(it))
// }
// }
// return posts
// }
//
// override fun likeById(id: Long) {
// db.execSQL(
// """
// UPDATE posts SET
// likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
// likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
// WHERE id = ?;
// """.trimIndent(), arrayOf(id)
// )
// }
//
// override fun repostById(id: Long) {
// db.execSQL(
// """
// UPDATE posts SET
// reposts = reposts + 1
// WHERE id = ?;
// """.trimIndent(), arrayOf(id)
// )
// }
//
// override fun removeById(id: Long) {
// db.delete(
// PostColumns.TABLE,
// "${PostColumns.COLUMN_ID} = ?",
// arrayOf(id.toString())
// )
// }
//
// override fun save(post: Post): Post {
// val values = ContentValues().apply {
// if (post.id != 0L) {
// put(PostColumns.COLUMN_ID, post.id)
// }
// put(PostColumns.COLUMN_AUTHOR, "Me")
// put(PostColumns.COLUMN_CONTENT, post.content)
// put(PostColumns.COLUMN_PUBLISHED, "now")
// }
// val id = db.replace(PostColumns.TABLE, null, values)
// db.query(
// PostColumns.TABLE,
// PostColumns.ALL_COLUMNS,
// "${PostColumns.COLUMN_ID} = ?",
// arrayOf(id.toString()),
// null,
// null,
// null,
// ).use {
// it.moveToNext()
// return map(it)
// }
// }
//
// override fun video() {
//
// }
//
// private fun map(cursor: Cursor): Post {
// with(cursor) {
// return Post(
// id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
// author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
// content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
// published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
// likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
// likes = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
// reposts = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_REPOSTS)),
// videoUrl = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO_URL))
// )
// }
// }