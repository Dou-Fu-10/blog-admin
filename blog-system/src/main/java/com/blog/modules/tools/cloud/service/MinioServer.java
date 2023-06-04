package com.blog.modules.tools.cloud.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.blog.exception.BadRequestException;
import com.blog.modules.tools.cloud.config.MinioConfig;
import com.blog.utils.FileUtil;
import com.blog.utils.StringUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PC
 */
@Slf4j
@Service
public class MinioServer {
    @Resource
    private MinioConfig prop;

    @Resource
    private MinioClient minioClient;


    /**
     * 查看存储bucket是否存在
     *
     * @return boolean
     */
    public boolean bucketExists(String bucketName) {
        boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 创建存储bucket
     *
     * @return Boolean
     */
    public boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建文件桶（建议租户ID为桶的名称）
     */
    public boolean exitsBucket(String bucket) {
        boolean found = false;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("create bucket is error", e);
        }
        return found;
    }


    /**
     * 删除一个桶
     *
     * @param bucket 桶名称
     */
    public boolean removeBucket(String bucket) throws Exception {
        // 删除之前先检查`my-bucket`是否存在。
        boolean found = bucketExists(bucket);
        if (found) {
            Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                //有对象文件，则删除失败
                if (item.size() > 0) {
                    return false;
                }
            }
            // 删除`bucketName`存储桶，注意，只有存储桶为空时才能删除成功。
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            found = bucketExists(bucket);
            return !found;
        }
        return false;
    }


    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BadRequestException("获取全部bucket失败");
    }

    /**
     * 获取单个桶中的所有文件对象名称
     *
     * @param bucket 桶名称
     * @return {@link List}<{@link String}>
     */
    public List<String> getBucketObjectName(String bucket) {
        boolean exsit = bucketExists(bucket);
        if (exsit) {
            List<String> listObjectName = new ArrayList<>();
            try {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    listObjectName.add(item.objectName());
                }
                return listObjectName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new BadRequestException("获取单个桶中的所有文件失败");
    }

    /**
     * 图片上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String uploadImages(MultipartFile file) {
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new BadRequestException("文件上传错误");
        }
        // 文件大小验证
        FileUtil.checkSize(prop.getImagesMaxSize(), file.getSize());
        // 验证文件上传的格式
        String image = "jpg png jpeg";
        String fileType = FileUtil.getExtensionName(originalFilename);
        if (fileType != null && !image.contains(fileType)) {
            throw new BadRequestException("文件格式错误！, 仅支持 " + image + " 格式");
        }
        // 保存文件名
        String fileName = IdUtil.simpleUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 文件名加路径
        String objectName = DateUtil.format(DateUtil.date(), "yyyy-MM/dd") + "/" + fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(prop.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("文件上传失败");
        }
        return objectName;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new RuntimeException();
        }
        String fileName = IdUtil.simpleUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String objectName = DateUtil.format(DateUtil.date(), "yyyy-MM/dd") + "/" + fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(prop.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * 预览图片
     *
     * @param objectName 对象名 (文件夹名 + 文件名)
     * @return uri 地址
     */
    public String getBucketObject(String objectName) {
        try {

            return getBucketObject(prop.getBucketName(), objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BadRequestException("图片获取失败");
    }

    /**
     * 预览图片
     *
     * @param bucket     桶名称
     * @param objectName 对象名 (文件夹名 + 文件名)
     * @return uri 地址
     */
    public String getBucketObject(String bucket, String objectName) {
        boolean found = exitsBucket(bucket);
        if (found) {
            // 查看文件地址
            GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().bucket(bucket).object(objectName).method(Method.GET).build();
            try {
                return minioClient.getPresignedObjectUrl(build);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new BadRequestException("图片URL获取失败");
    }

    /**
     * 文件下载
     *
     * @param objectName 文件名称
     * @param res        response
     */
    public void download(String objectName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(prop.getBucketName())
                .object(objectName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + objectName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看文件对象
     *
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(prop.getBucketName()).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("获取存储bucket对象信息失败");
        }
        return items;
    }

    /**
     * 删除
     *
     * @param objectName 对象名 (文件夹名 + 文件名)
     * @return boolean
     */
    public boolean remove(String objectName) {
        try {
            remove(prop.getBucketName(), objectName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param bucket     桶名称
     * @param objectName 对象名称
     * @return boolean
     */
    public boolean remove(String bucket, String objectName) {
        try {
            boolean exsit = bucketExists(bucket);
            if (exsit) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
                return true;
            }
        } catch (Exception e) {
            log.error("removeObject", e);
        }
        return false;
    }

    /**
     * 批量删除文件
     *
     * @param bucket      桶名称
     * @param objectNames 对象名称
     * @return boolean
     */
    public boolean batchRemove(String bucket, List<String> objectNames) {
        boolean exsit = bucketExists(bucket);
        if (exsit) {
            try {
                List<DeleteObject> objects = new LinkedList<>();
                for (String str : objectNames) {
                    objects.add(new DeleteObject(str));
                }
                minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build());
                return true;
            } catch (Exception e) {
                log.error("removeObject", e);
            }
        }
        return false;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucket     桶名称
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    public InputStream getObjectInputStream(String bucket, String objectName) {
        boolean exsit = bucketExists(bucket);
        if (exsit) {
            try {
                StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
                if (statObjectResponse.size() > 0) {
                    // 获取objectName的输入流。
                    return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new BadRequestException("获取文件失败");
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    public InputStream getObjectInputStream(String objectName) {
        try {
            return getObjectInputStream(prop.getBucketName(), objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BadRequestException("获取文件失败");
    }


    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public Boolean checkObjectIsExist(String objectName) {
        return this.checkObjectIsExist(prop.getBucketName(), objectName);
    }

    /**
     * 判断文件是否存在
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public Boolean checkObjectIsExist(String bucketName, String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}