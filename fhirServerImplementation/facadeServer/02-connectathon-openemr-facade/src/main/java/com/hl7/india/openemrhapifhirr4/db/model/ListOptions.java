package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "list_options")
public class ListOptions implements Serializable {

    @Id
    @Column(name = "list_id")
    private String listId;
    @Column(name="option_id")
    private String optionId;
    @Column(name="title")
    private String title;

    public ListOptions() {
    }

    public ListOptions(String listId, String optionId, String title) {
        this.listId = listId;
        this.optionId = optionId;
        this.title = title;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ListOptions{" +
                "listId='" + listId + '\'' +
                ", optionId='" + optionId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
