package com.whjn.sysManage.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.whjn.common.controller.BaseController;
import com.whjn.common.framework.web.WebUtil;
import com.whjn.common.util.FileUtils;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.model.po.SysFile;
import com.whjn.sysManage.model.po.SysUser;
import com.whjn.sysManage.service.SysFileService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sys/file")
public class SysFileController extends BaseController {

	@Resource
	private SysFileService sysFileService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestMapping(value = "/uploadAttachement", method = RequestMethod.POST)
	public void uploadAttachement(@RequestParam(value = "uploadAttachment", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		JSONObject json = new JSONObject();
		if (!file.isEmpty()) {
			if (file.getSize() > 2097152) {// 返回字节数 1M=1024k=1048576字节
				json.put("msg", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					// 文件ID
					long fileId = RequestUtils.getLongParameter(request, "fileId");
					// 数据Id
					long objId = RequestUtils.getLongParameter(request, "objId");
					// 业务表
					String objTb = RequestUtils.getStringParameter(request, "objTb");
					// 源文件名称
					String oldFilename = file.getOriginalFilename();
					// 文件类型
					String fileType = oldFilename.substring(oldFilename.lastIndexOf(".") + 1, oldFilename.length())
							.toLowerCase();
					// 文件目录
					String fileList = FileUtils.getFileList(fileType);
					// 文件大小
					long fileSize = file.getSize();
					// 当前用乎
					SysUser user = WebUtil.getCurrUserInfo(request);
					// 新文件名称
					String fileName = sdf.format(new Date()) + FileUtils.getRandomString(3) + "." + fileType;
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath()
							.replace("/WEB-INF/classes/", "/upload/" + fileList));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					json.put("success", true);
					json.put("data", "/upload/" + fileList + "/" + fileName);
					json.put("msg", requestContext.getMessage("g_uploadSuccess"));
					// 数据操作方式
					String saveType = RequestUtils.getStringParameter(request, "updateOrInsert");
					if (saveType.equals("update")) {
						// file.delete()
						// 原始文件数据记录
						SysFile oldFileDate = sysFileService.getByProerties("id", fileId);

						// 获取原始物理文件
						File oldFile = new File(filePath.getAbsolutePath() + "\\" + oldFileDate.getFileName());
						if (oldFile.exists() && oldFile.isFile()) {
							oldFile.delete();
						}
						SysFile uploadFile = new SysFile(fileId, oldFilename, fileName, objId, objTb, fileType,
								fileSize, user, new Date());
						// 更新数据
						sysFileService.merge(uploadFile);
					}
					if (saveType.equals("insert")) {
						SysFile uploadFile = new SysFile(oldFilename, fileName, objId, objTb, fileType, fileSize, user,
								new Date());
						// 插入数据
						sysFileService.persist(uploadFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
					json.put("msg", requestContext.getMessage("g_uploadFailure"));
				}
			}
		} else {
			json.put("msg", requestContext.getMessage("g_uploadNotExists"));
		}
		writeJSON(response, json.toString());
	}

}
