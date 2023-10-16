package com.ariel.BeersApi.Repositories;

import com.ariel.BeersApi.Abstractions.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean()
public interface IRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    Page<T> getAllByStatusTrue(Pageable pageable);
    long countByStatusTrue();
    List<T> getAllByStatusTrue();
    @Modifying()
    @Query("UPDATE #{#entityName} e SET e.status = False WHERE e.id = :Id AND e.status = True")
    void delete(@Param(value="Id") int id);

}
