package com.msstore.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CTDonHangID implements Serializable{
	@Column(name = "madon")
    private long maDon;
    @Column(name = "masp")
    private long maSP;
}
