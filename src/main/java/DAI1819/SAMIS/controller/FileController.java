package DAI1819.SAMIS.controller;

import DAI1819.SAMIS.entity.DBFile;
import DAI1819.SAMIS.payload.UploadFileResponse;
import DAI1819.SAMIS.service.DBFileStorageService;
import DAI1819.SAMIS.repository.DBFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    EntityManager em;
    @Autowired
    private DBFileStorageService DBFileStorageService;
    @Autowired
    private DBFileRepository dbFileRepository;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = DBFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(Integer.toString(dbFile.getId()))
                .toUriString();
        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/api/files")
    public List<DBFile> getAllFiles() {
        return dbFileRepository.findAll();
    }
    
    @GetMapping("/api/topfile")
    public DBFile getTopFiles() {
        return dbFileRepository.findTopByOrderByIdAsc();
    }
    @GetMapping("/api/files/{id}")
    public ResponseEntity<DBFile> getFileById(@PathVariable(value = "id") String fileId) {
        DBFile dbfile = em.find(DBFile.class, fileId);
        return ResponseEntity.ok().body(dbfile);
    }
    
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = DBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
