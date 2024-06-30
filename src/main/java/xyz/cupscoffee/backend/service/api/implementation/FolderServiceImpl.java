package xyz.cupscoffee.backend.service.api.implementation;

import org.springframework.stereotype.Service;

import xyz.cupscoffee.files.api.SavStructure;

import xyz.cupscoffee.backend.service.api.interfaces.FolderService;

@Service
public class FolderServiceImpl implements FolderService {
    @Override
    public SavStructure createDefaultSavStructure() {
        throw new UnsupportedOperationException("Unimplemented method 'createDefaultSavStructure'");
    }

    @Override
    public SavStructure syncSavStructure() {
        throw new UnsupportedOperationException("Unimplemented method 'syncSavStructure'");
    }
}
