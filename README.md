# The-Impossible-Library
This is the 1.12.2 branch

This mod was made to offer mod devs methods for doing things that are typically thought of as impossible, hacky, tedious, or maybe even require a 3rd party java library.

Of course when doing the impossible it has to be done efficiently, so that is the second focus. If you see something that does not look good or you have a suggestion/addition to the library, feel free to let me know and/or make a PR!

Some current features
- File creation and manipulation
- Gif rendering

Some WIP/partially implemented features
- MP4 rendering

Some planned features
- RGB shenanigans

CF Project: https://www.curseforge.com/minecraft/mc-mods/the-impossible-library

## How to include this mod as a dependency

You can just use curse maven like so

```
repositories {
    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
  implementation fg.deobf("curse.maven:the-impossible-library-661115:fileID")
}
```
The file ID for the latest version of `0.2` is `3939939`
