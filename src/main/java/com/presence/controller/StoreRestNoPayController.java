package com.presence.controller;

import com.presence.model.HRefModel;
import com.presence.service.FileSystemStorageService;
import com.presence.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreRestNoPayController {
    @Autowired
    private FileSystemStorageService storageService;

    @Autowired
    UsersInfoService userInfo;

    //pagina creata pentru a servi utilizatorului fisierele stocare in folderul
    //"fara plata" asociat acestuia, si poate in acelasi timp si incarca fisiere in acel folder
    @GetMapping(value = "/storerestnopay")
    public String storerestnopay(Model model) {
        List<Path> lodf = new ArrayList<>();
        List<HRefModel> uris = new ArrayList<>();
        storageService.setFolder("fara_plata");

        try {
            lodf = storageService.listSourceFiles(storageService.getUploadLocation());
            for(Path pt : lodf) {
                HRefModel href = new HRefModel();
                href.setHref(MvcUriComponentsBuilder
                        .fromMethodName(StoreRestNoPayController.class, "serveFile", pt.getFileName().toString())
                        .build()
                        .toString());

                href.setHrefText(pt.getFileName().toString());
                uris.add(href);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("isman", userInfo.isManager());
        model.addAttribute("listOfEntries", uris);
        return "storerestnopay";
    }

    @GetMapping("/files1/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "/files1/storerestnopay", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/storerestnopay";
    }
}
