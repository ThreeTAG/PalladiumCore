# PalladiumCore

Library for making for cross-platform mod developing easier.

## For Developers:
Add the following maven repository to your project:
```groovy
repositories {
    maven {
        url 'https://repo.repsy.io/mvn/lucraft/threetag'
    }
}
```

Then in your dedicated modules, replace `<VERSION>` with the version of the lib you want to use:
#### Common:
```groovy
dependencies {
    modApi "net.threetag:PalladiumCore:<VERSION>"
}
```

#### Fabric:
```groovy
dependencies {
    modApi "net.threetag:PalladiumCore-fabric:<VERSION>"
    include "net.threetag:PalladiumCore-fabric:<VERSION>"
}
```

#### Forge:
```groovy
dependencies {
    modApi "net.threetag:PalladiumCore-forge:<VERSION>"
    include "net.threetag:PalladiumCore-forge:<VERSION>"
}
```