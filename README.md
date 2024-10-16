# The-Impossible-Library
This is the multiversion branch (THIS BRANCH IS WIP AND HAS NOT BEEN RELEASED YET)

This mod was made to offer mod devs methods for doing things that are typically thought of as impossible, hacky, tedious, or maybe even require a 3rd party java library.

Of course when doing the impossible it has to be done efficiently, so that is the second focus. If you see something that does not look good, or you have a suggestion/addition to the library, feel free to let me know and/or make a PR!

Once I have a decent amount finished for the library I will be making a wiki to help explain exactly how to use the features present in the library!

Some current features
- File creation and manipulation
- Generic image rendering to the screen
- Global nbt data storage and retrieval
- Support for radial gui elements and some helper methods regarding that
- Custom async tick events able set by millisecond

Some planned features
- RGB shenanigans
- GIF rendering
- MP4 rendering

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
The file ID for the latest version of `0.4.0+universal` is `UNRELEASED`

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
