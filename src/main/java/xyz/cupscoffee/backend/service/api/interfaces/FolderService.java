package xyz.cupscoffee.backend.service.api.interfaces;

import xyz.cupscoffee.files.api.SavStructure;

public interface FolderService {
    SavStructure createDefaultSavStructure();

    SavStructure syncSavStructure();
}
