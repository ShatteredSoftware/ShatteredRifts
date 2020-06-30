<p align="center"><img src="https://raw.githubusercontent.com/ShatteredSuite/ShatteredRifts/master/header.png" alt=""/></p>

-----
<p align="center">
<a href="https://github.com/ShatteredSuite/ShatteredRifts/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/ShatteredSuite/ShatteredRifts?style=for-the-badge&logo=github" /></a>
<a href="https://github.com/ShatteredSuite/ShatteredRifts/issues"><img alt="GitHub Issues" src="https://img.shields.io/github/issues/ShatteredSuite/ShatteredRifts?style=for-the-badge&logo=github" /></a>
<a href="https://github.com/ShatteredSuite/ShatteredRifts/releases"><img alt="GitHub Version" src="https://img.shields.io/github/release/ShatteredSuite/ShatteredRifts?label=Github%20Version&style=for-the-badge&logo=github" /></a>
<a href="https://discord.gg/zUbNX9t"><img alt="Discord" src="https://img.shields.io/badge/Get%20Help-On%20Discord-%237289DA?style=for-the-badge&logo=discord" /></a>
<a href="ko-fi.com/uberpilot"><img alt="Ko-Fi" src="https://img.shields.io/badge/Support-on%20Ko--fi-%23F16061?style=for-the-badge&logo=ko-fi" /></a>
</p>

## About

ShatteredRifts is a plugin that can be used to create timed portals around the world, each going to 
specific locations. These teleport all entities inside of them, making them useful for getting pets 
around or for creating interesting RPG locations to explore.

## For Server Owners

Looking for a development release? You can get it 
[here](https://github.com/ShatteredSuite/ShatteredRifts/releases/tag/latest). Looking for the latest
full version? You can get it [here](https://github.com/ShatteredSuite/ShatteredRifts/releases/latest).
Either way, the file ending in `-dist.jar` is what you want.

## For Developers

### Building

**Mac/Linux:**
```
git clone https://github.com/ShatteredSuite/ShatteredRifts.git
cd ShatteredRifts
./gradlew publishToMavenLocal
```

**Windows:**
```
git clone https://github.com/ShatteredSuite/ShatteredRifts.git
cd ShatteredRifts
gradlew.bat publishToMavenLocal
```

In either case, the jars will be produced in `build/libs`. 

### Installing

#### Maven
Add the following to your `pom.xml`:
```xml
<repositories> 
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.ShatteredSuite</groupId>
        <artifactId>ShatteredRifts</artifactId>
        <version>Tag</version>
    </dependency>
</dependencies>
```

Next, add a dependency in your `plugin.yml`:
```yaml
depend:
- ShatteredRifts
```

Finally, use any of the features you like!


#### Gradle
Add the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ShatteredSuite:ShatteredRifts:Tag'
}
```

Next, add a dependency in your `plugin.yml`:
```yaml
depend:
- ShatteredRifts
```

Finally, use any of the features you like!

### API

#### Events

`RiftTeleportPlayerEvent` is called when a player is telepored from a rift. `RiftTeleportEntityEvent` 
is called when a non-player entity is teleported from a rift.