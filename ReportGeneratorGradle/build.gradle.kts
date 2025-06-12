plugins {
    id("java")
    id("org.example.code-analyzer")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

afterEvaluate {
    tasks.named("build") {
        dependsOn("wordsInProjectReport")
    }
}

tasks.test {
    useJUnitPlatform()
}