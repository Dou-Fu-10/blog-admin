package com.blog.modules.tools.controller;

import com.blog.modules.tools.domain.EmailConfig;
import com.blog.modules.tools.domain.vo.EmailVo;
import com.blog.modules.tools.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 发送邮件
 *
 * @author ty
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/email")
// @Api(tags = "工具：邮件管理")
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<Object> queryConfig(){
        return new ResponseEntity<>(emailService.find(),HttpStatus.OK);
    }

    @PutMapping
    // @ApiOperation("配置邮件")
    public ResponseEntity<Object> updateConfig(@Validated @RequestBody EmailConfig emailConfig) throws Exception {
        emailService.config(emailConfig,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    // @ApiOperation("发送邮件")
    public ResponseEntity<Object> sendEmail(@Validated @RequestBody EmailVo emailVo){
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
