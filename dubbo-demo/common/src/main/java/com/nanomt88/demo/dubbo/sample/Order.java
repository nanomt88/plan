package com.nanomt88.demo.dubbo.sample;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String orderTitle;
    private String userId;
    private String userName;
    private User user;
}
