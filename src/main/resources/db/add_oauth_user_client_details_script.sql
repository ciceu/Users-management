DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
`client_id` varchar(255) NOT NULL,
`resource_ids` varchar(255) DEFAULT NULL,
`client_secret` varchar(255) DEFAULT NULL,
`scope` varchar(255) DEFAULT NULL,
`authorized_grant_types` varchar(255) DEFAULT NULL,
`web_server_redirect_uri` varchar(255) DEFAULT NULL,
`authorities` varchar(255) DEFAULT NULL,
`access_token_validity` int(11) DEFAULT NULL,
`refresh_token_validity` int(11) DEFAULT NULL,
`additional_information` varchar(4096) DEFAULT NULL,
`autoapprove` varchar(255) DEFAULT NULL,
PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `oauth_client_details` VALUES ('demo',NULL,'$2a$10$TKjQdB97VlBNz65KPYc49OTESQCsDfxZCStUaId9JDUlEh6qkX4bq','webclient,mobileclient','authorization_code,refresh_token,implicit,password,client_credentials','','',3600,2592000,'{}','true');