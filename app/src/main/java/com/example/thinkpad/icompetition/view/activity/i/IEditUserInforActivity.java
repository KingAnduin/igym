package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.user.EditUserInforRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;

public interface IEditUserInforActivity extends IBaseActivity{
    void getUserInforReturn(UserInforRoot root);
    void getSubmitReturn(EditUserInforRoot root);
}
