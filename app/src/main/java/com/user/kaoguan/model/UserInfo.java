package com.user.kaoguan.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/4/8.
 */

public class UserInfo implements Serializable {
    //{ id ：用户ID，nickname：用户昵称，gender_name：用户性别，avatar_url：头像，mobile ：手机号，is_vip ：是否是vip（1：是；2：否；），day ：VIP剩余天数,"vip_end_time":"0","vip_end_date":""}
    @JSONField(name = "id")
    public String user_id;
    public String token;
   

    public String getUser_id() {
        return this == null || user_id == null ? "" : user_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserInfo) {
            UserInfo info = (UserInfo) obj;
            return info.user_id != null && user_id != null && info.user_id.equals(user_id);
        }
        return super.equals(obj);
    }
}
