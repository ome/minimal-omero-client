# Minimal OMERO client

[![Build Status](https://travis-ci.org/ome/minimal-omero-client.svg)](https://travis-ci.org/ome/minimal-omero-client)

Minimal Maven project for connecting to OMERO using the Java gateway based on
https://github.com/imagej/minimal-ij1-plugin. Thanks to @dscho and @ctrueden for the original example.

## Build

Install [Gradle](https://gradle.org/), and build this project:

    gradle build

## Install

    cd build/distributions
    unzip minimal-omero-client-5.5.x.zip

## Run

    ./minimal-omero-client-5.5.x/bin/minimal-omero-client --omero.host=localhost --omero.user=USERNAME --omero.pass=PASSWORD

You can pass additional properties, e.g. to debug SSL issues pass `--IceSSL.Trace.Security=1`.
