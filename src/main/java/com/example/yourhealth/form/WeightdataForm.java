package com.example.yourhealth.form;

import lombok.Data;

@Data
public class WeightdataForm {

    private Long id;

    private Long userId;

    private  Double weightData;

    private String path;

    private UserForm user;

}

