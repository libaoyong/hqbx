package com.hqbx.model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private Integer vxid;

    private String openid;

    private Integer code;

    private Integer vxsysid;

    private String tel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVxid() {
        return vxid;
    }

    public void setVxid(Integer vxid) {
        this.vxid = vxid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getVxsysid() {
        return vxsysid;
    }

    public void setVxsysid(Integer vxsysid) {
        this.vxsysid = vxsysid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vxid=").append(vxid);
        sb.append(", openid=").append(openid);
        sb.append(", code=").append(code);
        sb.append(", vxsysid=").append(vxsysid);
        sb.append(", tel=").append(tel);
        sb.append("]");
        return sb.toString();
    }
}