package com.dunice.advancedServiceKotlin.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "request_logs", schema = "news_schema")
 class RequestLog (
    @Id
    val timestamp: LocalDateTime,

    var method: String ,

    var url: String,

    var status: Int,

    var errors: String,

    @Column(name = "users_email")
    var user: String,
){
}
