package com.curriculum.constant;

public enum KnowledgePointEnum
{
    TABLE("1", "线性表"),
    TREE("2", "二叉树"),
    GRAPH("3", "图论");

    private String typeId;
    private String typeName;

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private KnowledgePointEnum(String typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public static KnowledgePointEnum getEnumById(String typeId) {
        for (KnowledgePointEnum knowledgePointEnum : values()) {
            if (knowledgePointEnum.getTypeId().equals(typeId)) {
                return knowledgePointEnum;
            }
        }
        return null;
    }
}