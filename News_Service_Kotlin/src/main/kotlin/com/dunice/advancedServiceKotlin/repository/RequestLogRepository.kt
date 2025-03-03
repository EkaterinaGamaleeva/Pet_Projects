package com.dunice.advancedServiceKotlin.repository

import com.dunice.advancedServiceKotlin.models.RequestLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestLogRepository : JpaRepository<RequestLog, Long>
