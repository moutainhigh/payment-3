package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.StateEnum;
import com.f.mvc.entity.Channel;
import com.f.mvc.entity.WithDrawRequest;
import com.f.mvc.service.ChanelService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.utils.StringUtils;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import com.f.vo.Page;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-05 下午4:05
 **/
@RestController
@RequestMapping(value = "/channel")
@Api(value = "/channel", description = "渠道信息管理")
public class ChannelAction extends BaseAction {
    @Autowired
    private ChanelService chanelService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;

    @ApiOperation(value = "查询所有渠道信息", notes = "分页查询")
    @PostMapping("/list")
    public ResponseVo list(@RequestBody Page page) {
        Example example = new Example(Channel.class);
        example.setOrderByClause("create_time");
        if (StringUtils.isNotEmpty(page.getKeyword())) {
            example.createCriteria().orLike("channelId", page.getKeyword() + "%").orLike("channelName", page.getKeyword() + "%");
        }
        return ResponseVo.builder().data(chanelService.queryPageListByExample(example, page.getPageNum(), page.getPageSize())).build();
    }


    @ApiOperation(value = "查询启用渠道信息", notes = "绑定第三方法账号，提供的查询渠道信息")
    @GetMapping("/querynormallist")
    public ResponseVo queryNormalList() {
        Channel channel = new Channel();
        channel.setStatus(StateEnum.NORMAL.getCode());
        return ResponseVo.builder().data(chanelService.queryListByWhere(channel)).build();
    }

    @ApiOperation(value = "新增渠道", notes = "格式：\n { \"channelId\": \"1002\", \"channelName\": \"clol支付\"}")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated(CreateGroup.class) ChannelVO channelVO) {
        Channel channel = new Channel();
        channel.setChannelId(channelVO.getChannelId());
        Channel info = chanelService.queryOne(channel);
        if (info != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道已存在").build();
        }
        if (channelVO.getStatus() != null && StateEnum.isValidcode(channelVO.getStatus())) {
            channel.setStatus(channelVO.getStatus());
        }
        channel.setChannelName(channelVO.getChannelName());
        Date dateTime = new Date();
        channel.setCreateTime(dateTime);
        channel.setCreateUserId(getUserId());
        channel.setUpdateTime(dateTime);
        channel.setModifyUserId(getUserId());
        int row = chanelService.save(channel);
        if (row > 0) {
            return ResponseVo.builder().data(channel).build();
        }
        return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
    }

    @ApiOperation(value = "修改", notes = "格式：\n {\"channelName\": \"string\",\"id\": 1, \"status\": 0} ")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated(UpdateGroup.class) ChannelVO channelVO) {
        Channel channel = chanelService.queryById(channelVO.getId());
        if (channel == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (channelVO.getStatus() == null && StringUtils.isEmpty(channelVO.getChannelName())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数不能都为空").build();
        }
        if (channelVO.getStatus() != null) {
            if (StateEnum.isValidcode(channelVO.getStatus())) {
                channel.setStatus(channelVO.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        if (StringUtils.isNotEmpty(channelVO.getChannelName())) {
            channel.setChannelName(channelVO.getChannelName());
        }
        channel.setModifyUserId(getUserId());
        channel.setUpdateTime(new Date());
        chanelService.updateSelective(channel);
        return ResponseVo.builder().data(channel).build();
    }

    @ApiOperation(value = "删除渠道信息", notes = "删除")
    @DeleteMapping("delete/{id}")
    public ResponseVo deleteByPrimaryKey(@PathVariable(value = "id") Long id) {
        Channel channel = chanelService.queryById(id);
        if (channel == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道不存在").build();
        }
        WithDrawRequest withDrawRequest = new WithDrawRequest();
        withDrawRequest.setChannelId(channel.getChannelId());
        List list = withDrawRequestService.queryListByWhere(withDrawRequest);
        if (!CollectionUtils.isEmpty(list)) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道已被关联不允许删除").build();
        }
        chanelService.deleteById(id);
        return ResponseVo.builder().code(HttpStatus.OK).build();
    }

    @Data
    @ApiModel(value = "渠道信息")
    static class ChannelVO {
        @ApiModelProperty(value = "主键id", notes = "新增不需要传，修改必须传", example = "1")
        @NotNull(message = "主键Id不能为空", groups = UpdateGroup.class)
        private Long id;

        /**
         * 渠道编码
         */
        @NotNull(message = "渠道编码不能为空", groups = CreateGroup.class)
        @ApiModelProperty(value = "渠道编码", notes = "新增必填")
        private String channelId;

        /**
         * 渠道名称
         */
        @NotNull(message = "渠道名称不能为空", groups = {CreateGroup.class})
        @ApiModelProperty(value = "渠道名称", notes = "新增必填，修改选填")
        private String channelName;

        /**
         * 渠道状态  0 使用 1 停用
         */
        @ApiModelProperty(value = "渠道状态", notes = "0 启用 1停用", example = "0")
        @NotNull(message = "渠道状态不能为空")
        private Integer status;
    }
}
