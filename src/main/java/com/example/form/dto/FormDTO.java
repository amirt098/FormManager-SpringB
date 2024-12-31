package com.example.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class FormDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    
    @JsonProperty
    private boolean published;
    
    @NotNull(message = "Fields list cannot be null")
    private List<FieldDTO> fields;
    @NotBlank(message = "Submit endpoint is required")
    private String submitEndpoint;
    @NotBlank(message = "Submit method is required")
    private String submitMethod;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldDTO> fields) {
        this.fields = fields;
    }

    public String getSubmitEndpoint() {
        return submitEndpoint;
    }

    public void setSubmitEndpoint(String submitEndpoint) {
        this.submitEndpoint = submitEndpoint;
    }

    public String getSubmitMethod() {
        return submitMethod;
    }

    public void setSubmitMethod(String submitMethod) {
        this.submitMethod = submitMethod;
    }
}
