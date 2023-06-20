package com.azwcl.backup.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * 文件存储 util
 *
 * @author azwcl
 * @since v1.0.0
 */

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class FileUtil {
    @Value("${file.storage.directory}")
    private String directory;

    /**
     * 保存文件
     *
     * @param path     路径
     * @param filename 文件名
     * @param bytes    字节
     */
    public void saveFile(String path, String filename, byte[] bytes) {
        String originalFilename = this.directory + path + filename;
        try {
            File directory = new File(this.directory + path);
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new IOException("create directory failed");
                }
            }
            File file = new File(originalFilename);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("create file failed");
                }
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(originalFilename)) {
                fileOutputStream.write(bytes);
            }

        } catch (IOException e) {
            log.error("file save failed; reason：{}", e.getMessage());
            throw new RuntimeException("文件存储失败");
        }
    }

    /**
     * 读取文件
     *
     * @param filepath 文件路径
     * @return 返回文件内容
     */
    public String readFile(String filepath) {
        filepath = this.directory + filepath;
        try {
            File file = new File(filepath);
            FileReader fileReader = new FileReader(file);

            Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            log.info("failed to decompress the zip file ; reason: {}", e.getMessage());
            return null;
        }
    }
}
