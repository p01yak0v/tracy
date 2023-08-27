plugins {
    id("tracy-jvm-convention")
}

dependencies {
    api(project(":common"))

    testImplementation(testFixtures(project(":common")))
}