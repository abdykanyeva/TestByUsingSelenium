package com.sparkle.entity;

import com.sparkle.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity {


    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    private Company company;


}
