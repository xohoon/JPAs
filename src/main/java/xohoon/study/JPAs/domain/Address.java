package xohoon.study.JPAs.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 기본 생성자 생성해야함 그나마 안전하게 protected
    protected Address() {
    }

    // 생성할때만 값 추가, setter 생성 x
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
