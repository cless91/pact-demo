package com.example.demo;

import lombok.Getter;

@Getter
public class AlligatorServiceException extends RuntimeException {
    private String error = "Argh!!!";
}
