package com.example.quanlysanpham.Entity;

import com.example.quanlysanpham.Configuration.Security.UserDetailsCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "Create_date")
    private Date CreateDate;

    @Column(name = "Create_By")
    private String CreateBy;

    @Column(name = "Modifier_date")
    private Date ModifierDate;

    @Column(name = "Modifier_by")
    private String ModifierBy;

    @PrePersist
    public void prePersist(){
        String createBy ="unknowUser";
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
            createBy = userDetailsCustom.getUsername();
        }
        catch (Exception e){
            System.out.println(e);
        }

        this.CreateBy =createBy;
        this.CreateDate = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        String modifyBy ="unknowUser";
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
            modifyBy = userDetailsCustom.getUsername();
        }
        catch (Exception e){
            System.out.println(e);
        }
        this.ModifierBy = modifyBy;
        this.ModifierDate = new Date();
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public Date getModifierDate() {
        return ModifierDate;
    }

    public void setModifierDate(Date modifierDate) {
        ModifierDate = modifierDate;
    }

    public String getModifierBy() {
        return ModifierBy;
    }

    public void setModifierBy(String modifierBy) {
        ModifierBy = modifierBy;
    }
}