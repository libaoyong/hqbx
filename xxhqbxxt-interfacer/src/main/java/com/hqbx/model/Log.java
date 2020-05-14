package com.hqbx.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private Integer id;

    private Integer czr;

    private String cz;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCzr() {
        return czr;
    }

    public void setCzr(Integer czr) {
        this.czr = czr;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz == null ? null : cz.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", czr=").append(czr);
        sb.append(", cz=").append(cz);
        sb.append(", time=").append(time);
        sb.append("]");
        return sb.toString();
    }
}