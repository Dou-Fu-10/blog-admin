package com.blog.modules.mnt.controller;

import com.blog.modules.logging.annotation.Log;
import com.blog.modules.mnt.domain.DeployHistory;
import com.blog.modules.mnt.domain.dto.DeployDto;
import com.blog.modules.mnt.domain.dto.DeployQueryParam;
import com.blog.modules.mnt.service.DeployService;
import com.blog.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 运维：部署管理
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deploy")
public class DeployController {

    private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
    private final DeployService deployService;


    /**
     * 导出部署数据
     * @param response
     * @param criteria
     * @throws IOException
     */
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('database:list')")
    public void download(HttpServletResponse response, DeployQueryParam criteria) throws IOException {
        deployService.download(deployService.queryAll(criteria), response);
    }

    /**
     * 查询部署
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping
    @PreAuthorize("@el.check('deploy:list')")
    public ResponseEntity<Object> query(DeployQueryParam criteria, Pageable pageable) {
        return new ResponseEntity<>(deployService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 新增部署
     * @param resources
     * @return
     */
    @Log("新增部署")
    @PostMapping
    @PreAuthorize("@el.check('deploy:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DeployDto resources) {
        deployService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 修改部署
     * @param resources
     * @return
     */
    @Log("修改部署")
    @PutMapping
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody DeployDto resources) {
        deployService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除部署
     * @param ids
     * @return
     */
    @Log("删除部署")
    @DeleteMapping
    @PreAuthorize("@el.check('deploy:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        deployService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 上传文件部署
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @Log("上传文件部署")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            File deployFile = new File(fileSavePath + fileName);
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            deployService.deploy(fileSavePath + fileName, id);
        } else {
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:" + Objects.requireNonNull(file).getOriginalFilename());
        Map<String, Object> map = new HashMap<>(2);
        map.put("errno", 0);
        map.put("id", fileName);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 系统还原
     * @param resources
     * @return
     */
    @Log("系统还原")
    @PostMapping(value = "/serverReduction")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> serverReduction(@Validated @RequestBody DeployHistory resources) {
        String result = deployService.serverReduction(resources);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 服务运行状态
     * @param resources
     * @return
     */
    @Log("服务运行状态")
    @PostMapping(value = "/serverStatus")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> serverStatus(@Validated @RequestBody DeployDto resources) {
        String result = deployService.serverStatus(resources);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 启动服务
     * @param resources
     * @return
     */
    @Log("启动服务")
    @PostMapping(value = "/startServer")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> startServer(@Validated @RequestBody DeployDto resources) {
        String result = deployService.startServer(resources);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 停止服务
     * @param resources
     * @return
     */
    @Log("停止服务")
    @PostMapping(value = "/stopServer")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> stopServer(@Validated @RequestBody DeployDto resources) {
        String result = deployService.stopServer(resources);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
