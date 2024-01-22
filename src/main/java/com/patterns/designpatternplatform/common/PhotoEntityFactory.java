package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.entities.DiscussionPhoto;
import com.patterns.designpatternplatform.entities.PatternPhoto;
import com.patterns.designpatternplatform.interfaces.PhotoFactory;

public class PhotoEntityFactory implements PhotoFactory {
    @Override
    public PatternPhoto createPatternPhoto(String filename, byte[] filecontent, String filetype) { //sets the parameters for a pattern photo based on input and return the photo
        PatternPhoto photo = new PatternPhoto();
        photo.setFilename(filename);
        photo.setFileContent(filecontent);
        photo.setFileType(filetype);
        return photo;
    }

    @Override
    public DiscussionPhoto createDiscussionPhoto(String filename, byte[] filecontent, String filetype) {//sets the parameters for a discussion photo based on input and returns the photo
        DiscussionPhoto photo = new DiscussionPhoto();
        photo.setFilename(filename);
        photo.setFileContent(filecontent);
        photo.setFileType(filetype);
        return photo;
    }
}
