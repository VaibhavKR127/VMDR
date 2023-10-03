package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.schema.FileResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
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
	public String updateAsset(@RequestBody Assets asset) {
		if(serimp.getAssetsByCode(asset.getAssetCode())!=null) {
		serimp.insertAssets(asset);
		return "Updated Successfully";
	}
		return "AssestCode not found";
		}
	
	@DeleteMapping("list/{assetCode}")
	public String deleteAsset(@PathVariable("assetCode") String assetCode) {
		if(serimp.getAssetsByCode(assetCode)!=null) {
			serimp.deleteAssets(assetCode);
			return "Deleted Successfully";
		}
			return "AssestCode not found";
	}
	
	@GetMapping("list/{assetCode}")
	public Assets getAssetByCode(@PathVariable("assetCode") String assetCode) {
		if(serimp.getAssetsByCode(assetCode)!=null) {
			return serimp.getAssetsByCode(assetCode);
		}
			return null;
	}
		
	
	
	@PostMapping("upload")
    public String handleFileUpload(@RequestParam("file") List<MultipartFile> exfiles) {
	//public String handleFileUpload() throws IOException {
		//Resource fileResource = exfile.getResource();
//		exfile.get; // Load your file resource here
////		File file = fileResource.getFile();
//		//FileInputStream fis=new FileInputStream(file.); 
		
		
		if(exfiles.size()<0) {
			return "Please select a file to upload.";
		}
		
		else {
		for(MultipartFile exfile: exfiles) {
				
		 int len = exfile.getOriginalFilename().length(); 
		// System.out.println(exfile.getContentType());
		 if (exfile.isEmpty()) {
	            return "Please select a file to upload.";
	        }
		
		 else if(exfile.getOriginalFilename().substring(len-4, len).equalsIgnoreCase("xlsx") || exfile.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ) 
		 {
			// return "File format not supported";
			 
			 try {
					InputStream fis= exfile.getInputStream(); 
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
			        	 int cellCount = row.getLastCellNum();
			        	if(cellCount==8 || cellCount==9) {
						Assets asset = new Assets(); 
						asset.setAssetCode(row.getCell(0).toString()); 
						asset.setAssetName(row.getCell(1).toString());
						asset.setHostName(row.getCell(2).toString());
						asset.setIpAddress(row.getCell(3).toString());
						asset.setAssetType(row.getCell(4).toString());
						asset.setPrimaryAssetOwner(row.getCell(5).toString());
						asset.setSecondaryAssetOwner(row.getCell(6).toString());
						
						if(row.getCell(7).getCellType()==0)
						{
						double excelDate = row.getCell(7).getNumericCellValue();
				        java.util.Date utilDate = DateUtil.getJavaDate(excelDate);
				        
				        // Format the date as yyyy-MM-dd
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        String formattedDate = sdf.format(utilDate);

				        // Create a SQL Date object
				        Date creationDate = Date.valueOf(formattedDate);
				        asset.setCreationDate(creationDate);
						}
						else {
							return "Wrong Date format";
						}
				        serimp.insertAssets(asset);
			        	}
			        	else {
			        		return "Wrong Excel format";
			        	}
			    }
					  
					//return "success";
					
					 }
					 catch (IOException e) {
				            e.printStackTrace();
				            return "Failed to upload the file or read data from it.";
				        }
					 }
		 
		 //System.out.println(exfile.getContentType());
		// System.out.println(exfile.getOriginalFilename().substring(len-4, len));
		 else {
		return "file format not supported  ";
	}
		}
		return "Success";
		}	
	}
	
	
	@GetMapping("export")
	public ResponseEntity<Resource> displayAllComp(@RequestBody List<Assets> assets) throws IOException{
		
		XSSFWorkbook wb=new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet1");
		//XSSFSheet sheet=wb.getSheetAt(0);  
		//FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
		//sheet.createRow(assets.size()+1);
		Row indexrow = sheet.createRow(0);
		Cell cell0 = indexrow.createCell(0);
		cell0.setCellValue("Asset Code");
		
		Cell cell1 = indexrow.createCell(1);
		cell1.setCellValue("Asset Name");
		
		Cell cell2 = indexrow.createCell(2);
		cell2.setCellValue("Host Name");
		
		Cell cell3 = indexrow.createCell(3);
		cell3.setCellValue("IP Address");
		
		Cell cell4 = indexrow.createCell(4);
		cell4.setCellValue("Asset Type");
		
		Cell cell5 = indexrow.createCell(5);
		cell5.setCellValue("Primary Asset Owner");
		
		Cell cell6 = indexrow.createCell(6);
		cell6.setCellValue("Secondary Asset Owner");
		
		Cell cell7 = indexrow.createCell(7);
		cell7.setCellValue("Creation Date");
		
		Cell cell8 = indexrow.createCell(8);
		cell8.setCellValue("Last Modified");
		
		int rowind=1;
		for(Assets asset:assets) {
			Row row = sheet.createRow(rowind);
			Cell dcell0 = row.createCell(0);
			dcell0.setCellValue(asset.getAssetCode());
			
			Cell dcell1 = row.createCell(1);
			dcell1.setCellValue(asset.getAssetName());
			
			Cell dcell2 = row.createCell(2);
			dcell2.setCellValue(asset.getHostName());
			
			Cell dcell3 = row.createCell(3);
			dcell3.setCellValue(asset.getIpAddress());
			
			Cell dcell4 = row.createCell(4);
			dcell4.setCellValue(asset.getAssetType());
			
			Cell dcell5 = row.createCell(5);
			dcell5.setCellValue(asset.getPrimaryAssetOwner());
			
			Cell dcell6 = row.createCell(6);
			dcell6.setCellValue(asset.getSecondaryAssetOwner());
			
			Cell dcell7 = row.createCell(7);
			dcell7.setCellValue(asset.getCreationDate());
			CellStyle cellStyle = wb.createCellStyle();
	        cellStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
	        dcell7.setCellStyle(cellStyle);
		
			Cell dcell8 = row.createCell(8);
			dcell8.setCellValue(asset.getLastModified());
	        dcell8.setCellStyle(cellStyle);
			rowind++;
		}
		
		 try (FileOutputStream outputStream = new FileOutputStream("./src/exportData.xlsx")) {
	            wb.write(outputStream);
	           // System.out.println("Excel file created successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 
		 Resource fileResource =   new FileSystemResource("./src/exportData.xlsx"); // Load your file resource here
			File file = fileResource.getFile();
		       // Set appropriate headers for the response
		       org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		       headers.add(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\rexport.xlsx\"" );
		       
		       // Return the file as a ResponseEntity
		       return ResponseEntity.ok()
		               .headers(headers)
		               .body(fileResource);
		
		
		//return null;
		
	}
}
