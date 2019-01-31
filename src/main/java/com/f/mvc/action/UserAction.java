package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.mvc.entity.Menu;
import com.f.mvc.entity.RoleMenu;
import com.f.mvc.entity.User;
import com.f.mvc.entity.UserRole;
import com.f.mvc.service.*;
import com.f.vo.ResponseVo;
import com.f.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: bvsoo
 * Date: 2018/9/24
 * Time: 下午3:24
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "/user", description = "用户")
public class UserAction extends BaseAction {
    private static final long serialVersionUID = -7366850015365031991L;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询账号的菜单
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "/menus", notes = "查询菜单")
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResponseVo menus(HttpServletRequest request) {
        Long centerUserId = super.getUserId(request);
        List<Menu> menus = Lists.newArrayList();
        List<UserRole> userRoles = userRoleService.findByUserId(centerUserId);
        userRoles.forEach(o -> {
            List<RoleMenu> roleMenus = roleMenuService.findRoleMenuByRoleId(o.getSysRoleId());
            roleMenus.forEach(a -> menus.add(menuService.findMenuById(a.getMenuId())));
        });
        List<Menu> menuList = menus.stream().distinct().sorted(Comparator.comparing(Menu::getId)).collect(Collectors.toList());
        return ResponseVo.builder().data(menuList).build();
    }


    /**
     * 查询所有用户
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @ApiOperation(value = "/query", notes = "查询所有用户")
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo managerQuery(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "0") int size,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        if (page < 1 || size < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("分页参数错误").build();
        Page<User> userPage = PageHelper.startPage(page, size);
        userService.findUserByParam(Strings.isNullOrEmpty(keyword) ? null : keyword, userPage);
        return ResponseVo.builder().data(userPage.toPageInfo()).build();
    }


    /*
     * @Author rebysfu@gmail.com
     * @Description 添加新用户
     * @Date 下午2:45 2019/1/4
     * @Param [userVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/generate", notes = "添加新用户")
    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo generate(@RequestBody @Validated UserVO userVO) {
        String lock = super.generateMutex(String.valueOf(userVO.getAccount()));
        Long userId = super.getUserId();
        User user = new User();
        synchronized (lock) {
            User old = userService.findUserByAccount(userVO.getAccount().trim());
            if (old != null)
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            user.setAccount(userVO.getAccount());
            user.setName(userVO.getAccount());
            user.setPassword(passwordEncoder.encode(userVO.getPassword().trim()));
            user.setCreateUserId(userId);
            user.setCreateTime(new Date());
            int row = userService.addUser(user, userVO.getRoleIds());
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().data(user).build();

        }
    }

    /*
     * @Author rebysfu@gmail.com
     * @Description 管理员重置密码
     * @Date 下午3:23 2019/1/4
     * @Param [userVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/modify/password", notes = "管理员重置用户密码")
    @RequestMapping(value = "/modify/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo modifyPassword(@RequestBody @Validated modifyPassword userVO) {
        if (Strings.isNullOrEmpty(userVO.getPassword()))
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        User user = userService.findUserById(userVO.getId());
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        user.setPassword(passwordEncoder.encode(userVO.getPassword().trim()));
        int row = userService.modifyUser(user);
        if (row < 1)
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().build();
    }


    /**
     * 删除账号
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "/remove", notes = "删除账号")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo removeUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("账号不存在").build();
        int row = userService.removeUser(user);
        if (row < 1)
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().build();
    }


    @ApiOperation(value = "/change/password", notes = "修改密码")
    @RequestMapping(value = "/change/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo changePassword(@RequestBody ChangePassword changePassword) {
        long userId = super.getUserId();
        User user = userService.findUserById(userId);
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword()))
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("原始密码错误").build();
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        int row = userService.modifyUser(user);
        if (row < 1)
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().build();
    }

    @Data
    @ApiModel(value = "修改密码")
    static class ChangePassword {
        @NotNull(message = "旧密码不能为空")
        private String oldPassword;
        @NotNull(message = "新密码不能为空")
        private String newPassword;
    }
    @ApiModel(value = "重置密码")
    @Data
    static class modifyPassword {
        @NotNull(message = "用户id不能为空")
        private Long id;
        @NotNull(message = "用户密码不能为空")
        private String password;//密码
    }
}
