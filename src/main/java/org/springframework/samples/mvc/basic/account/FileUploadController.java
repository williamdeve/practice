package org.springframework.samples.mvc.basic.account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController implements ServletContextAware {
	static Set<String> contentType =new HashSet<String>();
	static{
		contentType.add("application/vnd.ms-excel");
		contentType.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}
	private ServletContext servletContext;
	@RequestMapping(method=RequestMethod.GET)
	public String fileUploadForm(WebRequest webRequest, Model model) {
		return "/fileupload";	
	}
	@RequestMapping(method=RequestMethod.POST)
	public void processUpload(@RequestParam MultipartFile file, WebRequest webRequest, Model model) throws IOException {
		String message = "File '" + file.getOriginalFilename() + "' uploaded failed.";
		if(!file.isEmpty()){
			if(!contentType.contains(file.getContentType())){
				message = "File '" + file.getOriginalFilename() + "' doesn't match the file type.";
				model.addAttribute("message", message);
				return;
			}
			 String path = this.servletContext.getRealPath("upload");
			 FileOutputStream fos = new FileOutputStream(path +"\\"+file.getOriginalFilename());
			 fos.write(file.getBytes());
			 fos.close();
			 
			 try {
				Workbook workbook = Workbook.getWorkbook(new File(path +"\\"+file.getOriginalFilename()));
				workbook.getSheet(0);
				workbook.close();
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 message = "File '" + file.getOriginalFilename() + "' uploaded successfully";
		}
		System.out.println(message);
		model.addAttribute("message", message);
	}
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	
}
