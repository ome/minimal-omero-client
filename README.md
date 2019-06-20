This is a minimal Maven project for connecting to OMERO
using the Java gateway
based on https://github.com/imagej/minimal-ij1-plugin.

Thanks to @dscho and @ctrueden for the example. More
instructions to come!


# Build

    ./gradlew build

Alternatively if you already have [Gradle](https://gradle.org/) installed:

    gradle build

# Install

    cd build/distributions
    unzip minimal-omero-client-5.4.10.zip

# Run

    ./minimal-omero-client-5.4.10/bin/minimal-omero-client --omero.host=localhost --omero.user=USERNAME --omero.pass=PASSWORD

You can pass additional properties, e.g. to debug SSL issues pass `--IceSSL.Trace.Security=1`.
