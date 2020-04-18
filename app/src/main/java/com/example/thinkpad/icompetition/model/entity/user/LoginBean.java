package com.example.thinkpad.icompetition.model.entity.user;

import com.google.gson.annotations.SerializedName;

public class LoginBean {
    @SerializedName("token:")
    private String _$Token251; // FIXME check this code

    public String get_$Token251() {
        _$Token251 = _$Token251+":";
        return _$Token251;
    }

    public void set_$Token251(String _$Token251) {
        this._$Token251 = _$Token251;
    }
}
