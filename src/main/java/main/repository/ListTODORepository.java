package main.repository;

import main.model.ListTODO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ListTODORepository extends CrudRepository<ListTODO, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE ListTODO SET text = :text WHERE id = :id")
    void update(@Param("id") Integer id, @Param("text") String text);

    @Query(value = "SELECT lt FROM ListTODO lt")
    List<ListTODO> getListTODO();

    @Query(value = "SELECT lt FROM ListTODO lt WHERE id = :id")
    List<ListTODO> getById(@Param("id") Integer id);

}