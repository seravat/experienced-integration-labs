Every good project has a parent pom that defines all of the properties and configuration that will be ingerited by all of the child projects. This should always include the Fuse BOM for Fuse projects. Past that, the repositories necessary are generally included in a shared settings.xml file but are here for convenience. Versions that are not already in the BOM can be added here, but you should avoid using custom versoins that are already in the BOM as there is a risk of not being supported.

POM files used on child projects should aways be NON-SNAPSHOT final versions. It's ok to test a SNAPSHOT version, but once you have a set of dependencies, you should create a final version so that child projects have a consistent reference of versions.