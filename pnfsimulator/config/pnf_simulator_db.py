## ============LICENSE_START=======================================================
## Simulator
## ================================================================================
## Copyright (C) 2020 Nokia. All rights reserved.
## ================================================================================
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##      http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
## ============LICENSE_END=========================================================

from pymongo import MongoClient

client = MongoClient(host='10.183.35.247',
                     port=32223,
                     username='root',
                     password='zXcVbN123!',
                    authSource="admin")


col_list=client.pnf_simulator.list_collection_names()
client.pnf_simulator.add_user( 'pnf_simulator_user',  "zXcVbN123!", roles= [{'role':'readWrite', 'db':'pnf_simulator'},{'role':'dbAdmin', 'db':'pnf_simulator'}])


if 'simulatorConfig' not in col_list:
    client.pnf_simulator.simulatorConfig.insert_one({"vesServerUrl": "http://xdcae-ves-collector.onap:8080/eventListener/v7"})

if 'template' not in col_list:
    client.pnf_simulator.create_collection('template')

if 'flatTemplatesView' not in col_list:
    client.pnf_simulator.create_collection("flatTemplatesView", viewOn="template",pipeline=[{"$project":{"keyValues":{"$objectToArray": "$$ROOT.flatContent"}}}])

print("Following colections are present in simualtor db: " ,  client.pnf_simulator.list_collection_names())
