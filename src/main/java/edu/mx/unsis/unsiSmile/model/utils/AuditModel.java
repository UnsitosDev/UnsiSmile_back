package edu.mx.unsis.unsiSmile.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mx.unsis.unsiSmile.common.Constants;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditModel implements Serializable {

    @Basic
    @Column(name = "created_by", updatable = false)
    @JsonIgnore
    @CreatedBy
    private String createdBy = "Admin";

    @Basic
    @Column(name = "created_at", updatable = false)
    @JsonIgnore
    @CreationTimestamp
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_by", insertable = false)
    @JsonIgnore
    @LastModifiedBy
    private String updatedBy;

    @Basic
    @Column(name = "updated_at", insertable = false)
    @JsonIgnore
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "status_key")
    private String statusKey = Constants.ACTIVE;
}