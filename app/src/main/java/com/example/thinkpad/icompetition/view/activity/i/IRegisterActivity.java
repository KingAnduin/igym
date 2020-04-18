package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.user.RegisterRoot;
import com.example.thinkpad.icompetition.model.event.RegisterEvent;

public interface IRegisterActivity extends IBaseActivity {
    void registeredReturn(RegisterRoot root);//注册接口的回调
}
