package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavStructure;

public interface SystemService {
    void importSavStructure(InputStream inputStream);

    SavStructure createDefaultSavStructure();

    SavStructure syncSavStructure();

    byte[] export(SavStructure savStructure) throws UnsupportedEncodingException;
}
