package com.divyapankajananda.mimiapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "icon")
public class Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="icon_id")
    private Long iconId;

    @Column(name = "name", nullable = false)
    private String name;

    // fa fa-icons
    @Column(name = "icon_element", nullable = false)
    private String iconElement;

}
