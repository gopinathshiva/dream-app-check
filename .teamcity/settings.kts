import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.add
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.2"

project {

    vcsRoot(HttpsGithubComGopinathshivaDreamAppCheckRefsHeadsMaster)

//    buildType(Build)
//    buildType(FirefoxTest)
//    buildType(ChromeTest)

    val buildChain = sequential {
      buildType(Build)
      parallel {
        buildType(FirefoxTest)
        buildType(ChromeTest)
      }
    }

    buildChain.buildTypes().forEach { buildType(it) }

    subProject {
      id("sub_project_id")
      parentId("DreamAppCheck")
      name = "first sub project"
    }
}

object FirefoxTest : BuildType({
  name = "Firefox Test"

  vcs {
    root(HttpsGithubComGopinathshivaDreamAppCheckRefsHeadsMaster)
  }

  steps{
//    script {
//      name = "Firefox Test"
//      scriptContent = """
//          npm run test-Firefox
//        """.trimIndent()
//    }
    script {
      name = "echo"
      scriptContent = "echo Firefox successful"
    }
  }

  artifactRules = "coverage/my-dream-app => coverage.zip"

  triggers {
    vcs {
    }
  }

//  dependencies {
//    snapshot(Build){}
//  }

})

object ChromeTest : BuildType({
  name = "Chrome Test"

  vcs {
    root(HttpsGithubComGopinathshivaDreamAppCheckRefsHeadsMaster)
  }

  steps{
//    script {
//      name = "Chrome Test"
//      scriptContent = """
//          npm run test-chrome
//        """.trimIndent()
//    }
    script {
      name = "echo"
      scriptContent = "echo chrome successful"
    }
  }

  artifactRules = "coverage/my-dream-app => coverage.zip"

  triggers {
    vcs {
    }
  }

//  dependencies {
//    snapshot(Build){}
//  }

})

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComGopinathshivaDreamAppCheckRefsHeadsMaster)
    }

    steps{
//      script {
//        name = "Install"
//        scriptContent = "npm install"
//      }
//      script {
//        name = "Lint"
//        scriptContent = "npm run lint"
//      }
//      script {
//        name = "Build"
//        scriptContent = "npm run build"
//      }
        script{
          name = "echo"
          scriptContent = "echo successful %teamcity.build.branch%"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
      swabra {  }
    }
})

object HttpsGithubComGopinathshivaDreamAppCheckRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/gopinathshiva/dream-app-check#refs/heads/master"
    url = "https://github.com/gopinathshiva/dream-app-check"
})
