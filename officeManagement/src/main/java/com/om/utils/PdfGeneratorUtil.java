package com.om.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.om.services.StorageService;

@Component
public class PdfGeneratorUtil {
	
    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private StorageService storageService;
    
    public String createPdf(String templateName, Map<String, Object> map) throws Exception {
        Context ctx = new Context();
        Iterator itMap = map.entrySet().iterator();
        while (itMap.hasNext()) {
            Map.Entry pair = (Map.Entry) itMap.next();
            ctx.setVariable(pair.getKey().toString(), pair.getValue());
        }

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = UUID.randomUUID().toString();
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf");
            
            
//            ServiceUtils.storeFile(new MockMultipartFile("file", outputFile.getName(), "text/plain", new byte[10]), fileName, storageService);
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            InputStream input = new FileInputStream(outputFile);
            Files.copy(input, Paths.get("uploaded-File/").resolve(fileName+".pdf"), StandardCopyOption.REPLACE_EXISTING);

        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
        return fileName + ".pdf";
    }
}
