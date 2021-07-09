package com.eva.api.common;

import com.eva.api.BaseController;
import com.eva.core.annotation.trace.Trace;
import com.eva.core.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author Eva.Caesar Liu
 * @date 2021-07-09 11:00
 */
@Trace(exclude = true)
@Api(tags = "资源访问")
@RequestMapping("/resource")
@RestController
public class FileAccessController extends BaseController {

    @ApiOperation("访问图片")
    @GetMapping("/image")
    public void accessImage (@RequestParam(name = "f") String fileKey, HttpServletResponse response) throws IOException{
        ByteArrayOutputStream os = this.getOutputStream(fileKey);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(os.toByteArray());
    }

    @ApiOperation("下载文件")
    @GetMapping("/attach")
    public void downloadFile(@RequestParam(name = "f") String fileKey, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = this.getOutputStream(fileKey);
        String encodeFileName = URLEncoder.encode(fileKey, Charset.forName("UTF-8").toString());
        response.setHeader("Content-Disposition","attachment;filename=" + encodeFileName);
        response.setContentType("application/octet-stream");
        response.setHeader("eva-opera-type", "download");
        response.setHeader("eva-download-filename", encodeFileName);
        response.getOutputStream().write(os.toByteArray());
    }

    /**
     * 获取文件字节流
     *
     * @param fileKey 文件key
     * @return 字节流
     */
    private ByteArrayOutputStream getOutputStream (String fileKey) throws IOException {
        InputStream is = Utils.OSS.download(fileKey);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bs = new byte[is.available()];
        int len;
        while ((len = is.read(bs)) != -1) {
            baos.write(bs, 0, len);
        }
        return baos;
    }
}
