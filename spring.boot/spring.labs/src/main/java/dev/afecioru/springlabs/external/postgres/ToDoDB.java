package dev.afecioru.springlabs.external.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

interface ToDoDB extends JpaRepository<ToDoEntity, Long> {
}
