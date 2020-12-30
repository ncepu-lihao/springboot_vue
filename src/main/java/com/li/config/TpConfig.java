package com.li.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 自定义属性
 */
@Component
@ConfigurationProperties(prefix = "tp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TpConfig {
    /**
     * 网站的文件路径
     */
    private String fileDir = "";


    /**
     * jwt token过期时间
     */
    private Long jwtExpired = 30 * 24 * 60L;//默认30天

    /**
     * devMode
     * @return
     */
    /**
     * 开发模式
     */
    private Boolean devMode = false;



//    public Long getJwtExpired() {
//            return jwtExpired;
//        }
//
//        public void setJwtExpired(Long jwtExpired) {
//            this.jwtExpired = jwtExpired;
//        }
//
//
//        public String getFileDir() {
//            return fileDir;
//        }
//
//        public void setFileDir(String fileDir) {
//            this.fileDir = fileDir;
//        }


}

