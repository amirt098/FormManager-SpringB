package com.example.form.repository;

import com.example.form.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByPublished(boolean published);
}
