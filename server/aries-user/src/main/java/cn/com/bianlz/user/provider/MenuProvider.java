package cn.com.bianlz.user.provider;

import cn.com.bianlz.common.enums.Status;

/**
 * Created by bianlanzhou on 17/9/1.
 * Description
 */
public class MenuProvider {
    public String getMenuIdByRoleId(Long id,Integer level){
        StringBuffer sb = new StringBuffer();
        sb.append(" select b.* from menu_role a ,menu b ");
        sb.append(" where a.menu_id = b.id ");
        if(id!=null){
            sb.append(" and a.role_id = ").append(id);
        }else{
            sb.append(" and a.role_id = -100 ");
        }
        if(level!=null&&level>0){
            sb.append(" and b.level=").append(level);
        }
        sb.append(" and b.status = a.status = ").append(Status.VALID.getCode());
        return sb.toString();
    }

    public String getSubMenu(Long parentId,Long roleId,Integer level){
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from menu a where ");
        sb.append(" parent_id = ").append(parentId);
        sb.append(" and level = ").append(level);
        sb.append(" and status = ").append(Status.VALID.getCode());
        sb.append(" and exists (select 1 from menu_role b where a.id=b.menu_id ");
        sb.append(" and b.status=").append(Status.VALID.getCode());
        sb.append(" and b.role_id=").append(roleId).append(")");
        return sb.toString();
    }

    public String saveMenuRole(Long rid,Long menuId){
        StringBuilder sb = new StringBuilder();
        sb.append(" insert into menu_role (menu_id,role_id) values");
        sb.append(" ( ").append(rid).append(",").append(menuId).append(")");
        System.out.println("sql"+sb.toString());
        return sb.toString();
    }

    public String updateMenuStatus(Long rid,String inCase,String notInCase,Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("update menu_role set status = ").append(status);
        sb.append(" where role_id=").append(rid);
        if(null!=inCase){
            sb.append(" and menu_id in (").append(inCase).append(")");
        }
        if(null!=notInCase){
            sb.append(" and menu_id not in (").append(notInCase).append(")");
        }
        if(null==inCase&&null==notInCase){
            sb.append(" and menu_id =-100");
        }
        return sb.toString();
    }
}
