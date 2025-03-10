/*
 * This file is generated by jOOQ.
 */
package com.dunice.projectNews.models.tables;


import com.dunice.projectNews.models.Keys;
import com.dunice.projectNews.models.NewsSchema;
import com.dunice.projectNews.models.tables.NewsTags.NewsTagsPath;
import com.dunice.projectNews.models.tables.records.TagsRecord;

import java.util.Collection;
import java.util.function.Function;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
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
public class Tags extends TableImpl<TagsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>news_schema.tags</code>
     */
    public static final Tags TAGS = new Tags();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TagsRecord> getRecordType() {
        return TagsRecord.class;
    }

    /**
     * The column <code>news_schema.tags.tag_id</code>.
     */
    public final TableField<TagsRecord, Long> TAG_ID = createField(DSL.name("tag_id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>news_schema.tags.title</code>.
     */
    public final TableField<TagsRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private Tags(Name alias, Table<TagsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Tags(Name alias, Table<TagsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>news_schema.tags</code> table reference
     */
    public Tags(String alias) {
        this(DSL.name(alias), TAGS);
    }

    /**
     * Create an aliased <code>news_schema.tags</code> table reference
     */
    public Tags(Name alias) {
        this(alias, TAGS);
    }

    /**
     * Create a <code>news_schema.tags</code> table reference
     */
    public Tags() {
        this(DSL.name("tags"), null);
    }

    public <O extends Record> Tags(Table<O> path, ForeignKey<O, TagsRecord> childPath, InverseForeignKey<O, TagsRecord> parentPath) {
        super(path, childPath, parentPath, TAGS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class TagsPath extends Tags implements Path<TagsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> TagsPath(Table<O> path, ForeignKey<O, TagsRecord> childPath, InverseForeignKey<O, TagsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private TagsPath(Name alias, Table<TagsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public TagsPath as(String alias) {
            return new TagsPath(DSL.name(alias), this);
        }

        @Override
        public TagsPath as(Name alias) {
            return new TagsPath(alias, this);
        }

        @Override
        public TagsPath as(Table<?> alias) {
            return new TagsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : NewsSchema.NEWS_SCHEMA;
    }

    @Override
    public Identity<TagsRecord, Long> getIdentity() {
        return (Identity<TagsRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<TagsRecord> getPrimaryKey() {
        return Keys.TAGS_PKEY;
    }

    private transient NewsTagsPath _newsTags;

    /**
     * Get the implicit to-many join path to the
     * <code>news_schema.news_tags</code> table
     */
    public NewsTagsPath newsTags() {
        if (_newsTags == null)
            _newsTags = new NewsTagsPath(this, null, Keys.NEWS_TAGS__FK_NEWS_TAGS_TAGS.getInverseKey());

        return _newsTags;
    }

    @Override
    public Tags as(String alias) {
        return new Tags(DSL.name(alias), this);
    }

    @Override
    public Tags as(Name alias) {
        return new Tags(alias, this);
    }

    @Override
    public Tags as(Table<?> alias) {
        return new Tags(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(String name) {
        return new Tags(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(Name name) {
        return new Tags(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(Table<?> name) {
        return new Tags(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags where(Condition condition) {
        return new Tags(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Tags where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Tags where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Tags where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Tags where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Tags whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
