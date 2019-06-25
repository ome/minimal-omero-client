This is a minimal Maven project for connecting to OMERO
using the Java gateway
based on https://github.com/imagej/minimal-ij1-plugin.

Thanks to @dscho and @ctrueden for the example. More
instructions to come!


# Build

Install [Gradle](https://gradle.org/), and build this project:

    gradle build

# Install

    cd build/distributions
    unzip minimal-omero-client-5.4.10.zip

# Run

    ./minimal-omero-client-5.4.10/bin/minimal-omero-client --omero.host=localhost --omero.user=USERNAME --omero.pass=PASSWORD

You can pass additional properties, e.g. to debug SSL issues pass `--IceSSL.Trace.Security=1`.

# Using development CI libraries

Gradle will not accept a self-signed HTTPS certificate so you must first setup a local proxy.

Create a file `mergeci58443.conf`:
```
server {
    listen       8080;
    server_name  localhost;
    location / {
        proxy_pass https://merge-ci-devspace.openmicroscopy.org:58443;
    }
}
```
Run
```
docker run -it --rm -v$PWD/mergeci58443.conf:/etc/nginx/conf.d/default.conf:ro -p8080:8080 nginx`
```

Make the following change to `build.gradle`, changing `version` to the CI version:
```diff
diff --git a/build.gradle b/build.gradle
index f832e8e..bafcf51 100644
--- a/build.gradle
+++ b/build.gradle
@@ -11,6 +11,9 @@ def javaOpts = [
 version '5.5.0'

 repositories {
+    maven {
+        name 'mergeci'
+        url 'http://localhost:8080/nexus/repository/maven-internal/'}
     mavenLocal()
     mavenCentral()
     maven {
@@ -22,7 +25,7 @@ repositories {
 }

 dependencies {
-    compile(group: 'org.openmicroscopy', name: 'omero-gateway', version: '5.5.2')
+    compile(group: 'org.openmicroscopy', name: 'omero-gateway', version: '5.5.3-SNAPSHOT')
 }

 startScripts {
```

`gradle build` should pull in the new library.
