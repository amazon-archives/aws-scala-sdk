# Build and install project-generator and generator-maven-plugin
mvn clean install

# Run the project generator
pushd project-generator
mvn exec:java
popd

# Build and install the project
pushd project
mvn clean install
popd
