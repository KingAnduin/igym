package com.example.thinkpad.icompetition.model.entity.test;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by a'su's on 2018/11/13.
 */

@Entity
public class TestDB {
    @Id
    private String id;
    @Property
    private String name;
    @Property
    private Integer agr;
    @Generated(hash = 478320804)
    public TestDB(String id, String name, Integer agr) {
        this.id = id;
        this.name = name;
        this.agr = agr;
    }
    @Generated(hash = 1476071540)
    public TestDB() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAgr() {
        return this.agr;
    }
    public void setAgr(Integer agr) {
        this.agr = agr;
    }
}
