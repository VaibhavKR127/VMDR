package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Assets;
import com.example.demo.service.Service_Implementations;



@RestController
@RequestMapping("assets")
public class AssetsController {

	
private Service_Implementations serimp;
	
	@Autowired
	public AssetsController(Service_Implementations serimp) {
		this.serimp = serimp;
	}
	
	
	@GetMapping("/list")
	public List<Assets> displayAllComp(){
		List<Assets> compList = serimp.DisplayAllAssets();
		return compList;
	}
	
	@PostMapping("list")
	public void addAsset(@RequestBody Assets asset) {
		serimp.insertAssets(asset);
	}
	
	@PutMapping("list")
	public void updateAsset(@RequestBody Assets asset) {
		serimp.insertAssets(asset);
	}
	
	@DeleteMapping("list/{assetCode}")
	public void deleteAsset(@PathVariable("assetCode") String assetCode) {
		serimp.deleteAssets(assetCode);
	}
	
	@GetMapping("list/{assetCode}")
	public Assets getAssetByCode(@PathVariable("assetCode") String assetCode) {
		return serimp.getAssetsByCode(assetCode);
	}
	
	@PostMapping("upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile exfile) throws IOException {
	//public String handleFileUpload() throws IOException {
		Resource fileResource =   exfile.getResource();; // Load your file resource here
		File file = fileResource.getFile();
		FileInputStream fis=new FileInputStream(file);  
		//creating workbook instance that refers to .xls file  
		XSSFWorkbook wb=new XSSFWorkbook(fis);   
		//creating a Sheet object to retrieve the object  
		XSSFSheet sheet=wb.getSheetAt(0);  
		FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
		
		Iterator<Row> rowIterator = sheet.iterator();
		int count=0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            count++;
        }
		
        for(int i=1;i<count;i++)
		//for(Row row: sheet)     //iteration over row using for each loop  
		{  
        	Row row = sheet.getRow(i);
			Assets asset = new Assets(); 
			asset.setAssetCode(row.getCell(0).toString()); 
			asset.setAssetName(row.getCell(1).toString());
			asset.setHostName(row.getCell(2).toString());
			asset.setIpAddress(row.getCell(3).toString());
			asset.setAssetType(row.getCell(4).toString());
			asset.setPrimaryAssetOwner(row.getCell(5).toString());
			asset.setSecondaryAssetOwner(row.getCell(6).toString());
				
			double excelDate = row.getCell(7).getNumericCellValue();
	        java.util.Date utilDate = DateUtil.getJavaDate(excelDate);

	        // Format the date as yyyy-MM-dd
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = sdf.format(utilDate);

	        // Create a SQL Date object
	        Date creationDate = Date.valueOf(formattedDate);
	        asset.setCreationDate(creationDate);
	        serimp.insertAssets(asset);

    }
        
		return "redirect:/success";
}
}
