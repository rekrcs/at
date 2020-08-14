package com.sbs.byk.at.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sbs.byk.at.Util.Util;
import com.sbs.byk.at.dto.File;
import com.sbs.byk.at.dto.ResultData;
import com.sbs.byk.at.service.FileService;
import com.sbs.byk.at.service.VideoStreamService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	@Autowired
	private VideoStreamService videoStreamService;

	private LoadingCache<Integer, File> fileCache = CacheBuilder.newBuilder().maximumSize(100)
			.expireAfterAccess(2, TimeUnit.MINUTES).build(new CacheLoader<Integer, File>() {
				@Override
				public File load(Integer fileId) {
					return fileService.getFileById(fileId);
				}
			});

	@RequestMapping("/usr/file/streamVideo")
	public ResponseEntity<byte[]> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
			int id) {
		File file = Util.getCacheData(fileCache, id);

		return videoStreamService.prepareContent(new ByteArrayInputStream(file.getBody()), file.getFileSize(),
				file.getFileExt(), httpRangeList);
	}

	@RequestMapping("/usr/file/doUploadAjax")
	@ResponseBody
	public ResultData uploadAjax(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest) {

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		List<Integer> fileIds = new ArrayList<>();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			String[] fileInputNameBits = fileInputName.split("__");

			if (fileInputNameBits[0].equals("file")) {
				byte[] fileBytes = Util.getFileBytesFromMultipartFile(multipartFile);

				if (fileBytes == null || fileBytes.length == 0) {
					continue;
				}

				String relTypeCode = fileInputNameBits[1];
				int relId = Integer.parseInt(fileInputNameBits[2]);
				String typeCode = fileInputNameBits[3];
				String type2Code = fileInputNameBits[4];
				int fileNo = Integer.parseInt(fileInputNameBits[5]);
				String originFileName = multipartFile.getOriginalFilename();
				String fileExtTypeCode = Util.getFileExtTypeCodeFromFileName(multipartFile.getOriginalFilename());
				String fileExtType2Code = Util.getFileExtType2CodeFromFileName(multipartFile.getOriginalFilename());
				String fileExt = Util.getFileExtFromFileName(multipartFile.getOriginalFilename()).toLowerCase();
				int fileSize = (int) multipartFile.getSize();

				int oldFileId = fileService.getFileId(relTypeCode, relId, typeCode, type2Code, fileNo);

				boolean needToUpdate = oldFileId > 0;

				if (needToUpdate) {
					fileService.updateFile(oldFileId, originFileName, fileExtTypeCode, fileExtType2Code, fileExt,
							fileBytes, fileSize);

					fileCache.refresh(oldFileId);

				} else {
					int fileId = fileService.saveFile(relTypeCode, relId, typeCode, type2Code, fileNo, originFileName,
							fileExtTypeCode, fileExtType2Code, fileExt, fileBytes, fileSize);

					fileIds.add(fileId);
				}
			}
		}

		int deleteCount = 0;

		for (String inputName : param.keySet()) {
			String[] inputNameBits = inputName.split("__");

			if (inputNameBits[0].equals("fileDelete")) {
				String relTypeCode = inputNameBits[1];
				int relId = Integer.parseInt(inputNameBits[2]);
				String typeCode = inputNameBits[3];
				String type2Code = inputNameBits[4];
				int fileNo = Integer.parseInt(inputNameBits[5]);

				int oldFileId = fileService.getFileId(relTypeCode, relId, typeCode, type2Code, fileNo);

				boolean needToDelete = oldFileId > 0;

				if (needToDelete) {
					fileService.deleteFile(oldFileId);
					fileCache.refresh(oldFileId);
					deleteCount++;
				}
			}
		}

		Map<String, Object> rsDataBody = new HashMap<>();
		rsDataBody.put("fileIdsStr", Joiner.on(",").join(fileIds));
		rsDataBody.put("fileIds", fileIds);

		return new ResultData("S-1", String.format("%d개의 파일을 저장했습니다. %d개의 파일을 삭제했습니다.", fileIds.size(), deleteCount),
				rsDataBody);
	}
}