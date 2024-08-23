plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.4"
}

gitHooks {
    // Configuration
    commitMsg { conventionalCommits() } // Applies the default conventional commits configuration
    createHooks() // actual hooks creation
}

rootProject.name = "openapi-catalog-backend"
