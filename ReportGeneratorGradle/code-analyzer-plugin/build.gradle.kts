plugins {
    id("java")
    id ("java-gradle-plugin")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.pdfbox:pdfbox:3.0.5")
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("code-analyzer-plugin") {
            id = "org.example.code-analyzer"
            implementationClass = "org.example.WordsInCommentsPlugin"
        }
    }
}