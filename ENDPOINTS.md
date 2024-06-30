# Endpoints

## Objects

FileType

```ts
enum FileType {
  TXT,
  JPG,
  JSON,
}
```

Path: String

```txt
A:/dir1/dir2/file.txt
B:/config.json
```

File

```ts
type File {
  name: string;
  content: string;
  created: number;
  last_modified: number;
  size: number;
  type: TypeFile;
  path: Path;
}
```

Folder

```ts
type Folder {
  name: string;
  files: File[];
  folders: Folder[];
  created: number;
  last_modified: number;
  size: number;
  path: Path;
  is_root: boolean;
}
```

Disk

```ts
type Disk {
  name: string;
  root: Folder;
  limit_size: number;
  occupied_size: number;
}
```

Sav

```ts
type Sav {
  settings: {
    theme: Path;
  };
  disks: Disk[];
}
```

## Endpoints

### System

- [ ] completed

```http
// Import
POST /api/system/import

body {
  file: MultipartFile,
}
```

- [ ] completed

```http
// Create
POST /api/system/create
```

- [ ] completed

```http
// Status
GET /api/system/status

200 response {
  state: READY | IMPORTING,
}

401 Unauthorized
```

- [ ] completed

```http
// Sync/Resume
GET /api/system/sync

200 response {
  system: Sav
}

401 Unauthorized
```

- [ ] completed

```http
// Logout
POST /api/system/logout

200 response

401 Unauthorized
```

### Files

- [ ] completed

```http
// Upload file
POST /api/files/upload

body {
  path: Path:"A:/dir1/app.txt",
  file: MultipartFile,
  type: FileType,
}

200 response File

401 Unauthorized

404 Not found
```

- [ ] completed

```http
// Create file
POST /api/files/create

body {
  path: Path:"A:/dir1/app.txt",
  name: string;
  content: string;
  type: TypeFile;
}

200 response File

401 Unauthorized

404 Not found
```

- [ ] completed

```http
// Read content file
POST /api/files/read

body {
  path: Path:"A:/dir1/app.txt",
}

200 response {
  content: string
}

401 Unauthorized

404 Not found
```

- [ ] completed

```http
// Edit file
POST /api/files/edit

body {
  path: Path:"A:/dir1/app.json",
  content: string,
  type: FileType
}

200 response File

401 Unauthorized

404 Not found
```

- [ ] completed

```http
// Delete file
POST /api/files/delete

body {
  path: Path:"A:/dir1/app.txt",
}

200 response

401 Unauthorized

404 Not found
```

- [ ] completed

[How download](https://github.com/martindotpy/proyecto_algoritmos_estrucutras_de_datos/blob/main/app/src/main/java/pe/edu/utp/springboot/ApiController.java#L183)

```http
// Download file
GET /api/files/download

body {
  path: Path:"A:/dir1/app.json",
}

200 response byte[]

401 Unauthorized

404 Not found
```

- [ ] completed

```http
// File move
POST /api/files/move

body {
  from: Path:"A:/dir1/app.json",
  to: Path:"B:/dir2/app.json",
}

200 response File

401 Unauthorized

404 Not found
```

### Folders

```http
// Create folder
```

```http
// Rename folder
```

```http
// Move folder
```

```http
// Delete folder
```

### Disk

```http
// Create disk. Default folder structure: true/false
```

```http
// Rename disk.
```

```http
// Delete disk
```

```http
// Export disk. Export only this disk if possible
```
