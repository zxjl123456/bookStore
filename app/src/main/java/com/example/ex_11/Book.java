package com.example.ex_11;

import java.io.Serializable;
import java.util.HashMap;

public class Book implements Serializable {
    private int _id;
    private String title;
    private String author;
    private String summary;

    public Book() {
    }

    public Book(String title, String author, String summary) {
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }
}
