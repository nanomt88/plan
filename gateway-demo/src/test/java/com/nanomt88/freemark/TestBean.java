package com.nanomt88.freemark;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@Builder
public class TestBean {
    String name;
    String url;
    int page;
    boolean isNonProfit;
    Addr address;
    List<Links> links;
}

@ToString
@Data
@Builder
class Links {
    String name;
    String url;
}

@ToString
@Data
@Builder
class Addr {
    String street;
    String city;
    String country;
}