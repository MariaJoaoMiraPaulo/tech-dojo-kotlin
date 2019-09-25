package com.talkdesk.techdojo.repository

import com.talkdesk.techdojo.model.Status
import com.talkdesk.techdojo.model.Todo
import org.springframework.data.repository.CrudRepository

interface TaskRepository: CrudRepository<Todo, String> {
    fun findByStatus(status: Status): List<Todo>
}