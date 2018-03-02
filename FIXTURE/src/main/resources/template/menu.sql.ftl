-- 菜单SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`,`perms`,`menu_type`,`icon`,`sort`)
    VALUES (0,'${comments}', '${pathName}/index','${pathName}:info',1,null,0);

-- 菜单权限
     
set @parentId = @@identity;

-- 权限对应按钮SQL
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`,`perms`,`menu_type`,`icon`,`sort`)
     SELECT @parentId,'查看','${pathName}/list','${pathName}:list',2,null,1;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`,`perms`,`menu_type`,`icon`,`sort`)
    SELECT @parentId,'新增','${pathName}/save', '${pathName}:save',2,null,2;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`,`perms`,`menu_type`,`icon`,`sort`)
     SELECT @parentId,'修改','${pathName}/update','${pathName}:update',2,null,3;
INSERT INTO `sys_menu` (`parent_id`, `name`, `url`,`perms`,`menu_type`,`icon`,`sort`)
    SELECT @parentId,'删除', '${pathName}/delete','${pathName}:delete',2,null,4;
 
			

