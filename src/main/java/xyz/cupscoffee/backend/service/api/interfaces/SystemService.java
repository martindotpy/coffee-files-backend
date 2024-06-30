package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

public interface SystemService {
    void importSavStructure(InputStream inputStream) throws InvalidFormatFileException;

    SavStructure createDefaultSavStructure();

    SavStructure syncSavStructure();

    byte[] export(SavStructure savStructure) throws UnsupportedEncodingException;
}
