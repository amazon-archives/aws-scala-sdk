# Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License"). You may not use
# this file except in compliance with the License. A copy of the License is
# located at
#
#  http://aws.amazon.com/apache2.0/
#
# or in the LICENSE file accompanying this file. This file is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
# implied. See the License for the specific language governing permissions and
# limitations under the License.

# Build and install project-generator and generator-maven-plugin
mvn clean install deploy

# Run the project generator
pushd project-generator
mvn exec:java
popd

# Build and install the project
pushd project
mvn clean install deploy
popd
