package com.santikdef.notebook.repository;

import com.santikdef.notebook.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    List<Note> findByUserIdIsNull();

    @Modifying
    @Query(value = "update Note n set n.text = ?1 where id = ?2", nativeQuery = true)
    void updateTextById(String text, Long id);
}
