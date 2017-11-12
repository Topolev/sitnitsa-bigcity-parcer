#! /bin/bash
set -e

echo "Run MySQL server"
chown -R mysql:mysql /var/lib/mysql /var/run/mysqld
service mysql start $ sleep 10

echo "Create project database"
mysql --user=root --password=root -e "CREATE DATABASE IF NOT EXISTS \`JHipsterExample\` CHARACTER SET utf8 COLLATE utf8_general_ci; FLUSH PRIVILEGES;"

echo "Run java project"
java -jar /sitnitsa-bigcity-parcer/target/*.war


sleep 5

exec "$@"
