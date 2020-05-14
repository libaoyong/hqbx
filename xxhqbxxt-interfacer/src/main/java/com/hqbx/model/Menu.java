package com.hqbx.model;

import java.io.Serializable;

public class Menu implements Serializable {
    private Integer id;

    private String menuname;

    private String menulink;

    private String fdm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getMenulink() {
        return menulink;
    }

    public void setMenulink(String menulink) {
        this.menulink = menulink == null ? null : menulink.trim();
    }

    public String getFdm() {
        return fdm;
    }

    public void setFdm(String fdm) {
        this.fdm = fdm == null ? null : fdm.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menuname=").append(menuname);
        sb.append(", menulink=").append(menulink);
        sb.append(", fdm=").append(fdm);
        sb.append("]");
        return sb.toString();
    }
}