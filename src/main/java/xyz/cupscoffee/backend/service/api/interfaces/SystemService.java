package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavStructure;

public interface SystemService {
    byte[] export(SavStructure savStructure) throws UnsupportedEncodingException;

    SavStructure createDefaultSavStructure();

    SavStructure syncSavStructure();
}
