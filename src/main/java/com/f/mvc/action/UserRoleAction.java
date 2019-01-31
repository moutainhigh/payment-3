package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.mvc.entity.SysRole;
import com.f.mvc.entity.User;
import com.f.mvc.entity.UserRole;
import com.f.mvc.service.SysRoleService;
import com.f.mvc.service.UserRoleService;
import com.f.mvc.service.UserService;
import com.f.validation.CreateGroup;
import com.f.vo.ResponseVo;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User: bvsoo
 * Date: 2018/9/24
 * Time: 下午3:55
 */
@RestController
@RequestMapping(value = "/userRole")
@Api(value = "/userRole", description = "用户角色")
@Log4j2
public class UserRoleAction extends BaseAction {
    private static final long serialVersionUID = 222958499115366813L;


    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 查询用户角色
     *
     * @return
     */
    @ApiOperation(value = "/query", notes = "查询用户角色")
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo query(HttpServletRequest request) {
        Long centerUserId = super.getUserId(request);
        List<UserRole> userRoles = userRoleService.findByUserId(centerUserId);
        List<SysRole> sysRoles = userRoles.stream().map(o -> sysRoleService.findSysRoleById(o.getSysRoleId())).collect(Collectors.toList());
        return ResponseVo.builder().data(sysRoles).build();
    }


    /**
     * 管理员查询用户的角色
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "/manager/query/{userid}", notes = "管理员查询用户角色")
    @RequestMapping(value = "/manager/query/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo managerQuery(@PathVariable(value = "userid") long userId) {
        if (userId < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        List<UserRole> userRoles = userRoleService.findByUserId(userId);
        List<SysRole> sysRoles = userRoles.stream().map(o -> sysRoleService.findSysRoleById(o.getSysRoleId())).collect(Collectors.toList());
        return ResponseVo.builder().data(sysRoles).build();
    }


    /*
     * @Author rebysfu@gmail.com
     * @Description 添加角色
     * @Date 下午1:39 2019/1/4
     * @Param [UserRoleVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/generate", notes = "添加角色")
    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo generate(@RequestBody @Validated UserRole UserRoleVO  ) {
        if (UserRoleVO.getSysRoleId() < 1 || UserRoleVO.getUserId() < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数异常").build();
        if (UserRoleVO.getSysRoleId() == 1 && UserRoleVO.getUserId() == 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("添加失败").build();
        User user = userService.findUserById(UserRoleVO.getUserId());
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("用户不存在").build();
        SysRole sysRole = sysRoleService.findSysRoleById(UserRoleVO.getSysRoleId());
        if (sysRole == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("角色不存在").build();
        String lock = super.generateMutex(user.getAccount());
        synchronized (lock) {
            List<UserRole> userRoles = userRoleService.findByUserId(UserRoleVO.getUserId());
            Optional<UserRole> optionalUserRole = userRoles.stream().filter(o -> o.getSysRoleId().longValue() == UserRoleVO.getSysRoleId()).findFirst();
            if (optionalUserRole.isPresent()) return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            UserRole userRole = new UserRole();
            userRole.setSysRoleId(UserRoleVO.getSysRoleId());
            userRole.setUserId(UserRoleVO.getUserId());
            userRole.setCreateTime(new Date());
            int row = userRoleService.addUserRole(userRole);
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().build();
        }

    }


    /*
     * @Author rebysfu@gmail.com
     * @Description 移出角色
     * @Date 下午1:53 2019/1/4
     * @Param [UserRoleVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/remove", notes = "移除角色")
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo remove(@RequestBody @Validated(CreateGroup.class) UserRole UserRoleVO ) {
        if (UserRoleVO.getUserId() < 1 || UserRoleVO.getSysRoleId() < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数异常").build();
        if (UserRoleVO.getUserId() == 1 && UserRoleVO.getSysRoleId() == 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("删除失败").build();
        User user = userService.findUserById(UserRoleVO.getUserId());
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("用户不存在").build();
        SysRole sysRole = sysRoleService.findSysRoleById(UserRoleVO.getSysRoleId());
        if (sysRole == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("角色不存在").build();
        String lock = super.generateMutex(user.getAccount());
        synchronized (lock) {
            List<UserRole> userRoles = userRoleService.findByUserId(UserRoleVO.getUserId());
            Optional<UserRole> optionalUserRole = userRoles.stream().filter(o -> o.getSysRoleId().longValue() == UserRoleVO.getSysRoleId()).findFirst();
            if (!optionalUserRole.isPresent()) return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            int row = userRoleService.deleteUserRole(optionalUserRole.get());
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().build();
        }

    }

    /*
     * @Author rebysfu@gmail.com
     * @Description 批量操作用户角色
     * @Date 下午4:08 2019/1/4
     * @Param [userRoleVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/batch", notes = "批量操作用户角色")
    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo batch(@RequestBody UserRoleVO userRoleVO ) throws Exception {
        if (userRoleVO.getUserId() < 1 || (userRoleVO.getNewly().length < 1 && userRoleVO.getWaste().length < 1))
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        if (userRoleVO.getUserId() == 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        User user = userService.findUserById(userRoleVO.getUserId());
        if (user == null)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        String lock = super.generateMutex(user.getAccount());
        long[] newly=userRoleVO.getNewly();
        if (newly == null) {
            newly = new long[0];
        }
        synchronized (lock) {
            List<UserRole> userRoles = userRoleService.findByUserId(userRoleVO.getUserId());
            long[] finalNewly = newly;
            Optional<UserRole> optionalUserRole = userRoles.stream().filter(o -> {
                Long roleId = o.getSysRoleId();
                int row = Arrays.binarySearch(finalNewly, roleId.longValue());
                return row > 0;
            }).findFirst();
            if (optionalUserRole.isPresent())
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();

            int row = userRoleService.userRoleBatch(userRoleVO.getUserId(), userRoleVO.getNewly(), userRoleVO.getWaste());
            if (row < 1)
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseVo.builder().data(userRoleService.findByUserId(userRoleVO.getUserId())).build();

        }
    }

    @Data
    @ApiModel(value = "批量操作用户角色")
    static class UserRoleVO{
        @NotNull(message = "用户Id不能为空")
        @ApiModelProperty(value = "用户Id")
        long userId;
        //@NotEmpty(message = "新增角色不能为空")
        @ApiModelProperty(value = "新增角色")
        long[] newly;
       // @NotEmpty(message = "移出角色不能为空")
        @ApiModelProperty(value = "移除角色")
        long[] waste;
    }
}
