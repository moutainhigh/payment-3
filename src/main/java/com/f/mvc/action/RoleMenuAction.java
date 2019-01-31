package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.mvc.entity.Menu;
import com.f.mvc.entity.RoleMenu;
import com.f.mvc.service.MenuService;
import com.f.mvc.service.RoleMenuService;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User: bvsoo
 * Date: 2018/9/25
 * Time: 下午5:01
 */
@RestController
@RequestMapping(value = "/roleMenu")
@Api(value = "/roleMenu", description = "角色菜单")
@Log4j2
public class RoleMenuAction extends BaseAction {
    private static final long serialVersionUID = 1040758109498014818L;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;


    /*
     * @Author rebysfu@gmail.com
     * @Description 添加角色菜单
     * @Date 下午2:10 2019/1/4
     * @Param [roleMenuVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/generate", notes = "添加角色菜单")
    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo generate(@RequestBody @Validated RoleMenu roleMenuVO ) {
        if (roleMenuVO.getRoleId() <= 1 || roleMenuVO.getMenuId() < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        Menu menu = menuService.findMenuById(roleMenuVO.getMenuId());
        if (menu == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        String lock = super.generateMutex(String.valueOf(roleMenuVO.getRoleId()));
        Long centerUserId = super.getUserId();
        synchronized (lock) {
            List<RoleMenu> roleMenus = roleMenuService.findRoleMenuByRoleId(roleMenuVO.getRoleId());
            Optional<RoleMenu> optionalRoleMenu = roleMenus.stream().filter(o -> o.getMenuId().longValue() == roleMenuVO.getMenuId()).findFirst();
            if (optionalRoleMenu.isPresent())
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleMenuVO.getRoleId());
            roleMenu.setMenuId(roleMenuVO.getMenuId());
            roleMenu.setCreateUserId(centerUserId);
            roleMenu.setCreateTime(new Date());
            int row = roleMenuService.addNewRow(roleMenu);
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().data(roleMenu).build();
        }

    }

    /*
     * @Author rebysfu@gmail.com
     * @Description 删除角色菜单
     * @Date 下午2:07 2019/1/4
     * @Param [RoleMenuVo]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/remove", notes = "删除角色菜单")
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo remove(@RequestBody RoleMenu RoleMenuVo) {
        if (RoleMenuVo.getRoleId() <= 1 || RoleMenuVo.getMenuId() < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        Menu menu = menuService.findMenuById(RoleMenuVo.getMenuId());
        if (menu == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("菜单不存在").build();
        String lock = super.generateMutex(String.valueOf(RoleMenuVo.getRoleId()));
        synchronized (lock) {
            List<RoleMenu> roleMenus = roleMenuService.findRoleMenuByRoleId(RoleMenuVo.getRoleId());
            Optional<RoleMenu> optionalRoleMenu = roleMenus.stream().filter(o -> o.getMenuId().longValue() == RoleMenuVo.getMenuId()).findFirst();
            if (!optionalRoleMenu.isPresent())
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            int row = roleMenuService.deleteRoleMenuById(optionalRoleMenu.get());
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().build();
        }

    }

    /**
     * 查询角色菜单
     *
     * @param roleId
     * @return
     */
    @ApiOperation(value = "/query", notes = "查询角色菜单")
    @RequestMapping(value = "/query/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo query(@PathVariable(value = "roleId") long roleId) {
        if (roleId < 0)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        if (roleId <= 0)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        List<RoleMenu> roleMenus = roleMenuService.findRoleMenuByRoleId(roleId);
        List<Menu> menus = roleMenus.stream().map(o -> menuService.findMenuById(o.getMenuId())).collect(Collectors.toList());
        return ResponseVo.builder().data(menus).build();
    }
}
