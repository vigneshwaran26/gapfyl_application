package com.gapfyl.builders;

import java.util.UUID;

/**
 * @author vignesh
 * Created on 24/04/21
 **/

public class URLBuilder {

    private static String IMAGES_FOLDER         = "images";
    private static String VIDEOS_FOLDER         = "videos";
    private static String ASSETS_FOLDER         = "assets";
    private static String PREVIEWS_FOLDER       = "previews";
    private static String FILE_UPLOADS_FOLDER   = "uploads";
    private static String DOWNLOADS_FOLDER      = "downloads";

    private BuilderType builderType;
    private String accountId;
    private String imageId;
    private String videoId;
    private String assetId;
    private String previewId;
    private String uniqueId;

    private URLBuilder(BuilderType builderType) {
        this.builderType = builderType;
    }

    public static URLBuilder image() {
        return new URLBuilder(BuilderType.IMAGE);
    }

    public static URLBuilder video() {
        return new URLBuilder(BuilderType.VIDEO);
    }

    public static URLBuilder asset() {
        return new URLBuilder(BuilderType.ASSET);
    }

    public static URLBuilder preview() {
        return new URLBuilder(BuilderType.PREVIEW);
    }

    public static URLBuilder upload() {
        return new URLBuilder(BuilderType.FILE_UPLOAD);
    }

    public static URLBuilder download() {
        return new URLBuilder(BuilderType.DOWNLOAD);
    }

    public URLBuilder withAccount(String id) {
        this.accountId = id;
        return this;
    }

    public URLBuilder withImage(String id) {
        this.imageId = id;
        return this;
    }

    public URLBuilder withVideo(String id) {
        this.videoId = id;
        return this;
    }

    public URLBuilder withAsset(String id) {
        this.assetId = id;
        return this;
    }

    public URLBuilder withPreview(String id) {
        this.previewId = id;
        return this;
    }

    public URLBuilder withId(String id) {
        this.uniqueId = id;
        return this;
    }

    public URLBuilder withUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
        return this;
    }

    public String build() {
        switch (this.builderType) {
            case IMAGE:
                return String.format("%s/%s/%s/%s", accountId, IMAGES_FOLDER, imageId, uniqueId);
            case VIDEO:
                return String.format("%s/%s/%s/%s", accountId, VIDEOS_FOLDER, videoId, uniqueId);
            case ASSET:
                return String.format("%s/%s/%s", accountId, ASSETS_FOLDER, uniqueId);
            case PREVIEW:
                return String.format("%s/%s/%s", accountId, PREVIEWS_FOLDER, uniqueId);
            case FILE_UPLOAD:
                return String.format("%s/%s/%s", accountId, FILE_UPLOADS_FOLDER, uniqueId);
            case DOWNLOAD:
                return String.format("%s/%s/%s", accountId, DOWNLOADS_FOLDER, uniqueId);
        }

        throw new UnsupportedOperationException();
    }
}
