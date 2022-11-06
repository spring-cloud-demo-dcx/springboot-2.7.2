package com.skuu.controller;

import com.skuu.entity.User;
import com.skuu.enums.CallStatusEnum;
import com.skuu.enums.StatusEnum;
import com.skuu.pojo.vo.ReturnVO;
import com.skuu.pojo.vo.UserCallStatusVo;
import com.skuu.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dcx
 * @since 2022-08-28
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(summary = "获取视频通话状态")
    @GetMapping("/call-status")
    public ReturnVO<UserCallStatusVo> getUserIno(@RequestParam @Valid @NotBlank String userId) {
        User user = userService.getUser(userId);
        UserCallStatusVo userCallStatusVo = new UserCallStatusVo();
        if (user == null) {
            userCallStatusVo.setCallStatus(CallStatusEnum.NOT_IN_CALL.getCode());
        } else {
            Integer status = user.getStatus();
            userCallStatusVo.setUserId(userId);
            userCallStatusVo.setRoomId(user.getRoomId());
            List<Integer> callStatus = StatusEnum.getCallStatus();
            if (callStatus.contains(status)) {
                userCallStatusVo.setCallStatus(CallStatusEnum.CALLING.getCode());
            } else {
                userCallStatusVo.setCallStatus(CallStatusEnum.NOT_IN_CALL.getCode());
            }
        }
        return ReturnVO.ok(userCallStatusVo);
    }

}
