import org.gradle.api.JavaVersion.VERSION_11

repositories {
  mavenCentral()
  maven("https://jitpack.io")
}
plugins {
  `java-library`
  `maven-publish`
}
dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
  testImplementation("org.hamcrest:hamcrest:2.2")
}
java {
  sourceCompatibility = VERSION_11
  targetCompatibility = VERSION_11
}
tasks.test {
  useJUnitPlatform()
}
publishing {
  publications {
    create<MavenPublication>("bits") {
      from(components["java"])
    }
  }
}
