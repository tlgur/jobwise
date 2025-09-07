package com.jobwise.api.domain.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Component {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "component_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Component parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Component> children = new ArrayList<>();

    private Integer depth = 0;
}