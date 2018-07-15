package liu.york.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件上传 下载
 */
@RestController
public class FileController {

    /**
     * 参数 file 需要和上传的参数保持一致
     * @param file
     */
    @PostMapping("/file/upload")
    public void fileUpload(MultipartFile file) throws IOException {
        // 上传的参数名称
        System.out.println(file.getName());
        // 上传文件的原始文件名称
        System.out.println(file.getOriginalFilename());
        // 上传文件大小
        System.out.println(file.getSize());

        /*
         * 1 保存上传的文件，这里采用保存在本地路径
         * 2 保存时重新命名
         */
//        String savePath = "";
//        File localFile = new File(savePath, new Date().getTime() + ".txt");

        /*
         * 保存，直接调用 MultipartFile 对象的 transferTo 方法
         */
//        file.transferTo(localFile);

        /*
         * 如果将上传的文件保存在别的地方，可以使用 file 对象获取 inputStream 流，然后保存即可
         */
//        InputStream inputStream = file.getInputStream();

    }

    /**
     * 文件下载
     * @param fileId
     */
    @GetMapping("/file/download")
    public void fileDownload(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*
         * 下载文件参数
         *  1 文件的保存地址
         *  2 文件名称
         */
        String savePath = "";
        String fileName = "";
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(savePath, fileName));
                OutputStream fileOutputStream = response.getOutputStream())
        {
            // 文件下载出去的名称
            String name = "";
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=" + name +"");

            // 使用工具类完成 copy
            IOUtils.copy(fileInputStream, fileOutputStream);
            fileOutputStream.flush();
        }
    }
}
