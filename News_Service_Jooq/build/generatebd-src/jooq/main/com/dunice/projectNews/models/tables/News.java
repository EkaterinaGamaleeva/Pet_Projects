/*
 * This file is generated by jOOQ.
 */
package com.dunice.projectNews.models.tables;


import com.dunice.projectNews.models.Keys;
import com.dunice.projectNews.models.NewsSchema;
import com.dunice.projectNews.models.tables.NewsTags.NewsTagsPath;
import com.dunice.projectNews.models.tables.Users.UsersPath;
import com.dunice.projectNews.models.tables.records.NewsRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class News extends TableImpl<NewsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>news_schema.news</code>
     */
    public static final News NEWS = new News();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NewsRecord> getRecordType() {
        return NewsRecord.class;
    }

    /**
     * The column <code>news_schema.news.news_id</code>.
     */
    public final TableField<NewsRecord, Long> NEWS_ID = createField(DSL.name("news_id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>news_schema.news.description</code>.
     */
    public final TableField<NewsRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>news_schema.news.image</code>.
     */
    public final TableField<NewsRecord, String> IMAGE = createField(DSL.name("image"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>news_schema.news.title</code>.
     */
    public final TableField<NewsRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>news_schema.news.users_id</code>.
     */
    public final TableField<NewsRecord, UUID> USERS_ID = createField(DSL.name("users_id"), SQLDataType.UUID.nullable(false), this, "");

    private News(Name alias, Table<NewsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private News(Name alias, Table<NewsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>news_schema.news</code> table reference
     */
    public News(String alias) {
        this(DSL.name(alias), NEWS);
    }

    /**
     * Create an aliased <code>news_schema.news</code> table reference
     */
    public News(Name alias) {
        this(alias, NEWS);
    }

    /**
     * Create a <code>news_schema.news</code> table reference
     */
    public News() {
        this(DSL.name("news"), null);
    }

    public <O extends Record> News(Table<O> path, ForeignKey<O, NewsRecord> childPath, InverseForeignKey<O, NewsRecord> parentPath) {
        super(path, childPath, parentPath, NEWS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class NewsPath extends News implements Path<NewsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> NewsPath(Table<O> path, ForeignKey<O, NewsRecord> childPath, InverseForeignKey<O, NewsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private NewsPath(Name alias, Table<NewsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public NewsPath as(String alias) {
            return new NewsPath(DSL.name(alias), this);
        }

        @Override
        public NewsPath as(Name alias) {
            return new NewsPath(alias, this);
        }

        @Override
        public NewsPath as(Table<?> alias) {
            return new NewsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : NewsSchema.NEWS_SCHEMA;
    }

    @Override
    public Identity<NewsRecord, Long> getIdentity() {
        return (Identity<NewsRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<NewsRecord> getPrimaryKey() {
        return Keys.NEWS_PKEY;
    }

    @Override
    public List<ForeignKey<NewsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.NEWS__FK_NEWS_USERS);
    }

    private transient UsersPath _users;

    /**
     * Get the implicit join path to the <code>news_schema.users</code> table.
     */
    public UsersPath users() {
        if (_users == null)
            _users = new UsersPath(this, Keys.NEWS__FK_NEWS_USERS, null);

        return _users;
    }

    private transient NewsTagsPath _newsTags;

    /**
     * Get the implicit to-many join path to the
     * <code>news_schema.news_tags</code> table
     */
    public NewsTagsPath newsTags() {
        if (_newsTags == null)
            _newsTags = new NewsTagsPath(this, null, Keys.NEWS_TAGS__FK_NEWS_TAGS_NEWS.getInverseKey());

        return _newsTags;
    }

    @Override
    public News as(String alias) {
        return new News(DSL.name(alias), this);
    }

    @Override
    public News as(Name alias) {
        return new News(alias, this);
    }

    @Override
    public News as(Table<?> alias) {
        return new News(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public News rename(String name) {
        return new News(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public News rename(Name name) {
        return new News(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public News rename(Table<?> name) {
        return new News(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News where(Condition condition) {
        return new News(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public News where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public News where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public News where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public News where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public News whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, String, UUID> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super Long, ? super String, ? super String, ? super String, ? super UUID, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Long, ? super String, ? super String, ? super String, ? super UUID, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
