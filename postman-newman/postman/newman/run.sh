#!/bin/sh
newman run ./../collection/demo.postman_collection.json -d ./../data/students.csv --reporters cli,json --reporter-json-export ./report/report.json