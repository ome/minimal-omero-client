FROM eclipse-temurin:11-jdk-jammy
MAINTAINER ome-devel@lists.openmicroscopy.org.uk

ENV GRADLE_VERSION 6.8.3
RUN apt-get update && \
    apt-get install -y -q wget unzip
    
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt \
    && rm gradle-${GRADLE_VERSION}-bin.zip

COPY . /src

WORKDIR /src
RUN /opt/gradle-${GRADLE_VERSION}/bin/gradle build install

ENV ICE_CONFIG /src/ice.config
WORKDIR /src/build/install/src
CMD ["./bin/src"]
