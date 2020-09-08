package org.example.chat.client.message;

public abstract class AbstractMessageFile extends Message {

    protected String filename;
    protected String extension;

    public void setFilename(String filename) {
        this.filename = FileName.getNameWithoutExtension(filename);
        this.extension = FileName.getFileExtension(filename);
    }

    public String getFilename() {
        return filename;
    }

    public String getExtension() {
        return extension;
    }
}
