package com.whjn.sysManage.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.whjn.common.controller.BaseController;
import com.whjn.common.util.RequestUtils;
import com.whjn.sysManage.service.SysFileService;



@Controller
@RequestMapping("/sys/file")
public class SysFileController extends BaseController{

	@Resource
	private SysFileService sysFileService;

	@RequestMapping("/upLoad")
	public void upLoad(@RequestParam(value = "picFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//数据Id
		Integer objId = RequestUtils.getIntParameter(request, "objId");
		//业务表
		String objTb = RequestUtils.getStringParameter(request, "objTb");
		//更新或插入标识
		String instOrUd = RequestUtils.getStringParameter(request, "instOrUd");
		//文件路径
		String filePath = RequestUtils.getStringParameter(request, "filePath");
        //获取文件的路径
		String originalFilename = file.getOriginalFilename();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "ok");
		writeJSON(response, result);
	}
}
