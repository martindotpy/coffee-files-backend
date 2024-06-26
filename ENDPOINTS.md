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

```
// Import
POST /system/import

body {
  file: MultipartFile,
}

200 response {
  token: string,
  client_uuid: string,
}
```

- [ ] completed

```
// Create
POST /system/create

200 response {
  token: string,
  client_uuid: string,
}
```

- [ ] completed

```
// Status
GET /system/status

200 response {
  state: READY | IMPORTING,
}

401 Unauthorized
```

- [ ] completed

```
// Sync/Resume
GET /system/sync

200 response {
  system: Sav,
  client_uuid: string,
}

401 Unauthorized
```

- [ ] completed

```
// Logout
POST /system/logout

200 response

401 Unauthorized
```

### Files

- [ ] completed

```
// Upload file
POST /files/upload

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

```
// Create file
POST /files/create

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

```
// Read content file
POST /files/read

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

```
// Edit file
POST /files/edit

body {
  path: Path:"A:/dir1/app.json",
  content: string,
  type: FileType,
}

200 response File

401 Unauthorized

404 Not found
```

- [ ] completed

```
// Delete file
POST /files/delete

body {
  path: Path:"A:/dir1/app.txt",
}

200 response

401 Unauthorized

404 Not found
```

- [ ] completed

[How download](https://github.com/martindotpy/proyecto_algoritmos_estrucutras_de_datos/blob/main/app/src/main/java/pe/edu/utp/springboot/ApiController.java#L183)

```
// Download file
GET /files/download

body {
  path: Path:"A:/dir1/app.json",
}

200 response byte[]

401 Unauthorized

404 Not found
```

- [ ] completed

```
// File move
POST /files/move

body {
  from: Path:"A:/dir1/app.json",
  to: Path:"B:/dir2/app.json",
}

200 response File

401 Unauthorized

404 Not found
```

### Folders

```
// Create folder
```

```
// Rename folder
```

```
// Move folder
```

```
// Delete folder
```

### Disk

```
// Create disk. Default folder structure: true/false
```

```
// Rename disk.
```

```
// Delete disk
```

```
// Export disk. Export only this disk if possible
```
