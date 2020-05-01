package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.NormalRoot;
import com.example.thinkpad.icompetition.model.entity.comment.CommentRoot;

/**
 * Created By hjg on 2020/5/1
 */
public class CommentEvent extends BaseEvent {
    public static final int ADD_COMMENT_OK = 401;
    public static final int ADD_COMMENT_FAIL = 402;
    private String message = "";
    private NormalRoot normalRoot;

    public NormalRoot getNormalRoot() {
        return normalRoot;
    }

    public void setNormalRoot(NormalRoot normalRoot) {
        this.normalRoot = normalRoot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
