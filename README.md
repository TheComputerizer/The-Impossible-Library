# The-Impossible-Library
This is the multiversion branch (THIS BRANCH IS WIP AND HAS NOT BEEN RELEASED YET)

This mod was originally made to offer mod devs methods for doing things that are typically thought of as impossible, 
hacky, tedious, or maybe even require a third party java library. 
It now additionally serves as an API that can be used to _**load and run**_ mods on multiple versions & mod loaders.

## Supported Versions

The status indicators are as follows:
- BROKEN → The API has been implemented in this version, but it is not yet able to load.
- FUNCTIONAL → There are no known major issues specific to this version.
- LOADABLE → The game can load in this version, but entering a world is not yet possible.
- PLANNED → The API has not yet been implemented for this version, but it will be soon.
- PLAYABLE → Worlds are able to load in this version, but there may be stability issues

### Cleanroom
- 1.12.2 → PLANNED

### Fabric/Quilt
- 1.16.5 → FUNCTIONAL
- 1.18.2 → FUNCTIONAL
- 1.19.2 → FUNCTIONAL
- 1.19.4 → FUNCTIONAL
- 1.20.1 → FUNCTIONAL
- 1.20.4 → FUNCTIONAL
- 1.20.6 → FUNCTIONAL
- 1.21.1 → FUNCTIONAL
- 1.21.4 → PLANNED

Other: Registering stuff is not yet possible & a majority of the event wrappers have not yet been implemented.

### Forge
- 1.12.2 → FUNCTIONAL
- 1.16.5 → FUNCTIONAL
- 1.18.2 → FUNCTIONAL
- 1.19.2 → PLAYABLE
- 1.19.4 → FUNCTIONAL
- 1.20.1 → FUNCTIONAL
- 1.20.4 → BROKEN
- 1.20.6 → BROKEN
- 1.21.1 → PLAYABLE
- 1.21.4 → PLANNED

Other: Keybinds do not show up in the keybind menu in 1.19.2+

### NeoForge
- 1.20.1 → FUNCTIONAL
- 1.20.4 → PLAYABLE
- 1.20.6 → FUNCTIONAL
- 1.21.1 → FUNCTIONAL
- 1.21.4 → PLANNED

Other: Keybinds do not show up in the keybind menu

## Getting Started

To get started, all you need is a valid entrypoint class. For a class to be a valid entrypoint class, it must
- extend `mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint`
- be annotated with `@mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod`
- enter a valid modid in the `@MultiVersionMod` annotation
- be added to `TILMultiversionMods` in the manifest of the jar file

When running in a dev environment, you can use the `til.classpath.mods` arg to substitute for the manifest entry.
For example, `-Dtil.classpath.mods=mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint`

To use the coremod section of the API for any early loading stuff you might need to do, there is an additional
entrypoint `mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint`. 
The core entrypoint should be annotated `@MultiVersionCoreMod` rather than `@MultiVersionMod` and similarly should be
added to the manifest as `TILMultiversionCoreMods` or to the `til.classpath.coremods` arg.

A single mod jar is allowed to contain multiple common and core entrypoints. 
Each class name should be separated by `;` in the relevant manifest entry or arg.

## Features

I will be writing a more in-depth wiki at some point, but in the meantime here are a few helpful things you can do:
- Easier file creation and manipulation
- Generic image rendering to the screen
- Global nbt data storage and retrieval
- Support for radial gui elements and some helper methods regarding that
- Custom async tick events able set by millisecond

CF Project: https://www.curseforge.com/minecraft/mc-mods/the-impossible-library

Modrinth Project: https://modrinth.com/mod/the-impossible-library

## How to include this mod as a dependency

You can just use curse maven like so

```
repositories {
    maven {
        url = uri('https://www.cursemaven.com')
        content {
            includeGroup 'curse.maven'
        }
    }
}

dependencies {
  implementation fg.deobf('curse.maven:the-impossible-library-661115:fileID')
}
```
The file ID for the latest version of `0.4.0` is `UNRELEASED`

Alternatively, if you wish to use the modrinth maven, you can do it like this
```

repositories {
    maven {
        name = 'Modrinth'
        url = uri('https://api.modrinth.com/maven')
        content {
            includeGroup 'maven.modrinth'
        }
    }
}

dependencies {
  implementation fg.deobf('maven.modrinth:the-impossible-library:fileVersion-universal')
}
```
The where the latest file version is `UNRELEASED`
