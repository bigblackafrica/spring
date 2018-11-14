package com.spring.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Users")
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = true, length = 255)
    @ApiModelProperty(required = true)
    private String name;

    @Column(name = "salary", nullable = true, length = 10)
    @ApiModelProperty(required = true)
    private Integer salary;
}
