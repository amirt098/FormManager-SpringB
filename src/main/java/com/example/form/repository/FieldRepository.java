package com.example.form.repository;

import com.example.form.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFormId(Long formId);
}
