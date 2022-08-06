repositories {
  mavenCentral()
  maven("https://jitpack.io")
}
plugins {
  `java-library`
  `maven-publish`
}
dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
  testImplementation("org.hamcrest:hamcrest:2.2")
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
