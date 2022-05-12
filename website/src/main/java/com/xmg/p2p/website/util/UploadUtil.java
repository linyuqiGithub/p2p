package com.xmg.p2p.website.util;

import com.jcraft.jsch.*;
import com.xmg.p2p.base.util.Consts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传工具
 * 
 * @author Administrator
 * 
 */
public class UploadUtil {

	private static final String REMOTE_HOST = "192.168.2.73";  //远程主机ip
	private static final String USERNAME = "administrator";  //登录用户名
	private static final String PASSWORD = "";  //登陆密码
	private static final int REMOTE_PORT = 22;   //ssh协议默认端口
	private static final int SESSION_TIMEOUT = 10000; //session超时时间
	private static final int CHANNEL_TIMEOUT = 5000; //管道流超时时间


	/**
	 * 处理文件上传
	 * 
	 * @param file
	 *            存放文件的目录的绝对路径 servletContext.getRealPath("/upload")
	 * @return
	 */
	public static String upload(MultipartFile file,String basePath) {
		String orgFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(orgFileName);
		try {
			File targetFile = new File(basePath, fileName);
			FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
			//把文件同步到公共文件夹中
			File publicFile = new File(Consts.PUBLIC_IMG_SHARE_PATH,fileName);
			FileUtils.writeByteArrayToFile(publicFile,file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 远程文件上传
	 */
	public static String remote(MultipartFile file,String basePath){

		String orgFileName = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(orgFileName);

		JSch jsch = new JSch();
		Session jschSession = null;
		try {

			File targetFile = new File(basePath, fileName);
			FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
			jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
			// 通过密码的方式登录认证
			jschSession.setPassword(PASSWORD);
			jschSession.setConfig("StrictHostKeyChecking", "no");
			jschSession.connect(SESSION_TIMEOUT);
			Channel sftp = jschSession.openChannel("sftp");  //建立sftp文件传输管道
			sftp.connect(CHANNEL_TIMEOUT);
			ChannelSftp channelSftp = (ChannelSftp) sftp;
			// 传输本地文件到远程主机
			channelSftp.put(Consts.LOCAL_IMG_STORE_PATH+fileName,Consts.PUBLIC_IMG_SHARE_PATH);

			channelSftp.exit();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		System.out.println("传输文件完成");
		return fileName;
	}
}
























