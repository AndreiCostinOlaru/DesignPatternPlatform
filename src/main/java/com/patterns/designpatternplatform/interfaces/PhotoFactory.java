package com.patterns.designpatternplatform.interfaces;

import com.patterns.designpatternplatform.entities.DiscussionPhoto;
import com.patterns.designpatternplatform.entities.PatternPhoto;

public interface PhotoFactory {
    PatternPhoto createPatternPhoto(String filename, byte[] filecontent, String filetype);//creates a pattern photo object
    DiscussionPhoto createDiscussionPhoto(String filename, byte[] filecontent, String filetype);//creates a discussion photo object
}
