package ru.tim.TgMusicMiniApp.App.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    @Value("${google.jsonPath}")
    private String CREDENTIALS_FILE_PATH;

    @Value("${google.rootFolderId}")
    private String driveRootFolder;

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                        new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("tg-music-mini-app")
                .build();
    }

    public String uploadFile(MultipartFile file, String folderName) throws IOException, GeneralSecurityException {
        Drive drive = getDriveService();

        String folderId = getOrCreateFolder(drive, folderName);

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(folderId));

        java.io.File tempFile = java.io.File.createTempFile("upload-", ".tmp");
        file.transferTo(tempFile);

        try {
            FileContent mediaContent = new FileContent(file.getContentType(), tempFile);

            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            return uploadedFile.getId();
        } finally {
            tempFile.delete();
        }
    }

    private String getOrCreateFolder(Drive drive, String folderName) throws IOException {
        String query = String.format(
                "name='%s' and mimeType='application/vnd.google-apps.folder' " +
                        "and '%s' in parents and trashed=false",
                folderName.replace("'", "\\'"),
                driveRootFolder
        );

        FileList result = drive.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id)")
                .execute();

        if (!result.getFiles().isEmpty()) {
            return result.getFiles().get(0).getId();
        }

        File folderMetadata = new File();
        folderMetadata.setName(folderName);
        folderMetadata.setMimeType("application/vnd.google-apps.folder");
        folderMetadata.setParents(Collections.singletonList(driveRootFolder));

        File folder = drive.files().create(folderMetadata)
                .setFields("id")
                .execute();

        return folder.getId();
    }

    public byte[] downloadFile(String fileId) throws IOException, GeneralSecurityException {
        Drive drive = getDriveService();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        drive.files().get(fileId)
                .executeMediaAndDownloadTo(os);
        return os.toByteArray();
    }

    public void deleteFile(String fileId) throws IOException, GeneralSecurityException {
        Drive drive = getDriveService();

        try {
            drive.files().delete(fileId).execute();
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 404) {
                throw new IllegalArgumentException("File not found with ID: " + fileId);
            }
            throw e;
        }
    }

}
