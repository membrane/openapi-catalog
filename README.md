# OpenAPI Catalog

The Membrane OpenAPI Catalog manages OpenAPI specifications.  
Features:

- Automatic updates
- Linting
- Diff checking for breaking changes

## Requirements

- Linux, macOS or Windows
- x86-64
- Java 21
- Internet connection

## Installation

1. [Download the latest release](https://github.com/membrane/openapi-catalog/releases/latest)
2. Execute the OpenAPI-Catalog with `java -jar openapi-catalog-{version}.jar`
3. After you see `Completed initialization` in the terminal the application is ready.
4. A browser window should open automatically. If not, [open the service in the browser](http://localhost:8000).

## Configuration

You can configurate the application by opening its file with an archive manager and edit specific files.

### Database

- file: /BOOT-INF/classes/application.yml
    - values:
        - datasource.username
- file: /BOOT-INF/classes/application-prod.yml
    - values:
        - datasource.password
        - datasource.url

### Port

- file: /BOOT-INF/classes/application-prod.yml
    - values:
        - server.port

### Used tools

If the organization or repository names of the used tools change on GitHub, these values can be updated with the following parameters:

- file: /BOOT-INF/classes/application.yml
    - values:
        - tools.diff.github.name  
          _repository name_
        - tools.diff.github.organization
          _organization name_
        - tools.lint.github.name
          _repository name_
        - tools.lint.github.organization
          _organization name_

## Automatic setup

On execution, the software will do the following tasks:

1. Installation of the tools:
    - If the tool doesn't exist or is older than the GitHub version, it will be downloaded.
    - If no internet is available and tools are already downloaded, the software will use them.
    - The location of the downloaded tools will be at `/home/{$HOME}/.predic8/openapicatalog/tools/`.
    - diff tool
        - filename: `openapi-changes`
        - version file: `.openapi-changes`
    - linting tool
        - filename: `spectral`
        - version file: `.spectral`
2. Installation of the `hsqldb` database:
    - The database will be created at `/home/{$HOME}/.predic8/openapicatalog/database/`
3. Update specifications which should be updated automatically.
