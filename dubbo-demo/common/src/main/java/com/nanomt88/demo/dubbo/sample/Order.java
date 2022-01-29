package com.nanomt88.demo.dubbo.sample;


import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String orderTitle;
    private String userId;
    private String userName;
    private User user;

    public Order(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", user=" + user +
                '}';
    }
}
