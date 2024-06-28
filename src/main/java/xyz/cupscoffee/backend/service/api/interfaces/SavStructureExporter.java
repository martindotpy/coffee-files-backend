package xyz.cupscoffee.backend.service.api.interfaces;

import java.io.UnsupportedEncodingException;

import xyz.cupscoffee.files.api.SavStructure;

public interface SavStructureExporter {
    byte[] toBytes(SavStructure savStructure) throws UnsupportedEncodingException;
}
