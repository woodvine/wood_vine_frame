package com.wood.service.define;

import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;

/**
 * 
 * @title       :UserService
 * @description :用户操作相关的服务层
 *               突然意识到接口编程还是很有必要的，不能省掉接口，直接在Action层里面写
 *               业务代码，这样太不可扩展了。万一，哪天服务层业务改变了，则Action层就乱了。
 *               如果遵从接口编程规范，Action层完全可以使固定的调用模式，使用Spring重新
 *               配置bean的实现就好了。
 * @update      :2015-1-8 上午10:53:19
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-8
 */
public interface UserService {
   public ActionResponse login(String loginName,String userPwd);
   public ActionResponse register(TbUser user);
   public ActionResponse modifyUserInfo(TbUser user);
   public ActionResponse queryByPage(TbUser user);
   public ActionResponse queryAuthority(TbUser user);
   
}
