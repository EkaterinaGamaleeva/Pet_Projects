/*
 * This file is generated by jOOQ.
 */
package com.dunice.projectNews.models.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long tagId;
    private final String title;

    public Tags(Tags value) {
        this.tagId = value.tagId;
        this.title = value.title;
    }

    public Tags(
        Long tagId,
        String title
    ) {
        this.tagId = tagId;
        this.title = title;
    }

    /**
     * Getter for <code>news_schema.tags.tag_id</code>.
     */
    public Long getTagId() {
        return this.tagId;
    }

    /**
     * Getter for <code>news_schema.tags.title</code>.
     */
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Tags other = (Tags) obj;
        if (this.tagId == null) {
            if (other.tagId != null)
                return false;
        }
        else if (!this.tagId.equals(other.tagId))
            return false;
        if (this.title == null) {
            if (other.title != null)
                return false;
        }
        else if (!this.title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.tagId == null) ? 0 : this.tagId.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tags (");

        sb.append(tagId);
        sb.append(", ").append(title);

        sb.append(")");
        return sb.toString();
    }
}
