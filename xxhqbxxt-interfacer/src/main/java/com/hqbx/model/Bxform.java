package com.hqbx.model;

import java.io.Serializable;
import java.util.Date;

public class Bxform implements Serializable {
    private Integer id;

    private String info;

    private String address;

    private Date time;

    private Integer ztid;

    private String img;

    private Integer uid;

    private Integer mid;

    private String bxlx;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getZtid() {
        return ztid;
    }

    public void setZtid(Integer ztid) {
        this.ztid = ztid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getBxlx() {
        return bxlx;
    }

    public void setBxlx(String bxlx) {
        this.bxlx = bxlx == null ? null : bxlx.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", info=").append(info);
        sb.append(", address=").append(address);
        sb.append(", time=").append(time);
        sb.append(", ztid=").append(ztid);
        sb.append(", img=").append(img);
        sb.append(", uid=").append(uid);
        sb.append(", mid=").append(mid);
        sb.append(", bxlx=").append(bxlx);
        sb.append("]");
        return sb.toString();
    }
}