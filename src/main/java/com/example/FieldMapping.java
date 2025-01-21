package com.example;

public class FieldMapping {

    private String fieldName;
    private String sourceField;
    private String dataType;
    private String isRequired;

    public FieldMapping(String fieldName, String sourceField, String dataType, String isRequired) {
        this.fieldName = fieldName;
        this.sourceField = sourceField;
        this.dataType = dataType;
        this.isRequired = isRequired;
    }

    public FieldMapping() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public String toString() {
        return "FieldMapping{" +
                "fieldName='" + fieldName + '\'' +
                ", sourceField='" + sourceField + '\'' +
                ", dataType='" + dataType + '\'' +
                ", isRequired='" + isRequired + '\'' +
                '}';
    }
}
