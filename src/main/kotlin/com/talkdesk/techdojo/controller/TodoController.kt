package com.talkdesk.techdojo.controller

import com.talkdesk.techdojo.model.*
import com.talkdesk.techdojo.repository.TaskRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/todos")
class TaskController(private val taskRepository: TaskRepository) {

    @GetMapping
    fun listTasks() : ResponseEntity<List<Todo>>{
        val tasks = taskRepository.findAll().sortedBy { Task -> Task.updatedAt }
        return ResponseEntity.ok(tasks.toList())
    }

    @GetMapping( "done")
    fun listDoneTasks(): ResponseEntity<List<Todo>>{
        val tasks = taskRepository.findByStatus(Status.DONE)
        return ResponseEntity.ok(tasks.toList())
    }

    @GetMapping("undone")
    fun listUndoneTasks(): ResponseEntity<List<Todo>>{
        val tasks = taskRepository.findByStatus(Status.UNDONE)
        return ResponseEntity.ok(tasks.toList())
    }

    @PostMapping
    fun createTask(@RequestBody task: Todo) : ResponseEntity<Todo>{
        return ResponseEntity.ok(taskRepository.save(task))
    }

    @PatchMapping("/{id}")
    fun updateTask(@PathVariable id: String, @RequestBody task: Todo): ResponseEntity<Todo>{
        val foundTask = taskRepository.findById(id)
        return if (foundTask.isPresent){
            val updatedTask = foundTask.get().copy(name = task.name, content = task.content, updatedAt = Instant.now())
            taskRepository.save(updatedTask)
            ResponseEntity.ok(updatedTask)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{id}/status/{action}")
    fun changeState(@PathVariable id: String, @PathVariable action:String): ResponseEntity<Todo>{
       val foundTask = taskRepository.findById(id)

       val newStatus = when(action){
           "done" -> Status.DONE
           "undone" -> Status.UNDONE
           else -> return ResponseEntity.notFound().build()  
        }

        return if (foundTask.isPresent){
            val updatedTask = foundTask.get().copy(status = newStatus, updatedAt = Instant.now())
            taskRepository.save(updatedTask)
            ResponseEntity.ok(updatedTask)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun changeTask(@PathVariable id:String, @RequestBody task: Todo):ResponseEntity<Todo>{
        val foundTask = taskRepository.findById(id)

        return if (foundTask.isPresent){
            val updatedTask = foundTask.get().copy(status = task.status, content = task.content, name = task.name, updatedAt = Instant.now())
            taskRepository.save(updatedTask)
            ResponseEntity.ok(updatedTask)
        }
        else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<Unit>{
        taskRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }

}