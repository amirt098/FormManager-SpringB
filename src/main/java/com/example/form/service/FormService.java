package com.example.form.service;

import com.example.form.dto.FormDTO;
import com.example.form.dto.FieldDTO;
import com.example.form.model.Form;
import com.example.form.model.Field;
import com.example.form.repository.FormRepository;
import com.example.form.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormService {
    
    @Autowired
    private FormRepository formRepository;
    
    @Autowired
    private FieldRepository fieldRepository;

    public List<FormDTO> getAllForms() {
        return formRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FormDTO getFormById(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + id));
        return convertToDTO(form);
    }

    @Transactional
    public FormDTO createForm(FormDTO formDTO) {
        Form form = new Form();
        updateFormFromDTO(form, formDTO);
        Form savedForm = formRepository.save(form);
        return convertToDTO(savedForm);
    }

    @Transactional
    public FormDTO updateForm(Long id, FormDTO formDTO) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + id));
        updateFormFromDTO(form, formDTO);
        Form updatedForm = formRepository.save(form);
        return convertToDTO(updatedForm);
    }

    @Transactional
    public void deleteForm(Long id) {
        if (!formRepository.existsById(id)) {
            throw new EntityNotFoundException("Form not found with id: " + id);
        }
        formRepository.deleteById(id);
    }

    @Transactional
    public FormDTO togglePublishStatus(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + id));
        form.setPublished(!form.isPublished());
        return convertToDTO(formRepository.save(form));
    }

    public List<FormDTO> getPublishedForms() {
        return formRepository.findByPublished(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormDTO updateFormFields(Long id, List<FieldDTO> fieldDTOs) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found with id: " + id));
        
        // Clear existing fields
        form.getFields().clear();
        
        // Add new fields
        fieldDTOs.forEach(fieldDTO -> {
            Field field = new Field();
            field.setFieldName(fieldDTO.getFieldName());
            field.setLabel(fieldDTO.getLabel());
            field.setType(fieldDTO.getType());
            field.setDefaultValue(fieldDTO.getDefaultValue());
            field.setForm(form);
            form.getFields().add(field);
        });
        
        Form savedForm = formRepository.save(form);
        return convertToDTO(savedForm);
    }

    private FormDTO convertToDTO(Form form) {
        FormDTO dto = new FormDTO();
        dto.setId(form.getId());
        dto.setName(form.getName());
        dto.setPublished(form.isPublished());
        dto.setSubmitEndpoint(form.getSubmitEndpoint());
        dto.setSubmitMethod(form.getSubmitMethod());
        dto.setFields(form.getFields().stream()
                .map(this::convertToFieldDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private FieldDTO convertToFieldDTO(Field field) {
        FieldDTO dto = new FieldDTO();
        dto.setId(field.getId());
        dto.setFieldName(field.getFieldName());
        dto.setLabel(field.getLabel());
        dto.setType(field.getType());
        dto.setDefaultValue(field.getDefaultValue());
        return dto;
    }

    private void updateFormFromDTO(Form form, FormDTO dto) {
        form.setName(dto.getName());
        form.setPublished(dto.isPublished());
        form.setSubmitEndpoint(dto.getSubmitEndpoint());
        form.setSubmitMethod(dto.getSubmitMethod());
        
        if (dto.getFields() != null) {
            form.getFields().clear();
            dto.getFields().forEach(fieldDTO -> {
                Field field = new Field();
                field.setFieldName(fieldDTO.getFieldName());
                field.setLabel(fieldDTO.getLabel());
                field.setType(fieldDTO.getType());
                field.setDefaultValue(fieldDTO.getDefaultValue());
                field.setForm(form);
                form.getFields().add(field);
            });
        }
    }
}
