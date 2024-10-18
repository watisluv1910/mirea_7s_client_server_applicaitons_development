package com.wladischlau.app.pract5.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.FileExistsException;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@Controller
public class FileUploadController {

    private final GridFsOperations fsOperations;

    public FileUploadController(GridFsOperations fsOperations) {
        this.fsOperations = fsOperations;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        List<String> files = fsOperations.find(new Query()).map(GridFSFile::getFilename).into(new ArrayList<>());
        model.addAttribute("files", files);
        return "index";
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) throws FileNotFoundException {
        String decodedFilename = UriUtils.decode(filename, StandardCharsets.UTF_8);
        GridFSFile file = fsOperations.findOne(query(whereFilename().is(decodedFilename)));

        if (file == null) {
            throw new FileNotFoundException(decodedFilename);
        }

        return ResponseEntity.ok()
                .contentLength(file.getLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(fsOperations.getResource(file));
    }

    @DeleteMapping("/{filename:.+}")
    public String deleteFile(@PathVariable("filename") String filename, RedirectAttributes redirectAttributes) {
        String decodedFilename = UriUtils.decode(filename, StandardCharsets.UTF_8);
        fsOperations.delete(query(whereFilename().is(decodedFilename)));

        redirectAttributes.addFlashAttribute("message", "You successfully deleted " + decodedFilename + "!");
        return "redirect:/";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Invalid file");
            throw new BadRequestException("Invalid file");
        }

        if (fsOperations.findOne(query(whereFilename().is(originalFilename))) != null) {
            redirectAttributes.addFlashAttribute("message", "File already exists");
            throw new FileExistsException("File " + originalFilename + " already exists");
        }

        fsOperations.store(file.getInputStream(), originalFilename);

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + originalFilename + "!");
        return "redirect:/";
    }
}
