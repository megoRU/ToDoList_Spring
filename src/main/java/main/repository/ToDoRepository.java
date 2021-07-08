package main.repository;

import main.dao.ToDo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE to_do SET text = :text WHERE id = :id ", nativeQuery = true)
    void update(@Param("id") Integer id, @Param("text") String text);

}