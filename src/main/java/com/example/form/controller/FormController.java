package com.example.form.controller;

import com.example.form.dto.FormDTO;
import com.example.form.dto.FieldDTO;
import com.example.form.service.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/forms", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping
    public ResponseEntity<List<FormDTO>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormDTO> createForm(@Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.createForm(formDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormDTO> getFormById(@PathVariable Long id) {
        return ResponseEntity.ok(formService.getFormById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormDTO> updateForm(@PathVariable Long id, @Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.updateForm(id, formDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        formService.deleteForm(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/fields")
    public ResponseEntity<List<FieldDTO>> getFormFields(@PathVariable Long id) {
        FormDTO form = formService.getFormById(id);
        return ResponseEntity.ok(form.getFields());
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<FormDTO> updateFormFields(@PathVariable Long id, @Valid @RequestBody List<FieldDTO> fields) {
        return ResponseEntity.ok(formService.updateFormFields(id, fields));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<FormDTO> togglePublishStatus(@PathVariable Long id) {
        return ResponseEntity.ok(formService.togglePublishStatus(id));
    }

    @GetMapping("/published")
    public ResponseEntity<List<FormDTO>> getPublishedForms() {
        return ResponseEntity.ok(formService.getPublishedForms());
    }
}
