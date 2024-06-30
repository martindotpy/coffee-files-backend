package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

public interface SystemService {
    void importSavStructure(InputStream inputStream) throws InvalidFormatFileException;

    SavStructure createDefaultSavStructure() throws FileNotFoundException, IOException;

    SavStructure syncSavStructure();

    byte[] export() throws UnsupportedEncodingException;
}
