package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * RuleName Java Bean
 * This class is a representation of the rule name entity in the database.
 * It contains fields that correspond to the columns in the rule name table.
 */
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Length(max = 125)
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Length(max = 125)
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Length(max = 125)
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Length(max = 512)
    private String template;

    public RuleName() {}
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        setName(name);
        setDescription(description);
        setJson(json);
        setTemplate(template);
        setSqlStr(sqlStr);
        setSqlPart(sqlPart);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    @NotBlank(message = "SqlStr is mandatory")
    @Length(max = 125)
    private String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    @Length(max = 125)
    private String sqlPart;
}
