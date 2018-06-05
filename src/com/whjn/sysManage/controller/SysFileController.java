package com.whjn.sysManage.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.whjn.common.controller.BaseController;
import com.whjn.common.util.FileUtils;
import com.whjn.common.util.RequestUtils;
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
			if (file.getSize() > 2097152) {
				json.put("msg", requestContext.getMessage("g_fileTooLarge"));
			} else {
				try {
					// 类型Id
					long typeId  = RequestUtils.getLongParameter(request, "typeId");
					// 数据Id
					long objId = RequestUtils.getLongParameter(request, "objId");
					// 业务表
					String objTb = RequestUtils.getStringParameter(request, "objTb");
					// 源文件名称
					String originalFilename = file.getOriginalFilename();
					// 新文件名称
					String fileName = sdf.format(new Date()) + FileUtils.getRandomString(3)
							+ originalFilename.substring(originalFilename.lastIndexOf("."));
					File filePath = new File(getClass().getClassLoader().getResource("/").getPath().replace(
							"/WEB-INF/classes/", "/static/img/upload/" + typeId));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					file.transferTo(new File(filePath.getAbsolutePath() + "\\" + fileName));
					json.put("success", true);
					json.put("data", DateFormatUtils.format(new Date(), "yyyyMM") + "/" + fileName);
					json.put("msg", requestContext.getMessage("g_uploadSuccess"));
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
