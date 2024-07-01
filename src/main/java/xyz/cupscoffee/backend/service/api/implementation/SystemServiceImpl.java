package xyz.cupscoffee.backend.service.api.implementation;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.Metadata;
import xyz.cupscoffee.files.api.SavFileReader;
import xyz.cupscoffee.files.api.SavStructure;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import xyz.cupscoffee.backend.service.api.interfaces.SystemService;
import xyz.cupscoffee.backend.util.PathUtil;

@Service
@AllArgsConstructor
public class SystemServiceImpl implements SystemService {
    private final HttpSession session;

    @Override
    public void importSavStructure(InputStream inputStream) throws InvalidFormatFileException {
        SavStructure savStructure = SavFileReader.readSavFile(inputStream);
        session.setAttribute("file", savStructure);
    }

    @Override
    public SavStructure createDefaultSavStructure() throws FileNotFoundException, IOException {
        HashMap<String, String> metadataFile = new HashMap<>();
        metadataFile.put("Author", "Elder");
        metadataFile.put("FileType", "TXT");

        File entrada = new SimpleFile(
                "entrada.txt",
                ByteBuffer.wrap(new byte[0]),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "distribución", "enero", "entrada.txt"),
                metadataFile);
        File lista = new SimpleFile(
                "lista.txt",
                ByteBuffer.wrap(new byte[0]),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "distribución", "enero", "lista.txt"),
                metadataFile);
        File salida = new SimpleFile(
                "salida.txt",
                ByteBuffer.wrap(new byte[0]),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "distribución", "febrero", "salida.txt"),
                metadataFile);
        File recepcion = new SimpleFile(
                "recepción.txt",
                ByteBuffer.wrap(new byte[0]),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "distribución", "febrero", "recepción.txt"),
                metadataFile);

        HashMap<String, String> metadataFolder = new HashMap<>();
        metadataFolder.put("Author", "Elder");
        Folder seguridad = new SimpleFolder(
                "seguridad",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia", "seguridad"),
                metadataFolder);
        Folder informes = new SimpleFolder(
                "informes",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia", "informes"),
                metadataFolder);
        Folder proyectos = new SimpleFolder(
                "proyectos",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia", "proyectos"),
                metadataFolder);
        List<File> files = new LinkedList<>();
        files.add(entrada);
        files.add(lista);
        Folder enero = new SimpleFolder(
                "enero",
                files.subList(0, files.size()),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia", "enero"),
                metadataFolder);
        files = new LinkedList<>();
        files.add(salida);
        files.add(recepcion);
        Folder febrero = new SimpleFolder(
                "febrero",
                files.subList(0, files.size()),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia", "febrero"),
                metadataFolder);
        Folder copia = new SimpleFolder(
                "copia",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "copia"),
                metadataFolder);
        Folder distribucion = new SimpleFolder(
                "distribucion",
                new LinkedList<>(),
                List.of(enero, febrero),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "distribucion"),
                metadataFolder);

        Folder rootDatos = new SimpleFolder(
                "Datos",
                new LinkedList<>(),
                List.of(copia, distribucion),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of(""),
                metadataFolder);

        Disk diskDatos = new SimpleDisk(
                "Datos",
                rootDatos,
                1000000,
                metadataFolder);


        HashMap<String, String> metadataText = new HashMap<>();
        metadataText.put("FileType", "TEXT");
        HashMap<String, String> metaFolder = new HashMap<>();
        metaFolder.put("key1", "value1");
        metaFolder.put("key2", "value2");
        metaFolder.put("key3", "value3");

        // Load golden_sunrise.json and golden_sunrise.jpg from resources
        SimpleFile informe1 = new SimpleFile(
                "informes1.txt",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "informes", "egresos"),
                metadataText
        );

        SimpleFile informe2 = new SimpleFile(
                "informes2.txt",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "informes", "egresos"),
                metadataText
        );

        SimpleFile alpha = new SimpleFile(
                "alpha.txt",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "alfa"),
                metadataText
        );

        SimpleFile beta = new SimpleFile(
                "beta.txt",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "beta"),
                metadataText
        );

        SimpleFile gamma = new SimpleFile(
                "gamma.txt",
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "gamma"),
                metadataText
        );

        SimpleFolder seguridad_info = new SimpleFolder(
                "info",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad", "info"),
                metaFolder);

        SimpleFolder seguridad_reportes = new SimpleFolder(
                "reportes",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad", "reportes"),
                metaFolder);

        SimpleFolder seguridad_codigo_2023 = new SimpleFolder(
                "2023",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad", "codigo", "2023"),
                metaFolder);

        SimpleFolder seguridad_codigo_2024 = new SimpleFolder(
                "2024",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad", "codigo", "2024"),
                metaFolder);

        SimpleFolder seguridad_codigo = new SimpleFolder(
                "info",
                new LinkedList<>(),
                List.of(seguridad_codigo_2023, seguridad_codigo_2024),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad", "codigo"),
                metaFolder);

        SimpleFolder seguridadc = new SimpleFolder(
                "seguridad",
                new LinkedList<>(),
                List.of(seguridad_info, seguridad_reportes, seguridad_codigo),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "seguridad"),
                metaFolder);

        SimpleFolder informes_ingresos = new SimpleFolder(
                "ingresos",
                new LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "informes", "ingresos"),
                metaFolder);

        SimpleFolder informes_egresos = new SimpleFolder(
                "egresos",
                List.of(informe1, informe2),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "informes", "egresos"),
                metaFolder);

        SimpleFolder informesc = new SimpleFolder(
                "egresos",
                new LinkedList<>(),
                List.of(informes_ingresos, informes_egresos),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "informes"),
                metaFolder);

        SimpleFolder alphaFolder = new SimpleFolder(
                "alpha",
                List.of(alpha),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "alpha"),
                metaFolder);

        SimpleFolder betaFolder = new SimpleFolder(
                "beta",
                List.of(beta),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "beta"),
                metaFolder);

        SimpleFolder gammaFolder = new SimpleFolder(
                "alpha",
                List.of(gamma),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos", "gamma"),
                metaFolder);

        SimpleFolder proyectosc = new SimpleFolder(
                "proyectos",
                new LinkedList<>(),
                List.of(alphaFolder, betaFolder, gammaFolder),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("", "proyectos"),
                metaFolder);

        SimpleFolder rootC = new SimpleFolder(
                "",
                new LinkedList<>(),
                List.of(seguridadc, informesc, proyectosc),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of(""),
                metaFolder);

        SimpleDisk diskC = new SimpleDisk(
                "C",
                rootC,
                10000000,
                new HashMap<>()
        );

        Disk[] disks = { diskDatos, diskC };

        HashMap<String, String> metadata = new HashMap<>();
        SimpleSavStructure savStructure = new SimpleSavStructure(
                "CupsOfCoffee",
                disks,
                metadata);

        session.setAttribute("file", savStructure);

        return savStructure;
    }

    @Override
    public SavStructure syncSavStructure() {
        return (SavStructure) session.getAttribute("file");
    }

    @Override
    public byte[] export() throws UnsupportedEncodingException {
        SavStructure savStructure = (SavStructure) session.getAttribute("file");
        StringBuilder sb = new StringBuilder();

        // Get the header
        sb.append(String.format("%-16s", savStructure.getHeader()) + "\n");

        // Write the disks
        Disk[] disks = savStructure.getDisks();
        for (int i = 0; i < disks.length; i++) {
            Disk disk = disks[i];
            sb.append(String.format("%s(%d)[%s]:", disk.getName(), disk.getLimitSize(), disk.getMetadata()));
            loadFolderAsString(disks[i].getRootFolder(), sb);

            sb.append("\n");
        }

        sb.append(savStructure.getMetadata());

        String savStructureContent = sb.toString();

        return savStructureContent.getBytes();
    }

    private void loadFolderAsString(Folder folder, StringBuilder sb) {
        sb.append("|" + folder.getName() + "<");

        // Load folders
        List<Folder> subFolders = folder.getFolders();
        for (Folder subFolder : subFolders) {
            loadFolderAsString(subFolder, sb);
        }

        // Load files
        List<File> files = folder.getFiles();
        for (File file : files) {
            loadFileAsString(file, sb);
        }

        sb.append(">");

        // Load metadata
        loadMetadataAsString(folder, sb);
    }

    private void loadFileAsString(File file, StringBuilder sb) {
        sb.append("*" + file.getName() + "{");

        String content = new String(Base64.getEncoder().encode(file.getContent().duplicate()).array());

        sb.append(content);

        sb.append("}");

        // Load metadata
        loadMetadataAsString(file, sb);
    }

    private void loadMetadataAsString(Metadata metadata, StringBuilder sb) {
        sb.append("[");

        Arrays.asList(Metadata.class.getMethods()).forEach(method -> {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                try {
                    sb.append(method.getName().substring(3) + ":");

                    if (method.getReturnType().equals(LocalDateTime.class)) {
                        LocalDateTime localDateTime = (LocalDateTime) method.invoke(metadata);
                        sb.append(localDateTime.toEpochSecond(ZoneOffset.of("Z")) + ";");
                        return;
                    }

                    sb.append(method.invoke(metadata) + ";");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sb.deleteCharAt(sb.length() - 1);

        sb.append("]");
    }
}
