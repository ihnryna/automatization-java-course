plugins {
    id("java-library")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation (project(":annotations"))
    implementation ("com.squareup:javapoet:1.13.0")
    /*implementation ("com.google.auto.service:auto-service:1.1.1")
    annotationProcessor ("com.google.auto.service:auto-service:1.1.1")*/
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")
}

tasks.test {
    useJUnitPlatform()
}