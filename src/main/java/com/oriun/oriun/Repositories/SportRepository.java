package com.oriun.oriun.Repositories;
import com.oriun.oriun.Models.SportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface SportRepository extends JpaRepository<SportModel,String>{
   
}
