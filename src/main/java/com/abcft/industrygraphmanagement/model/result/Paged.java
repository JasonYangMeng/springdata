package com.abcft.industrygraphmanagement.model.result;

import lombok.Data;

import java.util.List;

@Data
public class Paged<T> {
    private List<T> rows;
    private int total;
}