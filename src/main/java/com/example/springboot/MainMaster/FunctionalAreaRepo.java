package com.example.springboot.MainMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionalAreaRepo extends JpaRepository<FunctionalArea , Integer>{

}
