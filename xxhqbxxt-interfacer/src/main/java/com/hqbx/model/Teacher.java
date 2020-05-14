package com.hqbx.model;

import java.io.Serializable;

public class Teacher implements Serializable {
    private Integer id;

    private String uname;

    private String school;

    private String zyzc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getZyzc() {
        return zyzc;
    }

    public void setZyzc(String zyzc) {
        this.zyzc = zyzc == null ? null : zyzc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uname=").append(uname);
        sb.append(", school=").append(school);
        sb.append(", zyzc=").append(zyzc);
        sb.append("]");
        return sb.toString();
    }
}