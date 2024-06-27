package xyz.coffee.backend.service.auth.api.interfaces;

import xyz.cupscoffee.files.api.SavFile;

public interface SavFileExporter {
    byte[] export(SavFile savFile);
}
