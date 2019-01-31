package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.mvc.entity.Menu;
import com.f.mvc.service.MenuService;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * User: bvsoo
 * Date: 2018/8/20
 * Time: 下午7:50
 */
@RestController
@RequestMapping(value = "/menu")
@Api(value = "/menu", description = "菜单")
@Log4j2
public class MenuAction extends BaseAction {
    private static final long serialVersionUID = 7500351868032439916L;
    @Autowired
    private MenuService menuService;

    /**
     * 查询所有菜单
     *
     * @return
     */
    @ApiOperation(value = "/query", notes = "查询所有菜单")
    @GetMapping(value = "/query")
    public ResponseVo query() {
        return ResponseVo.builder().data(menuService.findAllMenu()).build();
    }


    /*
     * @Author rebysfu@gmail.com
     * @Description 新增菜单
     * @Date 上午10:43 2019/1/4
     * @Param [parames]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/generate", notes = "新增菜单")
    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo generate(@RequestBody @Validated(CreateGroup.class) Menu menuVO) {
        Long centerUserId = super.getUserId();
        Menu menu = new Menu();
        menu.setUrl(menuVO.getUrl());
        menu.setName(menuVO.getName());
        menu.setType(0);
        menu.setCreateUserId(centerUserId);
        menu.setCreateTime(new Date());
        int row = menuService.addNewMenu(menu);
        if (row < 1) return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().data(menu).build();
    }

    /*
     * @Author rebysfu@gmail.com
     * @Description  修改菜单
     * @Date 上午11:39 2019/1/4
     * @Param [menuVO]
     * @return com.f.vo.ResponseVo
     **/
    @ApiOperation(value = "/modify", notes = "修改菜单")
    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo modify(@RequestBody @Validated(UpdateGroup.class) Menu menuVO) {
        Long centerUserId = super.getUserId();
        if (menuVO.getId() < 1){
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("请求参数错误").build();
        }
        Menu menu = menuService.findMenuById(menuVO.getId());
        if (menu == null){
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("修改的菜单不存在").build();
        }
        menu.setUrl(menuVO.getName());
        menu.setName(menuVO.getName());
        menu.setModifyUserId(centerUserId);
        menu.setModifyTime(new Date());
        int row = menuService.updateMenu(menu);
        if (row < 1) return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().data(menu).build();
    }


    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "/remove/id", notes = "删除菜单")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo modify(@PathVariable(value = "id") long id) {
        if (id < 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数格式错误").build();
        Menu menu = menuService.findMenuById(id);
        if (menu == null)
            return ResponseVo.builder().code(HttpStatus.OK).message("菜单已删除").build();
        if (menu.getType().intValue() == 1)
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("不允许删除固定菜单").build();
        int row = menuService.deleteMenuById(menu);
        if (row < 1) return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseVo.builder().data(menu).build();
    }


}
