rootProject.name = "ReportGeneratorGradle"
include("cli")
include("pdf-generator")
include("pdf-generator")
include("email-sender")

pluginManagement {
    includeBuild("code-analyzer-plugin")
}

