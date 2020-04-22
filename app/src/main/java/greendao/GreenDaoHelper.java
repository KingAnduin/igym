package greendao;

import android.content.Context;

import greendao.gen.DaoMaster;

/**
 * Created by a'su's on 2018/11/13.
 */

public class GreenDaoHelper extends DaoMaster.OpenHelper {
    public static final String DB_NAME = "igym.db";      //数据库名字
    //public static final String DB_NAME = "icompetition.db";      //数据库名字
    public Context mContext;     //上下文

    public GreenDaoHelper(Context context) {
        super(context, DB_NAME);
        this.mContext = context;
    }
}
