package com.turbo.turbomotores.library;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	// Database fields
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumnsArticle = {"_id", "title", "url", "excerpt", "text", "author", "imageUrl", "date"};
	private String[] allColumnsCategory = {"_id", "name"};
	private String[] allColumnsTag = {"_id", "name"};
	private String[] allColumnsArticleCategory = {"_id", "articleId", "categoryId"};
	private String[] allColumnsArticleTag = {"_id", "articleId", "tagId"};

	public DBManager(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Article createArticle(String title, String url, String excerpt, String text, String author, String imageUrl, String date) {
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("url", url);
		values.put("excerpt", excerpt);
		values.put("text", text);
		values.put("author", author);
		values.put("imageUrl", imageUrl);
		values.put("date", date);
		long insertId = database.insert(DBHelper.TABLE_ARTICLE, null, values);
		// To show how to query
		Cursor cursor = database.query(DBHelper.TABLE_ARTICLE, allColumnsArticle, "_id" + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCommentArticle(cursor);
	}
	
	public Category createCategory(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		long insertId = database.insert(DBHelper.TABLE_CATEGORY, null, values);
		// To show how to query
		Cursor cursor = database.query(DBHelper.TABLE_CATEGORY, allColumnsCategory, "_id" + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCommentCategory(cursor);
	}
	
	public Tag createTag(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		long insertId = database.insert(DBHelper.TABLE_TAG, null, values);
		// To show how to query
		Cursor cursor = database.query(DBHelper.TABLE_TAG, allColumnsTag, "_id" + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCommentTag(cursor);
	}
	
	public ArticleCategory createArticleCategory(long article_id, long category_id) {
		ContentValues values = new ContentValues();
		values.put("articleId", article_id);
		values.put("categoryId", category_id);
		long insertId = database.insert(DBHelper.TABLE_ARTICLECATEGORY, null, values);
		// To show how to query
		Cursor cursor = database.query(DBHelper.TABLE_ARTICLECATEGORY, allColumnsArticleCategory, "_id" + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCommentArticleCategory(cursor);
	}
	
	public ArticleTag createArticleTag(long article_id, long tag_id) {
		ContentValues values = new ContentValues();
		values.put("articleId", article_id);
		values.put("tagId", tag_id);
		long insertId = database.insert(DBHelper.TABLE_ARTICLETAG, null, values);
		// To show how to query
		Cursor cursor = database.query(DBHelper.TABLE_ARTICLETAG, allColumnsArticleTag, "_id" + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToCommentArticleTag(cursor);
	}

	public void deleteArticle(Article article) {
		long id = article.getId();
		System.out.println("article deleted with id: " + id);
		database.delete(DBHelper.TABLE_ARTICLE, "_id" + " = " + id, null);
	}
	
	public void deleteCategory(Category category) {
		long id = category.getId();
		System.out.println("category deleted with id: " + id);
		database.delete(DBHelper.TABLE_CATEGORY, "_id" + " = " + id, null);
	}
	
	public void deleteTag(Tag tag) {
		long id = tag.getId();
		System.out.println("tag deleted with id: " + id);
		database.delete(DBHelper.TABLE_TAG, "_id" + " = " + id, null);
	}
	
	public void deleteArticleCategory(ArticleCategory articleCategory) {
		long id = articleCategory.getId();
		System.out.println("article category deleted with id: " + id);
		database.delete(DBHelper.TABLE_ARTICLECATEGORY, "_id" + " = " + id, null);
	}
	
	public void deleteArticleTag(ArticleTag articleTag) {
		long id = articleTag.getId();
		System.out.println("article tag deleted with id: " + id);
		database.delete(DBHelper.TABLE_ARTICLETAG, "_id" + " = " + id, null);
	}
	
	public void deleteAllArticles() {
		System.out.println("deleted all articles");
		database.delete(DBHelper.TABLE_ARTICLE, null, null);
	}
	
	public void deleteAllCategories() {
		System.out.println("deleted all categories");
		database.delete(DBHelper.TABLE_CATEGORY, null, null);
	}
	
	public void deleteAllTags() {
		System.out.println("deleted all tags");
		database.delete(DBHelper.TABLE_TAG, null, null);
	}
	
	public void deleteAllArticleCategories() {
		System.out.println("deleted all article categories");
		database.delete(DBHelper.TABLE_ARTICLECATEGORY, null, null);
	}
	
	public void deleteAllArticleTags() {
		System.out.println("deleted all article tags");
		database.delete(DBHelper.TABLE_ARTICLETAG, null, null);
	}

	public ArrayList<Article> getAllArticles() {
		ArrayList<Article> articles = new ArrayList<Article>();
		Cursor cursor = database.query(DBHelper.TABLE_ARTICLE, allColumnsArticle, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Article article = cursorToCommentArticle(cursor);
			articles.add(article);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return articles;
	}
	
	public ArrayList<Category> getAllCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		Cursor cursor = database.query(DBHelper.TABLE_CATEGORY, allColumnsCategory, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category category = cursorToCommentCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return categories;
	}
	
	public ArrayList<Tag> getAllTags() {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.query(DBHelper.TABLE_TAG, allColumnsTag, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tag tag = cursorToCommentTag(cursor);
			tags.add(tag);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tags;
	}
	

	private Article cursorToCommentArticle(Cursor cursor) {
		Article article = new Article();
		article.setId(cursor.getLong(0));
		article.setTitle(cursor.getString(1));
		article.setUrl(cursor.getString(2));
		article.setExcerpt(cursor.getString(3));
		article.setText(cursor.getString(4));
		article.setAuthor(cursor.getString(5));
		article.setImageUrl(cursor.getString(6));
		article.setDate(cursor.getString(7));
		return article;
	}
	
	private Category cursorToCommentCategory(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getLong(0));
		category.setName(cursor.getString(1));
		return category;
	}
	
	private Tag cursorToCommentTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setId(cursor.getLong(0));
		tag.setName(cursor.getString(1));
		return tag;
	}
	
	private ArticleCategory cursorToCommentArticleCategory(Cursor cursor) {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setId(cursor.getLong(0));
		articleCategory.setArticle_id(cursor.getLong(1));
		articleCategory.setCategory_id(cursor.getLong(2));
		return articleCategory;
	}
	
	private ArticleTag cursorToCommentArticleTag(Cursor cursor) {
		ArticleTag articleTag = new ArticleTag();
		articleTag.setId(cursor.getLong(0));
		articleTag.setArticle_id(cursor.getLong(1));
		articleTag.setTag_id(cursor.getLong(2));
		return articleTag;
	}
}
