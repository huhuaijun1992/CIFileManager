package com.ci123.cifilemodule;

import java.io.Serializable;

/**
 * @author: 11304
 * @date: 2020/2/27
 * @desc:
 */
public class Book implements Serializable {
    int bid;
    String bookName;
    String author;

    public Book(int bid, String bookName, String author) {
        this.bid = bid;
        this.bookName = bookName;
        this.author = author;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "bid=" + bid +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
