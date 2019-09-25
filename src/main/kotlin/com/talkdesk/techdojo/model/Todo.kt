package com.talkdesk.techdojo.model

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Todo(
        @Id
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val content: String,
        val createdAt: Instant = Instant.now(),
        val updatedAt: Instant = Instant.now(),
        val status: Status = Status.UNDONE) {}