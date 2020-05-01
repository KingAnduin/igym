package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.NormalRoot;
import com.example.thinkpad.icompetition.model.event.CommentEvent;
import com.example.thinkpad.icompetition.model.impl.CommentModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.ICommentPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.CommentActivity;

/**
 * Created By hjg on 2020/5/1
 */
public class CommentPresenter extends BasePresenter<CommentActivity, CommentModel>
        implements IBasePresenter, ICommentPresenter {
    public CommentPresenter(CommentActivity view) {
        super(view);
    }

    @Override
    public void addComment(int order_id, String comment) {
        mModel.addComment(order_id,comment);
    }

    @Override
    protected CommentModel getModel(Handler handler) {
        return new CommentModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        if(msg.what == CommentEvent.ADD_COMMENT_OK){
            NormalRoot root = ((CommentEvent)msg.obj).getNormalRoot();
            mView.addCommenetResponse();
        }else {
            mView.failBecauseNotNetworkReturn(msg.what);
        }
    }
}
