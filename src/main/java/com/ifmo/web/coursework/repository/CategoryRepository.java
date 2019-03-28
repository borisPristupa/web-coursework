package com.ifmo.web.coursework.repository;

import com.ifmo.web.coursework.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
