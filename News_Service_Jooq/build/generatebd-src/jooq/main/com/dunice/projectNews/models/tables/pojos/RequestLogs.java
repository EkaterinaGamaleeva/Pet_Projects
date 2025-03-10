/*
 * This file is generated by jOOQ.
 */
package com.dunice.projectNews.models.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RequestLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final String method;
    private final String url;
    private final Integer status;
    private final String errors;
    private final String usersEmail;

    public RequestLogs(RequestLogs value) {
        this.timestamp = value.timestamp;
        this.method = value.method;
        this.url = value.url;
        this.status = value.status;
        this.errors = value.errors;
        this.usersEmail = value.usersEmail;
    }

    public RequestLogs(
        LocalDateTime timestamp,
        String method,
        String url,
        Integer status,
        String errors,
        String usersEmail
    ) {
        this.timestamp = timestamp;
        this.method = method;
        this.url = url;
        this.status = status;
        this.errors = errors;
        this.usersEmail = usersEmail;
    }

    /**
     * Getter for <code>news_schema.request_logs.timestamp</code>.
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Getter for <code>news_schema.request_logs.method</code>.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Getter for <code>news_schema.request_logs.url</code>.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Getter for <code>news_schema.request_logs.status</code>.
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * Getter for <code>news_schema.request_logs.errors</code>.
     */
    public String getErrors() {
        return this.errors;
    }

    /**
     * Getter for <code>news_schema.request_logs.users_email</code>.
     */
    public String getUsersEmail() {
        return this.usersEmail;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RequestLogs other = (RequestLogs) obj;
        if (this.timestamp == null) {
            if (other.timestamp != null)
                return false;
        }
        else if (!this.timestamp.equals(other.timestamp))
            return false;
        if (this.method == null) {
            if (other.method != null)
                return false;
        }
        else if (!this.method.equals(other.method))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.status == null) {
            if (other.status != null)
                return false;
        }
        else if (!this.status.equals(other.status))
            return false;
        if (this.errors == null) {
            if (other.errors != null)
                return false;
        }
        else if (!this.errors.equals(other.errors))
            return false;
        if (this.usersEmail == null) {
            if (other.usersEmail != null)
                return false;
        }
        else if (!this.usersEmail.equals(other.usersEmail))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
        result = prime * result + ((this.method == null) ? 0 : this.method.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
        result = prime * result + ((this.errors == null) ? 0 : this.errors.hashCode());
        result = prime * result + ((this.usersEmail == null) ? 0 : this.usersEmail.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RequestLogs (");

        sb.append(timestamp);
        sb.append(", ").append(method);
        sb.append(", ").append(url);
        sb.append(", ").append(status);
        sb.append(", ").append(errors);
        sb.append(", ").append(usersEmail);

        sb.append(")");
        return sb.toString();
    }
}
