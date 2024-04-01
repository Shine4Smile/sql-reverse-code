-- ----------------------------
-- Table structure for data_source_config
-- ----------------------------
DROP TABLE IF EXISTS "data_source_config";
CREATE TABLE "data_source_config" (
                                      "id" bigint NOT NULL,
                                      "dbType" integer,
                                      "dbName" varchar(255),
                                      "schemaName" varchar(255),
                                      "host" varchar(255),
                                      "port" varchar(255),
                                      "userName" varchar(255),
                                      "password" varchar(255),
                                      "desc" varchar(255),
                                      "groupName" varchar(255),
                                      "isDelete" integer,
                                      PRIMARY KEY ("id")
);

PRAGMA foreign_keys = true;